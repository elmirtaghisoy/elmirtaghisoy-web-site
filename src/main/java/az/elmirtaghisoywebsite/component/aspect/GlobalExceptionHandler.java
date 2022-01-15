package az.elmirtaghisoywebsite.component.aspect;


import az.elmirtaghisoywebsite.component.constraints.Status;
import az.elmirtaghisoywebsite.component.model.ValidationError;
import az.elmirtaghisoywebsite.component.response.ErrorResponse;
import az.elmirtaghisoywebsite.util.Translator;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse<List<ValidationError>> handleFieldValidation(ConstraintViolationException exception) {
        List<ValidationError> violations = exception.getConstraintViolations()
                .stream()
                .map(violation -> new ValidationError(violation.getPropertyPath().toString(), violation.getMessage()))
                .collect(Collectors.toList());

        return ErrorResponse.error(violations, Status.VALIDATION_ERROR);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ErrorResponse<String> handleEmptyResultDataAccessException() {
        return ErrorResponse.error(Status.DATA_NOT_FOUND);
    }

//    @ExceptionHandler(EntityNotFoundException.class)
//    public SuccessResponse entityNotFound() {
//        SuccessResponse successResponse = new SuccessResponse();
//        successResponse.setAppStatus(Status.DATA_NOT_FOUND);
//        return null;
//    }


//    @ExceptionHandler(Throwable.class).
//    public StandardResponse handleDatabaseProcessException(Throwable throwable) {
//        StandardResponse standardResponse = new StandardResponse();
//        standardResponse.setErrorResponse(new ErrorResponse("", throwable.getMessage()));
//        standardResponse.setAppStatus(AppStatus.INTERNAL_ERROR);
//        standardResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//        return standardResponse;
//    }

}
