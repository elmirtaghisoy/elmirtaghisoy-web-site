package az.et.ws.component.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

import static az.et.ws.component.entity.TokenEntity.HASH_NAME;
import static az.et.ws.component.entity.TokenEntity.TYPE_NAME;

//@Entity
//@Table(name = "user_token")
//@Getter
//@Setter
//@NoArgsConstructor
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias(TYPE_NAME)
@RedisHash(HASH_NAME)
public class TokenEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String HASH_NAME = "ETWSToken";
    public static final String TYPE_NAME = HASH_NAME + "Type";

    @Id
    @Column(name = "id")
    private Long id;

    @Indexed
    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "user_id")
    private Long userId;

}
