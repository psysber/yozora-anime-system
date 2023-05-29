package com.yozora.anime.service;

import com.yozora.anime.entity.NovelUsersEntity;
import com.yozora.anime.utils.GenerateCaptcha;
import com.yozora.anime.utils.JavaMailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;
@Service
public class RegistrationService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired private JavaMailUtil javaMailUtil;

    public boolean sendVerificationCode(NovelUsersEntity entity) throws Exception {
        GenerateCaptcha generator = new GenerateCaptcha();
        Map<String, String> data = generator.generateCaptcha();
        String img = data.get("img");
        String captcha = data.get("captcha");

        redisTemplate.opsForValue().set("register_" + captcha, entity, 30, TimeUnit.MINUTES);
        javaMailUtil.send(entity.getEmail(), img);

        return true;
    }
}
