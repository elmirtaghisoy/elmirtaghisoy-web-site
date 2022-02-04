package az.et.ws.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationApi {

//    private final AppUserService appUserService;

//    @PostMapping("/login")
//    public SuccessResponse<AuthResponse> login(@RequestBody LoginRequest request) {
//        return SuccessResponse.create(appUserService.login(request));
//    }
//
//    @PostMapping("/refresh-token")
//    public SuccessResponse<AuthResponse> refreshToken(@RequestBody RefreshTokeRequest request) {
//        return SuccessResponse.update(appUserService.refreshToken(request));
//    }

}
