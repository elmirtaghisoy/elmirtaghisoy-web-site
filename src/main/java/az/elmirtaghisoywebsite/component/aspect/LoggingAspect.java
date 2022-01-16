package az.elmirtaghisoywebsite.component.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void serviceBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Pointcut("within(az.elmirtaghisoywebsite.service..*)")
    public void servicePackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

//    @Pointcut("execution(public az.company.myevent.component.model.standard.RestResponse az.company.myevent.service.*.*(..))")
//    public void webServices() {
//    }
//
//    @Pointcut("execution(public az.company.myevent.component.model.standard.RestResponse az.company.myevent.component.aspect.GlobalExceptionHandler*.*(..))")
//    public void exceptionHandlers() {
//
//    }
//
//    @Before(value = "webServices() && args(request)")
//    public void beforeExecuting(JoinPoint joinPoint, StandardRequest request) {
//        log.info("#########################");
//        log.info("###### Starting : {} ", joinPoint.getSignature().getName());
//        log.info("****** Request : {}", request.toString());
//    }
//
//    @AfterReturning(value = "webServices() || exceptionHandlers()", returning = "response")
//    public void afterReturningSuccess(Object response) {
//        log.info("****** Response : {}", response.toString());
//    }
//
//    @After(value = "webServices()")
//    public void afterExecuting(JoinPoint joinPoint) {
//        log.info("###### Ending : {} ", joinPoint.getSignature().getName());
//    }

    @Around("servicePackagePointcut() && serviceBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        var signature = joinPoint.getSignature();
        if (log.isDebugEnabled()) {
            log.debug("Enter: {}.{}() with argument[s] = {}", signature.getDeclaringTypeName(), signature.getName(),
                    Arrays.toString(joinPoint.getArgs())
            );
        }
        try {
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                log.debug("Exit: {}.{}() with result = {}", signature.getDeclaringTypeName(),
                        signature.getName(), result
                );
            }
            return result;
        } catch (IllegalArgumentException ex) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    signature.getDeclaringTypeName(), signature.getName()
            );
            throw ex;
        }
    }
}
