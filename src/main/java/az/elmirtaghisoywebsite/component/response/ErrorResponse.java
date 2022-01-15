package az.elmirtaghisoywebsite.component.response;

import az.elmirtaghisoywebsite.component.constraints.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse<T> {

    T response;
    private Status status;
    private LocalDateTime timestamp;

    public static <R> ErrorResponse<R> error(R errorMessage, Status status) {
        ErrorResponse<R> response = new ErrorResponse<>();
        response.setStatus(status);
        response.setTimestamp(LocalDateTime.now());
        response.setResponse(errorMessage);
        return response;
    }

    public static <R> ErrorResponse<R> error(Status status) {
        ErrorResponse<R> response = new ErrorResponse<>();
        response.setStatus(status);
        response.setTimestamp(LocalDateTime.now());
        return response;
    }

}