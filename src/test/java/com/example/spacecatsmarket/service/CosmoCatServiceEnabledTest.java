package com.example.spacecatsmarket.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
        "feature.cosmoCats.enabled=true",
        "feature.kittyProducts.enabled=true"
})
class CosmoCatServiceEnabledTest {

    @Autowired
    private CosmoCatService cosmoCatService;

    @Test
    @DisplayName("Should return cosmo cats list when 'cosmoCats' feature is enabled")
    void getCosmoCatsEnabled() {
        List<String> cats = cosmoCatService.getCosmoCats();

        assertThat(cats)
                .isNotEmpty()
                .contains("Orion", "Nova", "Comet", "Galaxy");
    }

    @Test
    @DisplayName("Should return kitty products list when 'kittyProducts' feature is enabled")
    void getKittyProductsEnabled() {
        List<String> products = cosmoCatService.getKittyProducts();

        assertThat(products)
                .isNotEmpty()
                .contains("Meteor Ball", "Comet Scratcher", "Nebula Bed");
    }
}
