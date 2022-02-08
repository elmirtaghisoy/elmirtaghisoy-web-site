package az.et.ws.component.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Token {
    private String accessToken;
    private String refreshToken;
}
