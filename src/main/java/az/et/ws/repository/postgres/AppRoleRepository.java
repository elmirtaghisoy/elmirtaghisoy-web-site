package az.et.ws.repository.postgres;

import az.et.ws.component.entity.AppRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRoleRepository extends JpaRepository<AppRoleEntity, Long> {
    AppRoleEntity findByName(String name);
}
