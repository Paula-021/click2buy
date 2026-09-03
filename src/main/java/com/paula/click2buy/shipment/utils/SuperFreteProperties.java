package com.paula.click2buy.shipment.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SuperFreteProperties {


    @Value("${superfrete.api-token}")
    private String apiToken;
    @Value("${superfrete.api-base-url}")
    private String baseUrl;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
