package com.airnz.email.emailsample.interceptor;

import java.lang.reflect.Method;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.airnz.email.emailsample.annotation.UserToken;
import com.airnz.email.emailsample.exception.UnauthorizedException;
import com.airnz.email.emailsample.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String useToken = httpServletRequest.getHeader("authorization");
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //check if it needs Auth
        if (method.isAnnotationPresent(UserToken.class)) {
            String token = Utils.getToken(useToken);
            boolean isValid = Utils.isValidEmail(token);
            if (!isValid) {
                throw new UnauthorizedException("token is invalid");
            } else {
                return true;
            }
        }
        return true;
    }
}
