package com.yozora.anime.auth;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ParamFilter extends OncePerRequestFilter {

    @Value("${app.self.name}")
    private String selfName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        RequestParameterWrapper requestWrapper=null;
        String method=request.getMethod();
        if("GET".equalsIgnoreCase(method)){
            try {
                requestWrapper = new RequestParameterWrapper(request, RequestMethod.GET);
                Map<String, String[]> parameterMap = new HashMap<>(requestWrapper.getParameterMap());
                log.info("get 参数={}", parameterMap.toString());
                parameterMap.put("selfName", new String[]{selfName});
                requestWrapper.setParameterMap(parameterMap);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if("POST".equals(method)){
            try {
                requestWrapper = new RequestParameterWrapper(request, RequestMethod.POST);
                //获取post消息体
                String body = requestWrapper.getBody();
                JSONObject jsonObject = new JSONObject().getJSONObject(body);
                String dataAuthCode = (String) jsonObject.get("selfName");
                if (StringUtils.isNotBlank(dataAuthCode)) {
                    chain.doFilter(request, response);
                } else {
                    jsonObject.put("selfName", selfName);
                    requestWrapper.setBody(jsonObject);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if(requestWrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }
}

