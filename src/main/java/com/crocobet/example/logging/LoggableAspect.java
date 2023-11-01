package com.crocobet.example.logging;

import com.crocobet.example.model.log.LogModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggableAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggableAspect.class);

    private final ObjectMapper objectMapper;

    @Around(value = "@annotation(Loggable)")
    public Object loggable(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        Object proceed = null;

        try {
            proceed = proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            proceed = exception.getMessage();
            throw exception;
        } finally {
            log(startTime, proceedingJoinPoint, proceed);
        }

        return proceed;

    }

    private void log(Long startTime, ProceedingJoinPoint proceedingJoinPoint, Object proceed) {

        try {

            HttpServletRequest request =
                    ((ServletRequestAttributes) Objects.requireNonNull(
                            RequestContextHolder.getRequestAttributes()))
                            .getRequest();

            LogModel loggingModel = LogModel
                    .builder()
                    .execution(System.currentTimeMillis() - startTime)
                    .endpoint(request.getRequestURI())
                    .ip(request.getRemoteAddr())
                    .request(proceedingJoinPoint.getArgs())
                    .response(proceed)
                    .build();

            LOGGER.info(objectMapper.writeValueAsString(loggingModel));

        } catch (Exception e) {
            LOGGER.error("Unable to send log");
        }

    }

}
