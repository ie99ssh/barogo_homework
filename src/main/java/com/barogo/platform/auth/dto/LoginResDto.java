package com.barogo.platform.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginResDto {

    @ApiModelProperty(notes = "액세스 토큰")
    private String accessToken;

    @ApiModelProperty(notes = "리프레쉬 토큰")
    private String refreshToken;
}
