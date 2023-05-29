package com.yozora.anime.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yozora.anime.exception.CaptchaException;
import com.yozora.anime.utils.R;
import com.yozora.anime.utils.RET;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(R.failed(RET.UNAUTHORIZED));
        response.getWriter().write(json);
    }
}
