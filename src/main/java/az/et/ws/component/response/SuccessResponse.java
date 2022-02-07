package az.et.ws.component.response;

import az.et.ws.component.constraints.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SuccessResponse<R> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private R response;
    private Status status;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timestamp;

    public static <R> SuccessResponse<R> fetch(R data) {
        SuccessResponse<R> response = new SuccessResponse<R>();
        response.setStatus(Status.FETCH);
        response.setTimestamp(LocalDateTime.now());
        response.setResponse(data);
        return response;
    }

    public static <R> SuccessResponse<R> create(R data) {
        SuccessResponse<R> response = new SuccessResponse<R>();
        response.setStatus(Status.CREATE);
        response.setTimestamp(LocalDateTime.now());
        response.setResponse(data);
        return response;
    }

    public static <R> SuccessResponse<R> update(R data) {
        SuccessResponse<R> response = new SuccessResponse<R>();
        response.setStatus(Status.UPDATE);
        response.setTimestamp(LocalDateTime.now());
        response.setResponse(data);
        return response;
    }

    public static <R> SuccessResponse<R> ok() {
        SuccessResponse<R> response = new SuccessResponse<R>();
        response.setStatus(Status.SUCCESS);
        response.setTimestamp(LocalDateTime.now());
        return response;
    }

    public static <R> SuccessResponse<R> delete() {
        SuccessResponse<R> response = new SuccessResponse<R>();
        response.setStatus(Status.DELETE);
        response.setTimestamp(LocalDateTime.now());
        return response;
    }

}
