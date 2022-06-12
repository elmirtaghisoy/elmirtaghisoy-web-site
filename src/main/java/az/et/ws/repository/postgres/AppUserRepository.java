package az.et.ws.repository.postgres;

import az.et.ws.component.entity.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUserEntity, Long> {
    AppUserEntity findByUsername(String username);

    Boolean existsByEmail(String email);
}
