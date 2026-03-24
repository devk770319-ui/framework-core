package com.framwork.core.common.parameter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class ParameterConventionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler
    ) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        for (Parameter parameter : handlerMethod.getMethod().getParameters()) {
            if (!isUserInputParameter(parameter)) {
                continue;
            }
            if (isVoOrRecord(parameter.getType())) {
                continue;
            }
            log.warn("전달 파라미터 방식이 잘 못되었습니다.");
            log.warn("method={}, parameterType={}", handlerMethod.getShortLogMessage(), parameter.getType().getName());
        }
        return true;
    }

    private boolean isUserInputParameter(Parameter parameter) {
        return parameter.isAnnotationPresent(RequestBody.class)
                || parameter.isAnnotationPresent(ModelAttribute.class)
                || parameter.isAnnotationPresent(RequestParam.class)
                || parameter.isAnnotationPresent(PathVariable.class)
                || parameter.isAnnotationPresent(RequestPart.class);
    }

    private boolean isVoOrRecord(Class<?> parameterType) {
        return parameterType.isRecord() || parameterType.getSimpleName().endsWith("Vo");
    }
}
