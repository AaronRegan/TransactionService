package com.transaction.service.external;

import com.transaction.service.external.dto.AirlineDto;
import com.transaction.service.external.dto.AuthDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class AuthExternalService {

    @Value("${external.http.url.auth}")
    private String apiUrl;

    @Value("${external.http.url.auth.username}")
    private String apiUser;

    @Value("${external.http.url.auth.password}")
    private String apiPass;

    @Value("${external.http.url.auth.clientid}")
    private String apiClient;

    @Autowired
    public AuthExternalService() {
    }

    public String getBearerToken() {
        RestTemplate restTemplate = new RestTemplate();

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("grant_type", "password")
                .queryParam("scope", "offline_access")
                .queryParam("username", apiUser)
                .queryParam("password", apiPass)
                .queryParam("client_id", apiClient)
                .encode()
                .toUriString();

        ResponseEntity<AuthDto> response =
                restTemplate.exchange(urlTemplate,
                        HttpMethod.POST, null, new ParameterizedTypeReference<>() {
                        });
        return Objects.requireNonNull(response.getBody()).getAccessToken();
    }
}
