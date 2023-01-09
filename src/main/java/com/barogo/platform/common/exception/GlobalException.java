package com.barogo.platform.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * --------------------------------------------------------------------
 * ■사용자 정의 전역 오류 클래스 ■sangheon
 * --------------------------------------------------------------------
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = 3708694357653235562L;

    private Errors errors;

    public GlobalException(Errors errors) {
        super("[" + errors.getCode() + "] " + errors.getMessage());
        this.errors = errors;
    }
}
