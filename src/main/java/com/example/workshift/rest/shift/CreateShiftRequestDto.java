package com.example.workshift.rest.shift;

import java.time.Instant;

public record CreateShiftRequestDto(
        Instant activeFrom,
        Instant activeTo,
        Long userId,
        Long shopId
) {
}
