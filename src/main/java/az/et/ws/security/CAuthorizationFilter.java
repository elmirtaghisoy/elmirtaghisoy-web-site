package az.et.ws.security;

import az.et.ws.component.exception.InvalidTokenException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static az.et.ws.component.constraints.Status.EXPIRED_TOKEN;
import static az.et.ws.component.constraints.Status.INVALID_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
public class CAuthorizationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (request.getServletPath().equals("/login") || request.getServletPath().equals("/api/v1/refresh-token/**") || request.getServletPath().equals("/logout/")) {
                filterChain.doFilter(request, response);
            } else {
                String authorizationHeader = request.getHeader(AUTHORIZATION);
                if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
                    jwtUtil.verifyToken(request, response, filterChain, authorizationHeader);
                    filterChain.doFilter(request, response);
                } else {
                    filterChain.doFilter(request, response);
                }
            }
        } catch (JWTDecodeException ex) {
            jwtUtil.buildErrorResponse(response, INVALID_TOKEN);
        } catch (TokenExpiredException ex) {
            jwtUtil.buildErrorResponse(response, EXPIRED_TOKEN);
        }
    }
}
