package az.et.ws.component.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
