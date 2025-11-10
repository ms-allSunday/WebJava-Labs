package com.example.spacecatsmarket.featuretoggle;

import com.example.spacecatsmarket.exception.FeatureNotAvailableException;
import com.example.spacecatsmarket.service.FeatureToggleService;
import org.springframework.stereotype.Component;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
@Aspect
@Component
public class FeatureToggleAspect {

    private final FeatureToggleService featureToggleService;

    public FeatureToggleAspect(FeatureToggleService featureToggleService) {
        this.featureToggleService = featureToggleService;
    }

    @Around("@annotation(com.example.spacecatsmarket.featuretoggle.FeatureToggle)")
    public Object checkFeatureToggle(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        FeatureToggle featureToggleAnnotation = method.getAnnotation(FeatureToggle.class);

        String featureName = featureToggleAnnotation.feature();

        if (featureToggleService.isFeatureEnabled(featureName)) {
            return joinPoint.proceed();
        } else {
            throw new FeatureNotAvailableException(featureName);
        }
    }
}
