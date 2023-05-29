package com.yozora.anime.controller;

import com.yozora.anime.entity.CaptChaEntity;
import com.yozora.anime.utils.GenerateCaptcha;
import com.yozora.anime.utils.R;
import com.yozora.anime.utils.RET;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/captcha")
@Api(tags = "验证码", value = "captcha")
public class CaptchaController {
    private final RedisTemplate redisTemplate;

    @GetMapping("/generateCaptcha")
    public R generateCaptcha() throws IOException {
       GenerateCaptcha captcha = new GenerateCaptcha();
        Map<String, String> captchaResult = captcha.generateCaptcha();
        String captchaText =captchaResult.get("captcha");
        String uuid= UUID.randomUUID().toString();
        uuid=uuid.replace("-","");
        redisTemplate.opsForValue().set("CAPTCHA_"+uuid,captchaText,5, TimeUnit.MINUTES);
        captchaResult.put("captchaId",uuid);
        captchaResult.remove("captcha");
        return  R.ok(captchaResult);
    }

    @GetMapping("/validate/{captcha_id}/{captcha}")
    public R<?> validateCaptcha(@PathVariable String captcha_id, @PathVariable String captcha) {
        String code = (String) redisTemplate.opsForValue().get("CAPTCHA_" + captcha_id);
        if (code != null && code.equalsIgnoreCase(captcha)) {
            return R.ok();
        }
        return R.failed("验证码错误", RET.FAILED);
    }

}
