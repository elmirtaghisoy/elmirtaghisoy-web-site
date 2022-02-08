package az.et.ws.security;

import az.et.ws.component.constraints.Status;
import az.et.ws.component.exception.InvalidTokenException;
import az.et.ws.component.response.ErrorResponse;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
public class CAuthorizationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (request.getServletPath().equals("/login/") || request.getServletPath().equals("/api/v1/refresh-token/**") || request.getServletPath().equals("/logout/")) {
                filterChain.doFilter(request, response);
            } else {
                String authorizationHeader = request.getHeader(AUTHORIZATION);
                if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
                    String token = jwtUtil.verifyToken(authorizationHeader);
                    JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtUtil.getJwtSecret())).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role));
                    });
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } else {
                    filterChain.doFilter(request, response);
                }
            }
        } catch (JWTDecodeException | InvalidTokenException | TokenExpiredException ex) {
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(
                    response.getOutputStream(),
                    ErrorResponse.error(Status.INVALID_TOKEN)
            );
        }
    }
}
