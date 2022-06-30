package az.et.ws.service;

import az.et.ws.component.entity.AppRoleEntity;
import az.et.ws.component.entity.AuthenticationProvider;
import az.et.ws.component.exception.UserAlreadyExistsException;
import az.et.ws.component.mapper.ObjectMapper;
import az.et.ws.component.model.AppUser;
import az.et.ws.component.request.LoginRequest;
import az.et.ws.component.request.RegistrationRequest;
import az.et.ws.component.response.AuthResponse;
import az.et.ws.repository.postgres.AppRoleRepository;
import az.et.ws.repository.postgres.AppUserRepository;
import az.et.ws.security.JwtUtil;
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
    private final JwtUtil jwtUtil;

    private final AppRoleRepository appRoleRepository;

    @Override
    public AppUser loadUserByUsername(String email) {
        return objectMapper.generateAppUser(appUserRepository.findByEmail(email));
    }

    public AuthResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken credential = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(credential);
        AppUser appUser = (AppUser) authentication.getPrincipal();
        return jwtUtil.createTokenAndSession(appUser);
    }

    public void logout(String bearerToken) {
        jwtUtil.invalidateToken(bearerToken);
    }

    public AppUser registration(RegistrationRequest request, AuthenticationProvider authenticationProvider) {
        checkEmailAlreadyIsExists(request.getEmail());
        AppRoleEntity appRole = appRoleRepository.findByName("USER");
        return objectMapper.generateAppUser(appUserRepository.save(objectMapper.createNewUser(request, authenticationProvider, appRole)));
    }

    public void checkEmailAlreadyIsExists(String email) {
        if (appUserRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        }
    }

    /*public AuthResponse refreshToken(RefreshTokeRequest request) {
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
    }*/

}
