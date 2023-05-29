package com.yozora.anime.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yozora.anime.exception.CaptchaException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class CaptchaFilter extends GenericFilterBean {
    private final RedisTemplate redisTemplate;

    private  String loginFilterUrl = "/users/login";
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if("POST".equalsIgnoreCase(request.getMethod()) && loginFilterUrl.equals(request.getServletPath())){

                // 从 JSON 请求体中获取验证码字段
                String requestBodyString = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
                if(StringUtils.isNoneEmpty(requestBodyString)){
                // 将 JSON 数据解析为 JsonNode 对象
                ObjectMapper mapper = new ObjectMapper();
                JsonNode requestBody = mapper.readTree(requestBodyString);

                String captcha = requestBody.get("captcha").asText();
                String captchaId =requestBody.get("captchaId").asText();
                if (captcha == null || captcha.isEmpty()) {
                    // 如果验证码为空，返回错误响应
                    throw new CaptchaException("验证码不能为空");

                }

                // 从 Redis 中获取已保存的验证码
                String savedCaptcha = (String) redisTemplate.opsForValue().get("CAPTCHA_"+captchaId);

                // 检查验证码是否匹配
                if (!captcha.equalsIgnoreCase(savedCaptcha)) {
                    // 如果验证码不匹配，返回未授权响应
                    throw new CaptchaException("验证码不正确");

                }
            }

        }


        // 验证通过，继续调用过滤器链中的下一个过滤器或处理程序
        filterChain.doFilter(request, response);
    }
}
