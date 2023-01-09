package com.barogo.platform.common.exception;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ErrorModel {
    private String code;
    private String message;
}
