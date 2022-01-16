package az.elmirtaghisoywebsite.component.aspect;


import az.elmirtaghisoywebsite.component.constraints.Status;
import az.elmirtaghisoywebsite.component.model.ValidationError;
import az.elmirtaghisoywebsite.component.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse<List<ValidationError>> handleFieldValidation(ConstraintViolationException exception) {
        List<ValidationError> violations = exception.getConstraintViolations()
                .stream()
                .map(violation -> new ValidationError(violation.getPropertyPath().toString(), violation.getMessage()))
                .collect(Collectors.toList());
        return ErrorResponse.error(violations, Status.VALIDATION_ERROR, ExceptionUtils.getStackTrace(exception));
    }

    @ExceptionHandler({EntityNotFoundException.class, EmptyResultDataAccessException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse<String> dataNotFound(Exception exception) {
        return ErrorResponse.error(Status.DATA_NOT_FOUND, ExceptionUtils.getStackTrace(exception));
    }

    @ExceptionHandler(Throwable.class)
    public ErrorResponse<String> unknownError(Throwable throwable) {
        return ErrorResponse.error(Status.UNKNOWN_ERROR, ExceptionUtils.getStackTrace(throwable));
    }

}
