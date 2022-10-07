package az.et.ws.component.interceptor;

import az.et.ws.component.entity.AppUserEntity;
import az.et.ws.kafka.ActionInfo;
import az.et.ws.kafka.ActionProducer;
import az.et.ws.repository.postgres.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class AllRequestInterceptor implements HandlerInterceptor {

    @Autowired
    private ActionProducer producer;

    @Autowired
    private AppUserRepository appUserRepository;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!request.getRequestURI().contains("login")) {
            AppUserEntity appUser = appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            if (Objects.nonNull(appUser)) {
                producer.sendUserAction(new ActionInfo(Math.toIntExact(appUser.getId()), appUser.getEmail(), request.getRequestURI(), LocalDateTime.now()));
            }
        }
        return true;
    }
}