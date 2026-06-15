package com.jarsolutions.fitness.common.infrastructure.in.web.response;

import java.time.Instant;

public record ErrorResponse(int status, String error, String message, Instant timestamp) {}
