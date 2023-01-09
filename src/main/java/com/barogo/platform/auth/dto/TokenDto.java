package com.barogo.platform.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

public class TokenDto {

    @Data
    public static class TokenRequestDto {

        @ApiModelProperty(notes = "ID", required = true)
        @NotEmpty(message = "ID는 필수입니다.")
        private String id;

        @ApiModelProperty(notes = "access 토큰", required = true)
        @NotEmpty(message = "access 토큰은 필수입니다.")
        private String accessToken;

        @ApiModelProperty(notes = "refresh 토큰", required = true)
        @NotEmpty(message = "refresh 토큰은 필수입니다.")
        private String refreshToken;
    }


    @Data
    @Builder
    public static class TokenResponseDto {
        private String accessToken;
        private String refreshToken;
        private String grantType;
        private Long accessTokenExpiresIn;
    }

}
