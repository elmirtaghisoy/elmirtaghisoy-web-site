package az.et.ws.repository.redis;

import az.et.ws.component.entity.TokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<TokenEntity, String> {
    Optional<TokenEntity> findByAccessToken(String token);

    void deleteByAccessToken(String token);
}
