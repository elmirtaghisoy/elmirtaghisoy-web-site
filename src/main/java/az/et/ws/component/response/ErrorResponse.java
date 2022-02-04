package az.et.ws.component.response;

import az.et.ws.component.constraints.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    T response;
    private Status status;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timestamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String traceMessage;

    public static <R> ErrorResponse<R> error(R errorMessage, Status status, String traceMessage) {
        return ErrorResponse.<R>builder()
                .status(status)
                .timestamp(LocalDateTime.now())
                .response(errorMessage)
                .traceMessage(traceMessage)
                .build();
    }

    public static <R> ErrorResponse<R> error(Status status, String traceMessage) {
        return ErrorResponse.<R>builder()
                .status(status)
                .timestamp(LocalDateTime.now())
                .traceMessage(traceMessage)
                .build();
    }

    public static <R> ErrorResponse<R> error(Status status) {
        return ErrorResponse.<R>builder()
                .status(status)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
