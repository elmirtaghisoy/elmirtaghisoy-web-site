package az.et.ws.component.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GoogleUserResponse {
    private String name;
    private String surname;
    private String email;
    private String pic;
}
