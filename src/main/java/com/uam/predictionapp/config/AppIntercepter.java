package com.uam.predictionapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uam.predictionapp.model.TokenDto;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.uam.predictionapp.contants.AppConstants.TOKEN_HASH_MAP;

@Component
public class AppIntercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            final String cookie = request.getHeader("cookie");
            if(request.getMethod().equals(HttpMethod.OPTIONS) || request.getRequestURI().equals("/v1/usermgmt/login")){
                return true;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            final TokenDto tokenDto = objectMapper.readValue(cookie, TokenDto.class);
            final Long userId = tokenDto.getUserId();
            final String token = tokenDto.getToken();
            if(TOKEN_HASH_MAP.get(userId).equals(token)){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception) throws Exception {}
}
