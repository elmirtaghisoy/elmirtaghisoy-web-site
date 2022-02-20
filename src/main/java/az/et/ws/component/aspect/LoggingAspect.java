package az.et.ws.component.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("within(az.et.ws.service..*)")
    public void services() {
    }

    @Pointcut("within(az.et.ws.component.aspect.GlobalExceptionHandler)")
    public void exceptions() {

    }

    @Before("services()")
    public void beforeExecuting(JoinPoint joinPoint) {
        log.info("Starting: {} ", joinPoint.getSignature().getName());
        log.info("****** Request: {}", Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "services()", returning = "response")
    public void afterReturningSuccess(Object response) {
//        log.info("****** Response\t: {}", response.toString());
        log.info("Some message", kv("response", response));
    }

    @AfterThrowing("services()")
    public void afterThrowingError(JoinPoint joinPoint) {
        log.error("****** Response: {}", "Error occur in " + joinPoint.getSignature().getName());
    }

    @After("services()")
    public void afterExecuting(JoinPoint joinPoint) {
        log.info("###### Ending: {} ", joinPoint.getSignature().getName());
    }

    @AfterReturning(value = "exceptions()", returning = "response")
    public void afterReturningError(Object response) {
        log.error("****** Error Response: {}", response.toString());
    }
}
