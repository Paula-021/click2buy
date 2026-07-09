package com.paula.click2buy.shipment.services;

import com.paula.click2buy.shipment.dtos.TokenResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class MelhorEnvioTokenService {

    // Fazer a regra de negócio para armazenar o token
    // Fazer a regra de negócio para renovar o token e armazenar token
    // Fazer a regra de negócio para fornecer o token válido quando solicitado

    //TTL => Time To Live => tempo de vida do token

    private static final String ACCESS_TOKEN_KEY = "melhorenvio:access_token";
    private static final String REFRESH_TOKEN_KEY = "melhorenvio:refresh_token";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private MelhorEnvioOAuthService melhorEnvioOAuthService;

    public void saveToken(String accessToken, String refreshToken, long expiresIn) {

        redisTemplate.opsForValue().set(ACCESS_TOKEN_KEY, accessToken, Duration.ofSeconds(expiresIn));

        redisTemplate.opsForValue().set(REFRESH_TOKEN_KEY, refreshToken);
    }

    public String getAccessToken() {
        //vamos usar o Access Token quando quisermos pegar o acesso a API do melhor envio
        //

        String token = redisTemplate.opsForValue().get(ACCESS_TOKEN_KEY);

        if(token == null){
            // se for null, expirou => chamar o refresh token
            refreshToken();
            token = redisTemplate.opsForValue().get(ACCESS_TOKEN_KEY);
        }

        return token;

    }

    public void refreshToken() {

        //buscar o código (code) do refresh token no redis
        String refreshToken = redisTemplate.opsForValue().get(REFRESH_TOKEN_KEY);

        //verificar se o refresh token é nulo
        if(refreshToken == null){
            throw new IllegalStateException("Refresh token is null. Admin needs to re-authenticate.");
        }

        //chamar o serviço de OAuth para trocar o refresh token por um novo access token
        TokenResponseDTO response = melhorEnvioOAuthService.refreshToken(refreshToken);

        //salvar o novo access token e refresh token no redis
        saveToken(response.getAccessToken(), response.getRefreshToken(), response.getExpiresIn());

    }

}
