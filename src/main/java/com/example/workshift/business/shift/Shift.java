package com.example.workshift.business.shift;

import com.example.workshift.business.exception.InvalidShiftDurationException;
import com.example.workshift.business.exception.ShiftDurationMoreThanEightHoursException;
import lombok.NonNull;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Stream;

public record Shift(
        Long id,
        @NonNull Instant activeFrom,
        @NonNull Instant activeTo,
        @NonNull Long userId,
        @NonNull Long shopId
) {

    private static final String UTC = "UTC";

    public static Shift of(@NonNull final CreateShiftRequest createShiftRequest) {
        return new Shift(
                null,
                createShiftRequest.activeFrom(),
                createShiftRequest.activeTo(),
                createShiftRequest.userId(),
                createShiftRequest.shopId()
        );
    }

    public Shift {
        validateDuration(activeFrom, activeTo);
        validateMaxEightHoursDuration(activeFrom, activeTo);
    }

    private void validateDuration(final Instant from, final Instant to) {
        if (!to.isAfter(from)) {
            throw new InvalidShiftDurationException();
        }
    }

    private void validateMaxEightHoursDuration(final Instant from, final Instant to) {
        if (Duration.between(from, to).compareTo(Duration.ofHours(8)) > 0) {
            throw new ShiftDurationMoreThanEightHoursException();
        }
    }

    public Shift adjustActiveFrom(final Instant newActiveFrom) {
        return new Shift(id, newActiveFrom, activeTo, userId, shopId);
    }

    public Shift adjustActiveTo(final Instant newActiveTo) {
        return new Shift(id, activeFrom, newActiveTo, userId, shopId);
    }

    public boolean encloseTimestamp(final Instant timestamp) {
        return activeFrom.isBefore(timestamp) && activeTo.isAfter(timestamp);
    }

    public long calculateDurationInMs() {
        return Duration.between(activeFrom, activeTo).toMillis();
    }

    public Instant getStartOf5DayWindow() {
        final Instant startOfDayBasedOnActiveFrom = LocalDate.ofInstant(activeFrom, ZoneId.of(UTC)).atStartOfDay().toInstant(ZoneOffset.UTC);
        final long daysLeftFor5DayWindow = isDurationTheSameDay() ? 5 : 4;

        return startOfDayBasedOnActiveFrom.minus(Duration.ofDays(daysLeftFor5DayWindow));
    }

    public Instant getEndOf5DayWindow() {
        final Instant endOfDayBasedOnActiveTo = LocalDate.ofInstant(activeTo, ZoneId.of(UTC)).atTime(LocalTime.MAX).toInstant(ZoneOffset.UTC);
        final long daysLeftFor5DayWindow = isDurationTheSameDay() ? 5 : 4;

        return endOfDayBasedOnActiveTo.plus(Duration.ofDays(daysLeftFor5DayWindow));
    }

    public List<LocalDate> getDistinctDaysOfDuration() {
        final LocalDate dateOfActiveFrom = getDateInUTC(activeFrom);
        final LocalDate dateOfActiveTo = getDateInUTC(activeTo);

        return Stream.of(dateOfActiveFrom, dateOfActiveTo).distinct().toList();
    }

    private boolean isDurationTheSameDay() {
        return getDateInUTC(activeFrom).equals(getDateInUTC(activeTo));
    }

    private LocalDate getDateInUTC(final Instant timestamp) {
        return LocalDate.ofInstant(timestamp, ZoneId.of(UTC));
    }
}
