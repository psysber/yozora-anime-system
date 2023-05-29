package com.yozora.anime.auth;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

public class RequestParameterWrapper extends HttpServletRequestWrapper {

        private String body;

        private Map<String, String[]> parameterMap;

        public RequestParameterWrapper(HttpServletRequest request, RequestMethod method) throws Exception{
            super(request);

            if(RequestMethod.POST.equals(method)){
                StringBuilder stringBuilder = new StringBuilder();
                try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));) {
                    char[] charBuffer = new char[128];
                    int bytesRead;
                    while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                        stringBuilder.append(charBuffer, 0, bytesRead);
                    }
                } catch (Exception ex) {
                    throw ex;
                }
                body = stringBuilder.toString();
            }else if(RequestMethod.GET.equals(method)){
                parameterMap=request.getParameterMap();
            }

        }

        public String getBody() {
            return this.body;
        }

        /**
         * post方式使用
         * @param object
         */
        public void setBody(JSONObject object) {
            this.body=object.toString();
        }



        @Override
        public ServletInputStream getInputStream() throws IOException {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }
            };
        }


        @Override
        public Enumeration<String> getParameterNames() {
            Vector<String> vector = new Vector<String>(parameterMap.keySet());
            return vector.elements();
        }

        @Override
        public String getParameter(String name) {
            String[] results = parameterMap.get(name);
            return results[0];
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return parameterMap;
        }

        @Override
        public String[] getParameterValues(String name) {
            return parameterMap.get(name);
        }

        public void setParameterMap(Map<String, String[]> parameterMap) {
            this.parameterMap = parameterMap;
        }
    }



