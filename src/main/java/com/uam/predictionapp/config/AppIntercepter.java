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
        /*try {
            final String cookie = request.getHeader("cookie");
            if(canFilter(request)){
                return true;
            }
            if(cookie.equals(""))
                return false;
            ObjectMapper objectMapper = new ObjectMapper();
            final TokenDto tokenDto = objectMapper.readValue(cookie, TokenDto.class);
            if(tokenDto == null){
                return false;
            }
            final Long userId = tokenDto.getUserId();
            final String token = tokenDto.getToken();
            final String actualToken = TOKEN_HASH_MAP.get(userId);
            if (actualToken == null){
                return false;
            }
            if(actualToken.equals(token)){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/
        return true;
    }

    private boolean canFilter(HttpServletRequest request) {
        return request.getMethod().equals(HttpMethod.OPTIONS) ||
                request.getRequestURI().equals("/v1/usermgmt/login") ||
                request.getRequestURI().equals("/v1/usermgmt/users") ||
                request.getRequestURI().equals("/v1/resultMgmt/results/calculate");
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception) throws Exception {}
}
