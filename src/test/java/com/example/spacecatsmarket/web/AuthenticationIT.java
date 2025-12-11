package com.example.spacecatsmarket.web;

import com.example.spacecatsmarket.AbstractIT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Authentication Integration Tests")
class AuthenticationIT extends AbstractIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("API Key: Should allow access with valid API Key")
    void shouldAllowAccessWithValidApiKey() throws Exception {
        mockMvc.perform(get("/api/v1/products")
                        .header("X-Api-Key", "admin-secret-local"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("API Key: Should deny access with invalid API Key (401)")
    void shouldDenyAccessWithInvalidApiKey() throws Exception {
        mockMvc.perform(get("/api/v1/products")
                        .header("X-Api-Key", "wrong-password-123"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("JWT: Should allow access with valid Mock JWT token")
    void shouldAllowAccessWithMockJwt() throws Exception {
        mockMvc.perform(get("/api/v1/products")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GitHub: Should allow access after Mock OAuth2 Login")
    void shouldAllowAccessWithMockOAuth2Login() throws Exception {
        mockMvc.perform(get("/api/v1/products")
                        .with(oauth2Login()
                                .attributes(attrs -> {
                                    attrs.put("login", "test-user");
                                    attrs.put("name", "Test Cosmo Cat");
                                })))
                .andExpect(status().isOk());
    }
}