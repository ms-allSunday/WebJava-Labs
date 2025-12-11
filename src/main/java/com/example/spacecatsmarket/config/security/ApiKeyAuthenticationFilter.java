package com.example.spacecatsmarket.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private final SecurityProperties securityProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String headerName = securityProperties.getApiKeyHeader();
        String incomingKey = request.getHeader(headerName);

        if (incomingKey == null || incomingKey.isBlank() || securityProperties.getKeys() == null) {
            filterChain.doFilter(request, response);
            return;
        }

        securityProperties.getKeys().stream()
                .filter(k -> k.getValue().equals(incomingKey))
                .findFirst()
                .ifPresent(apiKey -> {
                    var authorities = Collections.singletonList(new SimpleGrantedAuthority(apiKey.getRole()));
                    var auth = new UsernamePasswordAuthenticationToken("api-user", null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                });

        filterChain.doFilter(request, response);
    }
}