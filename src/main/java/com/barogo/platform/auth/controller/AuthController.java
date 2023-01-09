package com.barogo.platform.auth.controller;

import com.barogo.platform.auth.dto.LoginReqDto;
import com.barogo.platform.auth.dto.TokenDto.TokenRequestDto;
import com.barogo.platform.auth.dto.TokenDto.TokenResponseDto;
import com.barogo.platform.auth.service.AuthService;
import com.barogo.platform.user.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * --------------------------------------------------------------------
 * ■로그인 컨트롤러 ■sangheon
 * --------------------------------------------------------------------
 **/
@RestController
@RequestMapping(value = {"/auth"})
@RequiredArgsConstructor
@Api(tags = "[A - 1] 유저 인증 관리")
public class AuthController {

    private final AuthService authService;

    // 로그인
    @ApiOperation(value = "로그인 처리", notes = "로그인 처리")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<UserDto.UserLoginRes> login(HttpServletRequest request, @RequestBody @Valid LoginReqDto loginReqDto) {

        UserDto.UserLoginRes loginResDto = authService.login(request, loginReqDto);
        return new ResponseEntity<>(loginResDto, HttpStatus.OK);
    }

    @ApiOperation(value = "리프레쉬 토큰 요청", notes = "리프레쉬 토큰 요청")
    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<TokenResponseDto> refresh(@RequestBody @Valid TokenRequestDto tokenRequestDto) {
        TokenResponseDto tokenResponseDto = authService.refresh(tokenRequestDto);

        return new ResponseEntity<>(tokenResponseDto, HttpStatus.OK);
    }

}
