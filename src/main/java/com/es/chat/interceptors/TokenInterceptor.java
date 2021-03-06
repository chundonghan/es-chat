package com.es.chat.interceptors;

import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class TokenInterceptor extends HandlerInterceptorAdapter
{
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        if (handler instanceof HandlerMethod)
        {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(Token.class);
            if (annotation != null)
            {
                boolean needSaveSession = annotation.save();
                if (needSaveSession)
                {
                    String token = UUID.randomUUID().toString();
                    request.getSession(true).setAttribute("token", token);
                    request.setAttribute("token", token);
                }
                boolean needRemoveSession = annotation.remove();
                if (needRemoveSession)
                {
                    if (isRepeatSubmit(request))
                    {
                        logger.warn("please don't repeat submit,url:" + request.getServletPath());
                        return false;
                    }
                    request.getSession(true).removeAttribute("token");
                }
            }
            return true;
        }
        else
        {
            return super.preHandle(request, response, handler);
        }
    }

    private boolean isRepeatSubmit(HttpServletRequest request)
    {
        String serverToken = (String) request.getSession(true).getAttribute("token");
        if (serverToken == null)
        {
            return true;
        }
        String clinetToken = request.getParameter("token");
        System.out.println("clientToken: "+clinetToken);
        if (clinetToken == null)
        {
            return true;
        }
        if (!serverToken.equals(clinetToken))
        {
            return true;
        }
        return false;
    }
}
