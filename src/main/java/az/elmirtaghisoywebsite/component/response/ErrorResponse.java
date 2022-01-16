package az.elmirtaghisoywebsite.component.response;

import az.elmirtaghisoywebsite.component.constraints.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    T response;
    private Status status;
    private LocalDateTime timestamp;
    private String traceMessage;

    public static <R> ErrorResponse<R> error(R errorMessage, Status status, String traceMessage) {
        ErrorResponse<R> response = new ErrorResponse<>();
        response.setStatus(status);
        response.setTimestamp(LocalDateTime.now());
        response.setResponse(errorMessage);
        response.setTraceMessage(traceMessage);
        return response;
    }

    public static <R> ErrorResponse<R> error(Status status, String traceMessage) {
        ErrorResponse<R> response = new ErrorResponse<>();
        response.setStatus(status);
        response.setTimestamp(LocalDateTime.now());
        response.setTraceMessage(traceMessage);
        return response;
    }

}