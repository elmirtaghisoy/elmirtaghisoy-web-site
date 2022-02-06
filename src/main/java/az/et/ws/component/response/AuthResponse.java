package az.et.ws.component.response;

import az.et.ws.component.model.Token;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private Token token;
}
