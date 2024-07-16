package com.transaction.service.external;

import com.transaction.repository.TransactionRepository;
import com.transaction.service.external.dto.AirlineDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AirlineExternalService {

    @Value("${external.http.url.airline}")
    private String apiUrl;

    private final AuthExternalService authExternalService;

    @Autowired
    public AirlineExternalService(AuthExternalService authExternalService) {
        this.authExternalService = authExternalService;
    }

    public List<AirlineDto> getAirlines() {
        String bearer = this.authExternalService.getBearerToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearer);

        HttpEntity<String> entity = new HttpEntity<>("body", headers);


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<AirlineDto>> rateResponse =
                restTemplate.exchange(apiUrl,
                        HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
                        });
        return rateResponse.getBody();
    }
}
