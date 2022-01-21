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

    @Before(value = "services()")
    public void beforeExecuting(JoinPoint joinPoint) {
        log.info("#########################");
        log.info("#########################");
        log.info("###### Starting\t: {} ", joinPoint.getSignature().getName());
        log.info("****** Request\t\t: {}", Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "services()", returning = "response")
    public void afterReturningSuccess(Object response) {
        log.info("****** Response\t: {}", response.toString());
    }

    @AfterThrowing(value = "services()")
    public void afterThrowingError(JoinPoint joinPoint) {
        log.warn("****** Response\t: {}", "Error occur in " + joinPoint.getSignature().getName());
    }

    @After(value = "services()")
    public void afterExecuting(JoinPoint joinPoint) {
        log.info("###### Ending\t\t: {} ", joinPoint.getSignature().getName());
        log.info("#########################");
    }

    @AfterReturning(value = "exceptions()", returning = "response")
    public void afterReturningError(Object response) {
        log.warn("****** Error Response\t: {}", response.toString());
        log.info("#########################");
        log.info("#########################");
    }
}
