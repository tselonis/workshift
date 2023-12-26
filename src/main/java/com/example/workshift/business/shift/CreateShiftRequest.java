package com.example.workshift.business.shift;

import lombok.NonNull;

import java.time.Instant;

public record CreateShiftRequest(
        @NonNull Instant activeFrom,
        @NonNull Instant activeTo,
        @NonNull Long userId,
        @NonNull Long shopId
) {
}
