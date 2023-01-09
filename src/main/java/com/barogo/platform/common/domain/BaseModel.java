package com.barogo.platform.common.domain;

import com.barogo.platform.common.exception.ErrorModel;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * --------------------------------------------------------------------
 * ■기본 DT 포함 모델 ■sangheon
 * --------------------------------------------------------------------
 **/
@Data
public class BaseModel {

    private Object data;
    private ErrorModel error;

}
