package com.example.spacecatsmarket.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties
public class FeatureToggleProperties {
    private Map<String, FeatureConfig> feature = new HashMap<>();

    public Map<String, FeatureConfig> getFeature() {
        return feature;
    }

    public void setFeature(Map<String, FeatureConfig> feature) {
        this.feature = feature;
    }

    public static class FeatureConfig {
        private boolean enabled;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
}
