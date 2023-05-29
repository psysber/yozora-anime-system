package com.yozora.anime.validator;

import com.yozora.anime.entity.NovelUsersEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component

public class NovelUsersValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return NovelUsersEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.required");

        NovelUsersEntity user = (NovelUsersEntity) target;
        if (!isValidEmail(user.getEmail())) {
            errors.rejectValue("email", "email.invalid");
        }
        if (!isValidPassword(user.getPassword())) {
            errors.rejectValue("password", "password.invalid");
        }
    }

    private boolean isValidEmail(String email) {
        // 邮箱正则表达式，用于校验邮箱格式
        String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String password) {
        // 密码为6-20位字符，必须同时包含大小写字母、数字和特殊符号中的3种
        String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[_!@#$%^&*()+=|{}?<>]).{6,20}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
