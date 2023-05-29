package com.yozora.anime.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yozora.anime.entity.NovelUsersEntity;
import com.yozora.anime.service.NovelUsersService;
import com.yozora.anime.service.RegistrationService;
import com.yozora.anime.utils.R;
import com.yozora.anime.utils.RET;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {

    private final  RedisTemplate redisTemplate;
    private final NovelUsersService usersService;
    private final RegistrationService registrationService;

    @PostMapping
    public R register(@Validated @RequestBody NovelUsersEntity entity, BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldError().getDefaultMessage();
            R.failed(errorMsg, RET.FAILED);
        }
        NovelUsersEntity user = usersService.getOne(Wrappers.<NovelUsersEntity>lambdaQuery().eq(NovelUsersEntity::getEmail,entity.getEmail()).or(wq->wq.eq(NovelUsersEntity::getUsername,entity.getUsername()))
        );
        if (user != null) {
            return R.failed("用户名或邮箱重复", RET.FAILED);
        }
        String password = entity.getPassword();
        String encode = new BCryptPasswordEncoder().encode(password);
        entity.setPassword(encode);
        boolean isSent = registrationService.sendVerificationCode(entity);
        if (!isSent) {
            return R.failed("发送验证码失败，请稍后再试", RET.FAILED);
        }

        return R.ok();
    }

    @GetMapping("/validate/{code}")
    public ResponseEntity<R> validateCode(@PathVariable String code) {
        try {
            if (StringUtils.isBlank(code)) {
                return ResponseEntity.badRequest().body(R.failed("验证码不能为空", RET.FAILED));
            }

            NovelUsersEntity registeredUser = (NovelUsersEntity) redisTemplate.opsForValue().get("register_" + code);
            if (registeredUser == null) {
                return ResponseEntity.notFound().build();
            }

            usersService.save(registeredUser);

            redisTemplate.delete("register_" + code);

            return ResponseEntity.ok().body(R.ok());

        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body(R.failed("空指针异常", RET.FAILED));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(R.failed("不合法参数异常", RET.FAILED));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(R.failed("服务器内部错误", RET.FAILED));
        }
    }

}
