package az.et.ws.component.aspect;


import az.et.ws.component.exception.EventNotAcceptableException;
import az.et.ws.component.exception.InvalidTokenException;
import az.et.ws.component.exception.UserAlreadyExistsException;
import az.et.ws.component.exception.WrongOTPCodeException;
import az.et.ws.component.model.ValidationError;
import az.et.ws.component.response.ErrorResponse;
import az.et.ws.util.Translator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

import static az.et.ws.component.constraints.Status.ACCESS_DENIED;
import static az.et.ws.component.constraints.Status.DATA_NOT_FOUND;
import static az.et.ws.component.constraints.Status.EVENT_NOT_ACCEPTABLE;
import static az.et.ws.component.constraints.Status.INVALID_TOKEN;
import static az.et.ws.component.constraints.Status.UNKNOWN_ERROR;
import static az.et.ws.component.constraints.Status.USER_ALREADY_EXISTS;
import static az.et.ws.component.constraints.Status.VALIDATION_ERROR;
import static az.et.ws.component.constraints.Status.WRONG_AUTH_PROVIDER;
import static az.et.ws.component.constraints.Status.WRONG_OTP_CODE;
import static az.et.ws.component.constraints.Status.WRONG_USERNAME_OR_PASSWORD;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse<List<ValidationError>> handleFieldValidation(ConstraintViolationException exception) {
        List<ValidationError> violations = exception.getConstraintViolations()
                .stream()
                .map(violation -> new ValidationError(
                        violation.getPropertyPath().toString(),
                        Translator.toLocale(violation.getMessage()))
                )
                .collect(Collectors.toList());
        return ErrorResponse.error(violations, VALIDATION_ERROR, ExceptionUtils.getStackTrace(exception));
    }

    @ExceptionHandler({EntityNotFoundException.class, EmptyResultDataAccessException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse<String> dataNotFound(Throwable throwable) {
        return ErrorResponse.error(DATA_NOT_FOUND, ExceptionUtils.getStackTrace(throwable));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponse<String> accessDeniedException() {
        return ErrorResponse.error(ACCESS_DENIED);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ErrorResponse<String> invalidTokenException() {
        return ErrorResponse.error(INVALID_TOKEN);
    }

    @ExceptionHandler({
            BadCredentialsException.class,
            InternalAuthenticationServiceException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse<String> badCredentialsException() {
        return ErrorResponse.error(WRONG_USERNAME_OR_PASSWORD);
    }

    @ExceptionHandler({UserAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse<String> userAlreadyExists(Throwable throwable) {
        return ErrorResponse.error(USER_ALREADY_EXISTS, throwable.getMessage());
    }

    @ExceptionHandler(ConversionFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse<String> wrongAuthenticationTypeException() {
        return ErrorResponse.error(WRONG_AUTH_PROVIDER);
    }

    @ExceptionHandler(EventNotAcceptableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorResponse<String> eventNotAcceptableException() {
        return ErrorResponse.error(EVENT_NOT_ACCEPTABLE);
    }

    @ExceptionHandler(WrongOTPCodeException.class)
    public ErrorResponse<String> wrongOtpCodeException() {
        return ErrorResponse.error(WRONG_OTP_CODE);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse<String> unknownError(Throwable throwable) {
        return ErrorResponse.error(UNKNOWN_ERROR, ExceptionUtils.getStackTrace(throwable));
    }

}

