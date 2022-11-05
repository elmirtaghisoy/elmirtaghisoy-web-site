package az.et.ws.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.file-properties")
public class FileProperties {

    @NotNull
    private String uploadPath;

}
