package com.uam.predictionapp.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uam.predictionapp.model.TokenDto;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.uam.predictionapp.contants.AppConstants.TOKEN_HASH_MAP;

@Component
public class AppFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter
            (ServletRequest request, ServletResponse response, FilterChain filterchain)
            throws IOException, ServletException {
        System.out.println("Remote Host:" + request.getRemoteHost());
        System.out.println("Remote Address:" + request.getRemoteAddr());
        final HttpServletResponse servletResponse = (HttpServletResponse) response;

        try {
            final HttpServletRequest servletRequest = (HttpServletRequest) request;
            final String cookie = servletRequest.getHeader("cookie");
            if (canFilter(servletRequest)) {
                filterchain.doFilter(request, response);
                return;
            }
            if (cookie == null || cookie.isEmpty()) {
                servletResponse.sendError(HttpStatus.UNAUTHORIZED.value());
                return;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            final TokenDto tokenDto = objectMapper.readValue(cookie, TokenDto.class);
            if (tokenDto == null) {
                servletResponse.sendError(HttpStatus.UNAUTHORIZED.value());
                return;
            }
            final Long userId = tokenDto.getUserId();
            final String token = tokenDto.getToken();
            final String actualToken = TOKEN_HASH_MAP.get(userId);
            if (actualToken == null) {
                servletResponse.sendError(HttpStatus.UNAUTHORIZED.value());
                return;
            }
            if (actualToken.equals(token)) {
                filterchain.doFilter(request, response);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            servletResponse.sendError(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        filterchain.doFilter(request, response);
    }

    private boolean canFilter(HttpServletRequest request) {
        return request.getMethod().equals(HttpMethod.OPTIONS) ||
                request.getRequestURI().equals("/v1/usermgmt/login") ||
                request.getRequestURI().equals("/v1/usermgmt/users") ||
                request.getRequestURI().equals("/v1/resultMgmt/results/calculate");
    }

    @Override
    public void init(FilterConfig filterconfig) throws ServletException {
    }
}
