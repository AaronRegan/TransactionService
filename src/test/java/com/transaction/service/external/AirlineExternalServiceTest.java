package com.transaction.service.external;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.transaction.service.external.dto.AirlineDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *  Aaron Regan - aaronregan20@gmail.com
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class AirlineExternalServiceTest {

    private AirlineExternalService subjectUnderTest;

    @MockBean
    private AuthExternalService authExternalService;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Before
    public void setUp() {
        subjectUnderTest = new AirlineExternalService(authExternalService);
    }

    @Test
    @Ignore
    public void checkExternalServiceReturnsOkay() {
        stubFor(post("/v1/airlines")
                .withHeader("Content-Type", containing("application/json"))
                .willReturn(ok()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{response:success")));

        List<AirlineDto> response = subjectUnderTest.getAirlines();

        assertThat(response).isEqualTo("");
    }
}
