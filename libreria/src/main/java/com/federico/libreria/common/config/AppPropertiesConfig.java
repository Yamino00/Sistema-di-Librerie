package com.federico.libreria.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        HttpClientProperties.class,
})
public class AppPropertiesConfig {}