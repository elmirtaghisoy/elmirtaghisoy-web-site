package az.et.ws.api;

import az.et.ws.component.entity.AuthenticationProvider;
import az.et.ws.component.model.AppUser;
import az.et.ws.component.request.LoginRequest;
import az.et.ws.component.request.RegistrationRequest;
import az.et.ws.component.response.AuthResponse;
import az.et.ws.component.response.SuccessResponse;
import az.et.ws.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@RequiredArgsConstructor
public class AuthenticationApi {

    private final AuthService authService;

    @PostMapping("/login/basic")
    public SuccessResponse<AuthResponse> loginBasic(
            @RequestBody LoginRequest loginRequest
    ) {
        return SuccessResponse.create(authService.login(loginRequest));
    }

    @PostMapping("/logout")
    public SuccessResponse<String> logout(
            @NotBlank @RequestHeader("Authorization") String bearerToken
    ) {
        authService.logout(bearerToken);
        return SuccessResponse.ok();
    }

    @PostMapping("/registration/{provider}")
    public SuccessResponse<AppUser> registration(
            @RequestBody RegistrationRequest request,
            @PathVariable AuthenticationProvider provider
    ) {
        AppUser newUser = authService.registration(request, provider);
        return SuccessResponse.create(newUser);
    }

    /*@PostMapping("/refresh-token")
    public SuccessResponse<AuthResponse> refreshToken(@RequestBody RefreshTokeRequest request) {
        return SuccessResponse.update(appUserService.refreshToken(request));
    }*/

}
