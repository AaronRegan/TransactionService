package com.transaction.service.external.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ToString
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirlineDto {

    @NotNull
    String id;

    @NotNull
    String name;

    @NotNull
    String country;

    @NotNull
    String established;
}
