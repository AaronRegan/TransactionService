package com.transaction.service.dto;

import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ToString
@AllArgsConstructor
public class StatisticsDto {

    @NotNull
    public String sum;

    @NotNull
    public String avg;

    @NotNull
    public String max;

    @NotNull
    public String min;

    @NotNull
    public Long count;

    public StatisticsDto() {
    }
}
