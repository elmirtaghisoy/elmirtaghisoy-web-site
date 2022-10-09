package az.et.ws.service;

import az.et.ws.component.mapper.ObjectMapper;
import az.et.ws.component.response.AppUserResponse;
import az.et.ws.component.views.AppUserView;
import az.et.ws.repository.postgres.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;

    private final ObjectMapper objectMapper;

    public Page<AppUserResponse> getAllActiveUser(Pageable pageable) {

        List<AppUserResponse> response = appUserRepository
                .findAllProjectedByAccountNonLockedIsTrue(AppUserView.class, pageable)
                .stream()
                .map(objectMapper::v2r)
                .collect(Collectors.toList());

        return new PageImpl<>(response);
    }
}
