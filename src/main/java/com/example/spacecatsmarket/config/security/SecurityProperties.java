package com.example.spacecatsmarket.config.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "application.security")
public class SecurityProperties {

    private String apiKeyHeader;
    private List<ApiKey> keys;

    @Getter
    @Setter
    public static class ApiKey {
        private String value;
        private String role;
    }
}