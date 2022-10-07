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
            "/ws/**"
    );

    static boolean pathIsWhiteListed(String path) {
        return WHITE_LIST.contains(path);
    }

}
