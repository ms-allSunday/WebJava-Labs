package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.featuretoggle.FeatureToggle;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CosmoCatService {
    @FeatureToggle(feature = "cosmoCats")
    public List<String> getCosmoCats() {
        System.out.println("LOG: Executing getCosmoCats() method...");
        return Arrays.asList(
                "Orion",
                "Nova",
                "Comet",
                "Galaxy"
        );
    }

    @FeatureToggle(feature = "kittyProducts")
    public List<String> getKittyProducts() {
        System.out.println("LOG: Executing getKittyProducts() method...");
        return Arrays.asList(
                "Meteor Ball",
                "Comet Scratcher",
                "Nebula Bed"
        );
    }
}