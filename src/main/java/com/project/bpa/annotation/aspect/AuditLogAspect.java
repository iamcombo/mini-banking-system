package com.project.bpa.annotation.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bpa.annotation.AuditLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

@Aspect
@Component
public class AuditLogAspect {

    private static final Logger log = LoggerFactory.getLogger(AuditLogAspect.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Around("@annotation(auditLog)")
    public Object around(ProceedingJoinPoint joinPoint, AuditLog auditLog) throws Throwable {
        long startTime = System.currentTimeMillis();

        // Generate unique trace ID for this request
        String traceId = UUID.randomUUID().toString();
        MDC.put("traceId", traceId);

        try {
            Object[] args = joinPoint.getArgs();
            String methodName = joinPoint.getSignature().toShortString();

            // Log request details
            log.info("Method '{}' called with arguments: {}",
                    methodName,
                    Arrays.stream(args)
                            .map(arg -> {
                                try {
                                    return objectMapper.writeValueAsString(arg);
                                } catch (Exception e) {
                                    return String.valueOf(arg);
                                }
                            })
                            .toArray()
            );

            Object result = joinPoint.proceed(); // Call the method

            long endTime = System.currentTimeMillis();
            log.info("Method '{}' executed in {} ms", methodName, endTime - startTime);

            return result;
        } catch (Throwable throwable) {
            log.error("Exception in {}", joinPoint.getSignature());
            throw throwable;
        } finally {
            // Clean up MDC to prevent memory leaks
            MDC.remove("traceId");
        }
    }
}
