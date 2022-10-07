package az.et.ws.service;

import az.et.ws.component.mapper.ObjectMapper;
import az.et.ws.component.response.AppUserResponse;
import az.et.ws.repository.postgres.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final ObjectMapper objectMapper;

    public List<AppUserResponse> getAllActiveUsers() {
        return appUserRepository
                .findAll()
                .stream()
                .map(objectMapper::e2r)
                .collect(Collectors.toList());
    }
}
