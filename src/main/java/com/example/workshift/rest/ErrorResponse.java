package com.example.workshift.rest;

import lombok.NonNull;

public record ErrorResponse(
        @NonNull String message,
        @NonNull String errorCode
) {
}
