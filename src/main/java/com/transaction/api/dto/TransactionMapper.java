package com.transaction.api.dto;

import com.transaction.model.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionDto entityToDto(TransactionEntity transactionEntity) {
        return new TransactionDto(transactionEntity.getAmount(), transactionEntity.getTimestamp());
    }

    public TransactionEntity dtoToEntity(TransactionDto transactionDto) {
        return new TransactionEntity(transactionDto.getAmount(), transactionDto.getTimestamp());
    }
}
