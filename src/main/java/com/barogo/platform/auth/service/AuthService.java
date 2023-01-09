package com.barogo.platform.auth.service;

import com.barogo.platform.auth.Entity.AuthRefreshToken;
import com.barogo.platform.auth.dao.AuthRefreshTokenRepository;
import com.barogo.platform.auth.dto.LoginReqDto;
import com.barogo.platform.auth.dto.TokenDto;
import com.barogo.platform.auth.dto.TokenDto.TokenResponseDto;
import com.barogo.platform.common.exception.Errors;
import com.barogo.platform.common.exception.GlobalException;
import com.barogo.platform.common.security.JwtTokenProvider;
import com.barogo.platform.common.util.CommonUtil;
import com.barogo.platform.user.dto.UserDto.UserLoginRes;
import com.barogo.platform.user.dto.UserDto.UserRes;
import com.barogo.platform.user.entity.User;
import com.barogo.platform.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;

/**
 * --------------------------------------------------------------------
 * ■로그인 인터페이스 구현부 ■sangheon
 * --------------------------------------------------------------------
 **/
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final AuthRefreshTokenRepository authRefreshTokenRepository;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final CommonUtil commonUtil;

    @Override
    public UserDetails loadUserByUsername(String id) {
        return userService.getAuthByUserId(id)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(id + " : 유저가 존재하지 않습니다."));
    }

    private UserDetails createUserDetails(User user) {

        GrantedAuthority grantedAuthority
                = new SimpleGrantedAuthority("USER");

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                "",
                Collections.singleton(grantedAuthority)
        );
    }

    public UserLoginRes login(HttpServletRequest request, LoginReqDto loginReqDto) {

        String userId = loginReqDto.getUserId();
        String password = loginReqDto.getPassword();
        User user = userService.getUser(userId);

        String ipAddress = commonUtil.getIpAddr(request);

        // 유저 존재 유무 확인
        if (ObjectUtils.isEmpty(user)) {
            throw new GlobalException(Errors.USER_NOT_FOUND);
        }

        // 패스워드 일치 확인
        if (!passwordEncoder.matches(password, user.getUserPwd())) {
            throw new GlobalException(Errors.USER_WRONG_PASSWORD);
        }

        if (!user.getUseFlag()) {
            throw new GlobalException(Errors.USER_DEPRECATED);
        }

        user.setLastLoginDate(LocalDateTime.now());
        user.setUpdDate(LocalDateTime.now());
        user.setLastLoginIP(ipAddress);
        user.setUpdId(userId);

        UserLoginRes userLoginRes = new UserLoginRes();
        UserRes userRes = new UserRes();
        BeanUtils.copyProperties(user, userRes);

        TokenResponseDto tokenResponseDto = this.getAccessToken(userId);

        AuthRefreshToken authRefreshToken = authRefreshTokenRepository.findByUserId(userId);

        if (ObjectUtils.isEmpty(authRefreshToken)) {
            authRefreshToken = new AuthRefreshToken();
            authRefreshToken.setUserId(userId);
            authRefreshToken.setRefreshToken(tokenResponseDto.getRefreshToken());
            authRefreshToken.setRegId(userId);
            authRefreshTokenRepository.save(authRefreshToken);
        } else {
            authRefreshToken.setRefreshToken(tokenResponseDto.getRefreshToken());
            authRefreshToken.setUpdDate(LocalDateTime.now());
            authRefreshToken.setUpdId(userId);
        }

        userLoginRes.setAccessToken(tokenResponseDto.getAccessToken());
        userLoginRes.setRefreshToken(tokenResponseDto.getRefreshToken());
        userLoginRes.setUserData(userRes);

        return userLoginRes;

    }

    public TokenResponseDto refresh(TokenDto.TokenRequestDto tokenRequestDto) {

        String userId = tokenRequestDto.getId();

        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new GlobalException(Errors.USER_INVALID_REFRESH_TOKEN);
        }

        // 2. Access Token 에서 manager ID 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        if (ObjectUtils.isEmpty(authentication)) {
            throw new GlobalException(Errors.USER_INCONSISTENCY_ACCESS_TOKEN);
        }

        if (!authentication.getName().equals(userId)) {
            throw new GlobalException(Errors.USER_REQUEST_INCONSISTENCY_REFRESH_TOKEN);
        }

        // 3. 저장소에서 user ID 를 기반으로 Refresh Token 값 가져옴
        AuthRefreshToken refreshToken = this.getAuthRefreshToken(authentication.getName());

        if (ObjectUtils.isEmpty(refreshToken)) {
            throw new GlobalException(Errors.USER_ALREADY_LOGOUT);
        }

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getRefreshToken().equals(tokenRequestDto.getRefreshToken())) {
            throw new GlobalException(Errors.USER_INCONSISTENCY_REFRESH_TOKEN);
        }

        // 5. 새로운 토큰 생성
        TokenResponseDto tokenResponseDto = jwtTokenProvider.generateToken(authentication);

        // 6. 저장소 정보 업데이트
        refreshToken.setRefreshToken(tokenResponseDto.getRefreshToken());

        // 토큰 발급
        return tokenResponseDto;
    }

    public AuthRefreshToken getAuthRefreshToken(String userId) {
        return authRefreshTokenRepository.findByUserId(userId);
    }

    public TokenResponseDto getAccessToken(String userId) {
        UserDetails userDetails = this.loadUserByUsername(userId);
        UserDetails principal
                = new org.springframework.security.core.userdetails.User(userDetails.getUsername(), "", userDetails.getAuthorities());
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(principal, "", userDetails.getAuthorities());

        return jwtTokenProvider.generateToken(authenticationToken);
    }

}

