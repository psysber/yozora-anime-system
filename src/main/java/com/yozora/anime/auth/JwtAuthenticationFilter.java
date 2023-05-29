package com.yozora.anime.auth;

import com.yozora.anime.exception.CaptchaException;
import com.yozora.anime.utils.GenerateCaptcha;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final String TOKEN_PREFIX = "Bearer";
    private final String HEADER_STRING = "Authorization";
    @Autowired
    private   JwtTokenProvider jwtTokenProvider ;

    @Autowired
    private   RedisTemplate<String, Object> redisTemplate;
    private  String loginFilterUrl = "/users/login";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


            try {
                String jwt = extractJwtFromRequest(request);
                if (jwt != null) {
                    String username = getUsernameFromJwt(jwt);
                    Object tokenObj = redisTemplate.opsForValue().get("auth_"+username);
                    if (tokenObj != null && tokenObj.toString().equals(jwt)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    } else if (validateJwt(jwt)) {
                        redisTemplate.opsForValue().set("auth_"+username, jwt);
                        redisTemplate.expire("auth_"+username, 1, TimeUnit.HOURS);
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            } catch (Exception e) {
                logger.error("Cannot set user authentication: {}", e);
            }

        filterChain.doFilter(request, response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_STRING);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.replace(TOKEN_PREFIX, "").trim();
        }
        return null;
    }


    private boolean validateJwt(String jwt) throws JwtException {

       return  jwtTokenProvider.validateToken(jwt);
    }

    private String getUsernameFromJwt(String jwt) {

        return jwtTokenProvider.getUsernameFromJWT(jwt);
    }
}
