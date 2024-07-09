package com.transaction.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "transaction")
@Getter
@Setter
public class TransactionEntity {

    public TransactionEntity() {

    }

    public TransactionEntity(BigDecimal amount, ZonedDateTime timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    public BigDecimal amount;

    @NotNull
    @DateTimeFormat(
            iso = DateTimeFormat.ISO.DATE_TIME,
            pattern = "YYYY-MM-DDThh:mm:ss.sssZ")
    public ZonedDateTime timestamp;
}
