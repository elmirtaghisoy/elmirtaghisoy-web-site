package az.et.ws.component.request;

import lombok.Data;

@Data
public class QRLoginRequest {
    private int googleCode;
    private String email;
}
