package com.yozora.anime.exception;

import org.springframework.security.core.AuthenticationException;

public class CaptchaException extends AuthenticationException {

    private String captcha;

    public CaptchaException(String msg) {
        super(msg);
    }

    public CaptchaException(String msg, String captcha) {
        super(msg);
        this.captcha = captcha;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
