package az.elmirtaghisoywebsite.component.response;

import az.elmirtaghisoywebsite.component.constraints.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SuccessResponse<R> {

    private R data;
    private Status status;
    private LocalDateTime timestamp;

    public static <R> SuccessResponse<R> fetch(R data) {
        SuccessResponse<R> response = new SuccessResponse<R>();
        response.setStatus(Status.FETCH);
        response.setTimestamp(LocalDateTime.now());
        response.setData(data);
        return response;
    }

    public static <R> SuccessResponse<R> create() {
        SuccessResponse<R> response = new SuccessResponse<R>();
        response.setStatus(Status.CREATE);
        response.setTimestamp(LocalDateTime.now());
        return response;
    }

    public static <R> SuccessResponse<R> update() {
        SuccessResponse<R> response = new SuccessResponse<R>();
        response.setStatus(Status.UPDATE);
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
