package az.et.ws.component.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("within(az.et.ws.api..*)")
    public void services() {
    }

    @Pointcut("within(az.et.ws.component.aspect.GlobalExceptionHandler)")
    public void exceptions() {

    }

    @Before("services()")
    public void beforeExecuting(JoinPoint joinPoint) {
        log.info("Executing",
                kv("method.name", joinPoint.getSignature().getName()),
                kv("request", joinPoint.getArgs())
        );
    }

    @AfterReturning(value = "services()", returning = "response")
    public void afterReturningSuccess(JoinPoint joinPoint, Object response) throws JsonProcessingException {
        log.info("Successful Executing",
                kv("method.name", joinPoint.getSignature().getName()),
                kv("response", new ObjectMapper().writeValueAsString(response))
        );
    }

    @AfterThrowing("services()")
    public void afterThrowingError(JoinPoint joinPoint) {
        log.info("Failed Executing",
                kv("method.name", joinPoint.getSignature().getName())
        );
    }

    @After("services()")
    public void afterExecuting(JoinPoint joinPoint) {
        log.info("Successful Finished",
                kv("method.name", joinPoint.getSignature().getName())
        );
    }

    @AfterReturning(value = "exceptions()", returning = "response")
    public void afterReturningError(JoinPoint joinPoint, Object response) throws JsonProcessingException {
        log.error("Failed Executing",
                kv("method.name", joinPoint.getSignature().getName()),
                kv("response", new ObjectMapper().writeValueAsString(response))
        );
    }
}
