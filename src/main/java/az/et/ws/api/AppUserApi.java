package az.et.ws.api;

import az.et.ws.component.response.AppUserResponse;
import az.et.ws.component.response.SuccessResponse;
import az.et.ws.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AppUserApi {

    private final AppUserService appUserService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users")
    public SuccessResponse<Page<AppUserResponse>> getAllActiveUser(
            Pageable pageable
    ) {
        return SuccessResponse.fetch(appUserService.getAllActiveUser(pageable));
    }

}
