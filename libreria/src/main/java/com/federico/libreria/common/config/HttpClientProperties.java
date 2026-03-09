package com.federico.libreria.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "security.http")
public class HttpClientProperties {

    private int connectTimeout;
    private int responseTimeout;
}