package az.et.ws.repository.postgres;

import az.et.ws.component.entity.AppUserEntity;
import az.et.ws.component.views.AppUserView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends PagingAndSortingRepository<AppUserEntity, Long> {
    AppUserEntity findByEmail(String email);

    Boolean existsByEmail(String email);

    Page<AppUserView> findAllProjectedByAccountNonLockedIsTrue(Class<AppUserView> projection, Pageable pageable);
}
