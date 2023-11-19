package com.crocobet.example.logging;

import com.crocobet.example.model.log.LogModel;
import com.crocobet.example.service.pulsar.PulsarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggableAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggableAspect.class);

    private final PulsarService pulsarService;

    private final ObjectMapper objectMapper;

    /**
     * Handle Loggable annotated method execution
     * Build Log object
     * Send to Pulsar
     *
     * @param proceedingJoinPoint ProceedingJoinPoint
     * @return Response object
     * @throws Throwable On eny exception
     */
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

    /**
     * Build and send log object to pulsar async
     *
     * @param startTime           Execution start time
     * @param proceedingJoinPoint ProceedingJoinPoint
     * @param proceed             Response object
     */
    private void log(Long startTime, ProceedingJoinPoint proceedingJoinPoint, Object proceed) {

        try {

            String endpoint = "";
            String remoteAddr = "";

            if(Objects.nonNull(RequestContextHolder.getRequestAttributes())) {

                HttpServletRequest request =
                        ((ServletRequestAttributes) Objects.requireNonNull(
                                RequestContextHolder.getRequestAttributes()))
                                .getRequest();

                endpoint = request.getRequestURI();
                remoteAddr = request.getRemoteAddr();
            }

            LogModel logModel = LogModel
                    .builder()
                    .execution(System.currentTimeMillis() - startTime)
                    .endpoint(endpoint)
                    .ip(remoteAddr)
                    .request(proceedingJoinPoint.getArgs())
                    .response(proceed)
                    .build();

            pulsarService.sendLogAsync(objectMapper.writeValueAsString(logModel));

        } catch (Exception e) {
            LOGGER.error("Unable to send log");
        }
    }
}
