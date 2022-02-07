package az.et.ws.api;

import az.et.ws.component.response.SuccessResponse;
import az.et.ws.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@RequiredArgsConstructor
public class AuthenticationApi {

    private final AppUserService appUserService;

    @PostMapping("/logout")
    public SuccessResponse<String> logout(@RequestHeader("Authorization") @NotBlank String bearerToken) {
        appUserService.logout(bearerToken);
        return SuccessResponse.ok();
    }
//
//    @PostMapping("/refresh-token")
//    public SuccessResponse<AuthResponse> refreshToken(@RequestBody RefreshTokeRequest request) {
//        return SuccessResponse.update(appUserService.refreshToken(request));
//    }

}
