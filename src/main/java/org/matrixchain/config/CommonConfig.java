package org.matrixchain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.matrixchain")
public class CommonConfig {
    @Bean
    public SystemProperties systemProperties() {
        return SystemProperties.getDefault();
    }
}
