package com.transaction.service.external.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ToString
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class AuthDto {

    @NotNull
    @JsonProperty("token_type")
    String tokenType;

    @NotNull
    @JsonProperty("access_token")
    String accessToken;
}
