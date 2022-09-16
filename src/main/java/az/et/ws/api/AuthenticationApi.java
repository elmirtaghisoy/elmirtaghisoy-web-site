package az.et.ws.api;

import az.et.ws.component.entity.AuthenticationProvider;
import az.et.ws.component.model.AppUser;
import az.et.ws.component.request.BasicLoginRequest;
import az.et.ws.component.request.QRLoginRequest;
import az.et.ws.component.request.RegistrationRequest;
import az.et.ws.component.response.AuthResponse;
import az.et.ws.component.response.SuccessResponse;
import az.et.ws.service.AuthService;
import az.et.ws.service.TOTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

@RestController
@RequiredArgsConstructor
public class AuthenticationApi {

    private final AuthService authService;
    private final TOTPService totpService;

    @PostMapping("/login/basic")
    public SuccessResponse<AuthResponse> loginBasic(
            @RequestBody BasicLoginRequest basicLoginRequest
    ) {
        return SuccessResponse.create(authService.login(basicLoginRequest));
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

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/generate/authenticator-qr")
    public SuccessResponse<String> generateQRAuthenticator(
            HttpServletResponse response
    ) {
        totpService.generateQRAuthenticator(response);
        return SuccessResponse.fetch("");
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login/qr")
    public SuccessResponse<AuthResponse> loginWithQr(
            @RequestBody QRLoginRequest qrLoginRequest
    ) {
        return SuccessResponse.fetch(totpService.loginWithQr(qrLoginRequest));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/ping")
    public SuccessResponse<String> ping() {
        return SuccessResponse.fetch("Service is work");
    }

}
