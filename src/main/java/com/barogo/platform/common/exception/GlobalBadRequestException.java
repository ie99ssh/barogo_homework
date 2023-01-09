package com.barogo.platform.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class GlobalBadRequestException extends RuntimeException {

    private final Errors errors;
    private String detailMessage;

    public GlobalBadRequestException(Errors errors) {
        super("[" + errors.getCode() + "] " + errors.getMessage());
        this.errors = errors;
    }

    public GlobalBadRequestException(Errors errors, String detailMessage) {
        super("[" + errors.getCode() + "] " + errors.getMessage());
        this.errors = errors;
        this.detailMessage = detailMessage;
    }

    public Errors getErrors() {
        return this.errors;
    }

    public String getDetailMessage() {
        return this.detailMessage;
    }
}
