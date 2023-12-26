package com.example.workshift.business.shift;

import com.example.workshift.business.exception.ActiveShiftException;
import com.example.workshift.business.exception.MaxHoursInTheSameShopWithin24HourWindowExceededException;
import com.example.workshift.business.exception.MoreThanFiveDaysInARowInTheSameShopException;
import com.example.workshift.business.exception.ShiftCreationInThePastException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

@Service
@Transactional
@AllArgsConstructor
class ShiftServiceImpl implements ShiftService {

    private static final String ACTIVE_SHIFTS_ERROR_MESSAGE = "User has other active shifts in the following shops: %s";
    private static final String DELIMITER = ", ";

    private ShiftRepository shiftRepository;

    @Override
    public Shift createShift(@NonNull final CreateShiftRequest createShiftRequest) {
        final Shift shift = Shift.of(createShiftRequest);

        validateShiftCreationNotInThePast(shift);
        validateUserIsNotWorkingAtTheSameTime(shift);
        validateUserHoursInTheSameShopWithin24HourWindow(shift);
        validateUserCannotWorkMoreThanFiveDaysInARowInTheSameShop(shift);

        return shiftRepository.createShift(shift);
    }

    private void validateShiftCreationNotInThePast(final Shift shift) {
        if (shift.activeFrom().isBefore(Instant.now())) {
            throw new ShiftCreationInThePastException();
        }
    }

    private void validateUserIsNotWorkingAtTheSameTime(final Shift shift) {
        final List<Shift> shiftsWithOverlappingDuration = shiftRepository.findShiftByUserIdAndOverlappingDuration(
                shift.userId(),
                shift.activeFrom(),
                shift.activeTo()
        );
        if (!shiftsWithOverlappingDuration.isEmpty()) {
            final String distinctShopIds =
                    shiftsWithOverlappingDuration.stream().map(Shift::shopId).distinct().map(String::valueOf).collect(joining(DELIMITER));
            throw new ActiveShiftException(String.format(ACTIVE_SHIFTS_ERROR_MESSAGE, distinctShopIds));
        }
    }

    private void validateUserHoursInTheSameShopWithin24HourWindow(final Shift shift) {
        final Duration durationOfShift = Duration.between(shift.activeFrom(), shift.activeTo());
        final Duration durationLeftFor24HourWindow = Duration.ofHours(24).minus(durationOfShift);
        final Instant startTimestamp = shift.activeFrom().minus(durationLeftFor24HourWindow);
        final Instant endTimestamp = shift.activeTo().plus(durationLeftFor24HourWindow);

        final List<Shift> existingShiftsWithinPeriod = shiftRepository.findShiftByUserIdAndShopIdWithin(
                shift.userId(),
                shift.shopId(),
                startTimestamp,
                endTimestamp
        );
        final List<Shift> allShifts = Stream.concat(existingShiftsWithinPeriod.stream(), Stream.of(shift)).toList();
        final List<Shift> allShiftsAdjusted = allShifts.stream().map(s -> getShiftWithAdjustedActiveFrom(s, startTimestamp)).map(s -> getShiftWithAdjustedActiveTo(s, endTimestamp)).toList();

        for (Shift controlShift : allShiftsAdjusted) {
            final Instant twentyFourHourWindowEnd = controlShift.activeFrom().plus(Duration.ofHours(24));
            final long sumOfDurationInMs = allShiftsAdjusted.stream()
                    .filter(s -> s.activeTo().isAfter(controlShift.activeFrom()) && s.activeFrom().isBefore(twentyFourHourWindowEnd))
                    .map(s -> getShiftWithAdjustedActiveTo(s, twentyFourHourWindowEnd))
                    .mapToLong(Shift::calculateDurationInMs)
                    .sum();
            if (sumOfDurationInMs > Duration.ofHours(8).toMillis()) {
                throw new MaxHoursInTheSameShopWithin24HourWindowExceededException();
            }
        }
    }

    private void validateUserCannotWorkMoreThanFiveDaysInARowInTheSameShop(final Shift shift) {
        final Instant fiveDayWindowStart = shift.getStartOf5DayWindow();
        final Instant fiveDayWindowEnd = shift.getEndOf5DayWindow();

        final List<Shift> existingShiftsWithinPeriod = shiftRepository.findShiftByUserIdAndShopIdWithin(
                shift.userId(),
                shift.shopId(),
                fiveDayWindowStart,
                fiveDayWindowEnd
        );
        final List<Shift> allShifts = Stream.concat(existingShiftsWithinPeriod.stream(), Stream.of(shift)).toList();
        final List<Shift> allShiftsAdjusted = allShifts.stream().map(s -> getShiftWithAdjustedActiveFrom(s, fiveDayWindowStart)).map(s -> getShiftWithAdjustedActiveTo(s, fiveDayWindowEnd)).toList();
        final List<LocalDate> uniqueSortedDatesUserWorksInTheShop = allShiftsAdjusted.stream()
                .map(Shift::getDistinctDaysOfDuration)
                .flatMap(List::stream)
                .distinct()
                .sorted()
                .toList();
        if (uniqueSortedDatesUserWorksInTheShop.size() > 5 && areThereMoreThanFiveConsecutiveDays(uniqueSortedDatesUserWorksInTheShop)) {
            throw new MoreThanFiveDaysInARowInTheSameShopException();
        }
    }

    private boolean areThereMoreThanFiveConsecutiveDays(final List<LocalDate> localDates) {
        int consecutiveDaysCounter = 0;
        for (int i = 1; i < localDates.size(); i++) {
            if (localDates.get(i).equals(localDates.get(i - 1).plusDays(1))) {
                consecutiveDaysCounter++;
            } else {
                consecutiveDaysCounter = 0;
            }
            if ((consecutiveDaysCounter) == 5) {
                return true;
            }
        }
        return false;
    }

    private Shift getShiftWithAdjustedActiveFrom(final Shift shift, final Instant startTimestamp) {
        return shift.encloseTimestamp(startTimestamp)
                ? shift.adjustActiveFrom(startTimestamp)
                : shift;
    }

    private Shift getShiftWithAdjustedActiveTo(final Shift shift, final Instant endTimestamp) {
        return shift.encloseTimestamp(endTimestamp)
                ? shift.adjustActiveTo(endTimestamp)
                : shift;
    }
}
