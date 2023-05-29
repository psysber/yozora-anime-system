package com.yozora.anime.aop;

import com.yozora.anime.utils.R;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MemberSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;


import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
@Order(100) // 定义切面的优先级，数字越小优先级越高
@RequiredArgsConstructor // 使用 Lombok 注解生成构造器
public class FilterRepeatRequest {

    // Redis 中 key 值的前缀，用于区分不同场景下的请求防重。
    private static final String SUFFIX = "REQUEST_";

    private final RedisTemplate redisTemplate;

    // 定义一个 AOP 切点，匹配所有被 AvoidRepeatRequest 注解标记了的方法
    @Pointcut("@annotation(com.yozora.anime.aop.AvoidRepeatRequest)")
    public void arrPointCut() {}

    // Around 通知，对匹配到的方法进行拦截
    @Around("arrPointCut()")
    public Object arrBusiness(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        // 构造 Redis 中存储当前请求唯一性的 key
        String key = SUFFIX + request.getSession().getId() + "_" + request.getRequestURI();
        // 获取方法对象，再获取其中的 AvoidRepeatRequest 注解，从而得到重复请求时间间隔及返回结果信息
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        AvoidRepeatRequest annotation = method.getAnnotation(AvoidRepeatRequest.class);
        // 查询 Redis 中是否存在该 key，如果存在说明当前请求已经发生过或正在处理中，则直接返回注解中设置的错误信息。否则设置该 key 的值，并设置失效时间。
        if (!redisTemplate.opsForValue().setIfAbsent(key, 1, annotation.intervalTime(), TimeUnit.SECONDS)) {
            return annotation.msg();
        }
        try {
            // 执行拦截到的请求
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            // 异常处理，如果拦截请求执行中产生异常，则返回 R.failed() 作为错误信息。
            throwable.printStackTrace();
            return R.failed();
        } finally {
            // 删除 Redis 中存储当前请求唯一性的 key
            redisTemplate.delete(key);
        }
    }
}

