package com.paula.click2buy.shipment.services;

import com.paula.click2buy.shipment.dtos.TokenResponseDTO;
import com.paula.click2buy.shipment.utils.MelhorEnvioProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Service
public class MelhorEnvioOAuthServiceImpl implements MelhorEnvioOAuthService {

    @Autowired
    private MelhorEnvioProperties melhorEnvioProperties;

    private final RestTemplate restTemplate = new RestTemplate();


    @Override
    public TokenResponseDTO exchangeCodeForToken(String code) {
        // code que precisa ser trocado para token
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(melhorEnvioProperties.getClientId(), melhorEnvioProperties.getClientSecret());
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = Map.of(
                "grant_type", "authorization_code",
                "code", code,
                "redirect_uri", melhorEnvioProperties.getRedirectUri()
        );

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<TokenResponseDTO> response = restTemplate.postForEntity(melhorEnvioProperties.getAuthUrl(), request, TokenResponseDTO.class);

        TokenResponseDTO tokenResponseDTO = response.getBody();

        return tokenResponseDTO;

    }

    @Override
    public TokenResponseDTO refreshToken(String refreshToken) {
        // refresh token para obter um novo access token
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(melhorEnvioProperties.getClientId(), melhorEnvioProperties.getClientSecret());
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = Map.of(
                "grant_type", "refresh_token",
                "refresh_token", refreshToken,
                "client_id", melhorEnvioProperties.getClientId(),
                "client_secret", melhorEnvioProperties.getClientSecret()
        );

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<TokenResponseDTO> response = restTemplate.postForEntity(melhorEnvioProperties.getAuthUrl(), request, TokenResponseDTO.class);

        return response.getBody();
    }
}
