package az.et.ws.component.request;

import lombok.Data;

@Data
public class BasicLoginRequest {
    private String email;
    private String password;
}
