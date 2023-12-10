package az.et.ws.security;

import java.util.List;

public interface SecurityConstraints {

    List<String> WHITE_LIST = List.of(
            "/refresh-token/**",
            "/logout/**",
            "/login/**",
            "/registration/**",
            "/oauth2/authorize-client",
            "/ping/**",
            "/login/qr",
            "/ws/**",
            "/api/v1/files/upload/excel" // it is temporary
    );

    static boolean pathIsWhiteListed(String path) {
        return WHITE_LIST.contains(path);
    }

}
