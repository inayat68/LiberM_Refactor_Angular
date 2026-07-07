package com.aws.carddemo.angular;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class ObjectVarargsArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isArray()
                && parameter.getParameterType().getComponentType().equals(Object.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        // simplest: always provide empty varargs
        return new Object[0];

        // If you want to pass something:
        // HttpServletRequest req = webRequest.getNativeRequest(HttpServletRequest.class);
        // return new Object[] { req.getHeader("X-Something") };
    }
}
