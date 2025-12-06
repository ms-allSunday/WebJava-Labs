package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.AbstractIT;
import com.example.spacecatsmarket.exception.FeatureNotAvailableException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@TestPropertySource(properties = {
        "feature.cosmoCats.enabled=false",
        "feature.kittyProducts.enabled=false"
})
class CosmoCatServiceDisabledTest extends AbstractIT {

    @Autowired
    private CosmoCatService cosmoCatService;

    @Test
    @DisplayName("Should throw FeatureNotAvailableException when 'cosmoCats' feature is disabled")
    void getCosmoCatsDisabled() {
        assertThatThrownBy(() -> cosmoCatService.getCosmoCats())
                .isInstanceOf(FeatureNotAvailableException.class)
                .hasMessage("The feature 'cosmoCats' is currently disabled.");
    }

    @Test
    @DisplayName("Should throw FeatureNotAvailableException when 'kittyProducts' feature is disabled")
    void getKittyProductsDisabled() {
        assertThatThrownBy(() -> cosmoCatService.getKittyProducts())
                .isInstanceOf(FeatureNotAvailableException.class)
                .hasMessage("The feature 'kittyProducts' is currently disabled.");
    }
}
