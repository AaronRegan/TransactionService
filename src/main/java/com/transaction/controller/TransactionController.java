package com.transaction.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.transaction.service.dto.StatisticsDto;
import com.transaction.service.dto.TransactionDto;
import com.transaction.service.dto.mapper.TransactionMapper;
import com.transaction.model.TransactionEntity;
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

    @Autowired
    public TransactionController(@NonNull final TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transactions")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<String> postTransaction(@RequestBody @Valid TransactionDto transactionDto) {
        if (transactionService.checkTransactionTimeInFuture(transactionDto)) {
            return new ResponseEntity<>("", HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (transactionService.checkTransactionTimeOlderThanOneMinute(transactionDto)) {
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        } else {
            transactionService.storeNewTransaction(transactionDto);
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
