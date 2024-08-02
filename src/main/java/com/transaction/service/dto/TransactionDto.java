package com.transaction.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@AllArgsConstructor
@ToString
@Getter
public class TransactionDto {

    @NotNull
    public BigDecimal amount;

    @NotNull
    @DateTimeFormat(
            iso = DateTimeFormat.ISO.DATE_TIME,
            pattern = "YYYY-MM-DDThh:mm:ss.sssZ")
    public ZonedDateTime timestamp;


}
