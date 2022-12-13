package az.et.ws.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
@ComponentScan("az.et.ws.component.job")
public class BatchConfig {
}
