package com.transaction.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.transaction.controller.dto.StatisticsDto;
import com.transaction.controller.dto.TransactionDto;
import com.transaction.controller.dto.TransactionMapper;
import com.transaction.domain.TransactionEntity;
import com.transaction.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *  Aaron Regan - aaronregan20@gmail.com
 */
@Slf4j
@Validated
@RequestMapping(consumes="*")
@RestController
public class TransactionController {

    @NonNull
    private final TransactionService transactionService;

    @NonNull
    private final TransactionMapper transactionMapper;

    @Autowired
    public TransactionController(@NonNull final TransactionService transactionService,
                                 @NonNull final TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    @PostMapping("/transactions")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<String> postTransaction(@RequestBody @Valid TransactionDto transactionDto) {
        TransactionEntity incomingTransaction = transactionMapper.dtoToEntity(transactionDto);
        if (transactionService.checkTransactionTimeInFuture(incomingTransaction)) {
            return new ResponseEntity<>("", HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (transactionService.checkTransactionTimeOlderThanOneMinute(incomingTransaction)) {
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        } else {
            transactionService.storeNewTransaction(incomingTransaction);
            return new ResponseEntity<>("", HttpStatus.CREATED);
        }
    }

    @GetMapping("/statistics")
    public StatisticsDto getTransactionsSatistics() {
        return transactionService.getTransactionstatisticsForLast60Seconds();
    }

    @DeleteMapping("/transactions")
    public ResponseEntity<String> deleteExisitingTransactions() {
        transactionService.clearTransactionStorage();
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<String> handleInvalidFormatException(InvalidFormatException e) {
        return new ResponseEntity<>("", HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
