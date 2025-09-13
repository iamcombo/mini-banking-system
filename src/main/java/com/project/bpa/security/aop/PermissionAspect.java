package com.project.bpa.security.aop;

import com.project.bpa.exception.AccessDeniedException;
import com.project.bpa.security.annotation.RequiresPermission;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class PermissionAspect {

    private final PermissionEvaluator permissionEvaluator;

    public PermissionAspect(PermissionEvaluator permissionEvaluator) {
        this.permissionEvaluator = permissionEvaluator;
    }

    @Around("@annotation(com.project.bpa.security.annotation.RequiresPermission)")
    public Object checkPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        // Get the method being called
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // Get the permission annotation
        RequiresPermission requiresPermission = method.getAnnotation(RequiresPermission.class);
        String permission = requiresPermission.value();

        // Get the authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user has the required permission
        if (permissionEvaluator.hasPermission(authentication, null, permission)) {
            return joinPoint.proceed();
        } else {
            throw new AccessDeniedException("You don't have permission to access this resource");
        }
    }
}
