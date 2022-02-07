package az.et.ws.security;

public interface SecurityConstraints {
    String[] WHITE_LIST =
            {
                    "/api/v1/refresh-token/**",
                    "/logout/**"
            };
}