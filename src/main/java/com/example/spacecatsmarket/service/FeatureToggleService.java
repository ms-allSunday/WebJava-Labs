package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.config.FeatureToggleProperties;
import org.springframework.stereotype.Service;

@Service
public class FeatureToggleService {

    private final FeatureToggleProperties featureProperties;

    public FeatureToggleService(FeatureToggleProperties featureProperties) {
        this.featureProperties = featureProperties;
    }

    public boolean isFeatureEnabled(String featureName) {
        FeatureToggleProperties.FeatureConfig config = featureProperties.getFeature().get(featureName);

        if (config != null) {
            return config.isEnabled();
        }
        return false;
    }
}
