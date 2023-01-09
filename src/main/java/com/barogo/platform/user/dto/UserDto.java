package com.barogo.platform.user.dto;

import com.barogo.platform.auth.dto.LoginResDto;
import com.barogo.platform.common.domain.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDto extends BaseModel {

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class UserLoginRes extends LoginResDto {

        @ApiModelProperty(notes = "유저 정보")
        private UserRes userData;

    }

    @Data
    public static class UserRes {

        @ApiModelProperty(notes = "유저 일련번호")
        private Long userSeq;

        @ApiModelProperty(notes = "이메일")
        private String email;

        @ApiModelProperty(notes = "연락처")
        private String phoneNo;

        @ApiModelProperty(notes = "마지막 로그인 일시")
        private LocalDateTime lastLoginDate;

        @ApiModelProperty(notes = "마지막 로그인 IP")
        private String lastLoginIP;

        @ApiModelProperty(notes = "사용여부")
        private Boolean useFlag;
    }

    @Data
    public static class InsertUserReq {

        @ApiModelProperty(notes = "유저 ID", required = true)
        @NotEmpty(message = "유저 ID는 필수입니다.")
        private String userId;

        @ApiModelProperty(notes = "유저명", required = true)
        @NotEmpty(message = "유저명은 필수입니다.")
        private String userName;

        @ApiModelProperty(notes = "유저 비밀번호", required = true)
        @NotEmpty(message = "유저 비밀번호는 필수입니다.")
        private String userPwd;

        @ApiModelProperty(notes = "이메일")
        private String email;

        @ApiModelProperty(notes = "연락처")
        private String phoneNo;

        @ApiModelProperty(notes = "등록자", required = true)
        @NotEmpty(message = "등록자는 필수입니다.")
        private String regId;
    }
}
