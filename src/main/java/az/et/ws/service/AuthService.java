package az.et.ws.service;

import az.et.ws.component.mapper.ObjectMapper;
import az.et.ws.component.model.AppUser;
import az.et.ws.component.request.LoginRequest;
import az.et.ws.component.response.AuthResponse;
import az.et.ws.repository.postgres.AppUserRepository;
import az.et.ws.security.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Override
    public AppUser loadUserByUsername(String username) {
        return objectMapper.createAppUser(appUserRepository.findByUsername(username));
    }

    public AuthResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken credential = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(credential);
        return jwtUtil.createTokenAndSession(authentication);
    }

    public void logout(String bearerToken) {
        jwtUtil.invalidateToken(bearerToken);
    }

/*
    public AuthResponse refreshToken(RefreshTokeRequest request) {
        String refreshToken = request.getRefreshToken();
        if (Objects.nonNull(refreshToken) && refreshToken.startsWith("Bearer ")) {
            try {
                String token = refreshToken.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                String username = decodedJWT.getSubject();
                User user = (User) loadUserByUsername(username);
                return generateToken(user);
            } catch (Exception exception) {
                response.sendError(FORBIDDEN.value());
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
        return null;
    }
*/

}
