package com.barogo.platform.user.controller;

import com.barogo.platform.common.domain.BaseModel;
import com.barogo.platform.user.dto.UserDto.InsertUserReq;
import com.barogo.platform.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Api(tags = "[A - 2] 유저 관리")
public class UserController {

    private final UserService userService;

    /**
     * --------------------------------------------------------------------
     * ■유저 등록 API ■sangheon
     * --------------------------------------------------------------------
     **/
    @ApiOperation(value = "유저 회원가입", notes = "유저 회원가입")
    @ResponseBody
    @RequestMapping(value = "/users/register", method = RequestMethod.POST)
    public ResponseEntity<BaseModel> insertUser(@Valid @RequestBody InsertUserReq insertUserReq) {
        userService.insertUser(insertUserReq);
        return new ResponseEntity<>(new BaseModel(), HttpStatus.CREATED);
    }

}
