package com.paula.click2buy.shipment.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MelhorEnvioProperties {

    @Value("${melhorenvio.client-id}")
    private String clientId;
    @Value("${melhorenvio.client-secret}")
    private String clientSecret;
    @Value("${melhorenvio.redirect-uri}")
    private String redirectUri;
    @Value("${melhorenvio.api-base-url}")
    private String baseUrl;
    @Value("${melhorenvio.auth-url}")
    private String authUrl;

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
