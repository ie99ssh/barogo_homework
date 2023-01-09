package com.barogo.platform.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginReqDto {

    @ApiModelProperty(notes = "유저 ID", required = true)
    @NotEmpty(message = "유저 ID는 필수입니다.")
    private String userId;

    @ApiModelProperty(notes = "패스워드")
    @NotEmpty(message = "패스워드는 필수입니다.")
    private String password;
}

