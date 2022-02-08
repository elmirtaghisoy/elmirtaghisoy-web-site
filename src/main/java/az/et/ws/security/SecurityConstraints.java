package az.et.ws.security;

public interface SecurityConstraints {
    String[] WHITE_LIST =
            {
                    "/refresh-token/**",
                    "/logout/**",
                    "/login/**"
            };
}