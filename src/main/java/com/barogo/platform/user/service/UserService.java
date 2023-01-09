package com.barogo.platform.user.service;

import com.barogo.platform.common.exception.Errors;
import com.barogo.platform.common.exception.GlobalBadRequestException;
import com.barogo.platform.user.dao.UserRepository;
import com.barogo.platform.user.dto.UserDto;
import com.barogo.platform.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public User getUser(String email) {
        return repository.findByUserId(email);
    }

    public Optional<User> getAuthByUserId(String userId) {
        return repository.findAuthByUserId(userId);
    }

    public User getByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    public User insertUser(UserDto.InsertUserReq insertUserReq) {

        String password = insertUserReq.getUserPwd();
        String pwPattern1 = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{12,}$";
        String pwPattern2 = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[$@$!%*#?&]).{12,}$";
        String pwPattern3 = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{12,}$";
        String pwPattern4 = "^(?=.*[a-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{12,}$";

        boolean passwordValid1 = Pattern.matches(pwPattern1, password);
        boolean passwordValid2 = Pattern.matches(pwPattern2, password);
        boolean passwordValid3 = Pattern.matches(pwPattern3, password);
        boolean passwordValid4 = Pattern.matches(pwPattern4, password);

        if (!passwordValid1 && !passwordValid2 && !passwordValid3 && !passwordValid4) {
            throw new GlobalBadRequestException(Errors.USER_GENERATE_PASSWORD_INVALID);
        }

        User user = this.getByUserId(insertUserReq.getUserId());

        if (!ObjectUtils.isEmpty(user)) {
            throw new GlobalBadRequestException(Errors.USER_ID_ALREADY_EXIST);
        } else {
            user = new User();
        }

        String encodePassword = passwordEncoder.encode(password);

        BeanUtils.copyProperties(insertUserReq, user);
        user.setUseFlag(true);
        user.setUserPwd(encodePassword);

        return repository.save(user);

    }

}
