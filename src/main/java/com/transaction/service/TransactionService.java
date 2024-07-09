package com.transaction.service;

import com.transaction.api.dto.StatisticsDto;
import com.transaction.api.dto.TransactionDto;
import com.transaction.model.TransactionEntity;
import com.transaction.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.impl.collector.BigDecimalSummaryStatistics;
import org.eclipse.collections.impl.collector.Collectors2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.time.ZonedDateTime;

/**
 *  Aaron Regan - aaronregan20@gmail.com
 */
@Slf4j
@Service
@EnableScheduling
public class TransactionService {

    private static final long MINUTE_TO_MILLI = 60 * 1000;

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void storeNewTransaction(TransactionDto transactionDto) {
        log.debug("Received New Transaction {}", transactionDto.toString());
        transactionRepository.save(this.dtoToEntity(transactionDto));
    }

    public Boolean checkTransactionTimeInFuture(TransactionDto transactionDto) {
        return transactionDto.timestamp.isAfter(ZonedDateTime.now());
    }

    public Boolean checkTransactionTimeOlderThanOneMinute(TransactionDto transactionDto) {
        return transactionDto.timestamp.isBefore(ZonedDateTime.now().minusSeconds(60));
    }

    public void clearTransactionStorage() {
        transactionRepository.deleteAll();
    }


    public StatisticsDto getTransactionstatisticsForLast60Seconds() {
        BigDecimalSummaryStatistics stats = transactionRepository.findAll()
                .stream()
                .map(this::entityToDto)
                .filter(transactionDto -> !this.checkTransactionTimeOlderThanOneMinute(transactionDto))
                .collect(Collectors2.summarizingBigDecimal(TransactionDto::getAmount));

        // The Integration Tests Expect String output for the statistics
        return new StatisticsDto(
                stats.getSum().setScale(2, RoundingMode.HALF_UP).toString(),
                stats.getAverage().setScale(2, RoundingMode.HALF_UP).toString(),
                stats.getMax() != null ? stats.getMax().setScale(2, RoundingMode.HALF_UP).toString() : "0.00",
                stats.getMin() != null ? stats.getMin().setScale(2, RoundingMode.HALF_UP).toString() : "0.00",
                stats.getCount());
    }

    @Scheduled(fixedRate = MINUTE_TO_MILLI)
    public void cleanTransactionsOlderThan60Seconds() {
        log.info("Transaction Storage size Before clean up: {}", transactionRepository.findAll().size());
        this.transactionRepository.deleteIfOlderThanTimestamp(ZonedDateTime.now().minusSeconds(60L));
        log.info("Transaction Storage size after clean up: {}", transactionRepository.findAll().size());
    }

    private TransactionDto entityToDto(TransactionEntity transactionEntity) {
        return new TransactionDto(transactionEntity.getAmount(), transactionEntity.getTimestamp());
    }

    private TransactionEntity dtoToEntity(TransactionDto transactionDto) {
        return new TransactionEntity(transactionDto.getAmount(), transactionDto.getTimestamp());
    }
}
