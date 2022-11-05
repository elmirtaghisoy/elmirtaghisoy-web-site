package az.et.ws.config.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({FileProperties.class})
public class ApplicationPropertiesAutoConfiguration {
}
