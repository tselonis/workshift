package com.example.workshift.rest.shift;

import com.example.workshift.business.shift.CreateShiftRequest;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
class ShiftDomainMapper {
    public CreateShiftRequest mapToCreateShiftRequest(@NonNull final CreateShiftRequestDto createShiftRequestDto) {
        return new CreateShiftRequest(
                createShiftRequestDto.activeFrom(),
                createShiftRequestDto.activeTo(),
                createShiftRequestDto.userId(),
                createShiftRequestDto.shopId()
        );
    }
}
