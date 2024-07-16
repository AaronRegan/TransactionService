package com.transaction.service;

import com.transaction.api.dto.TransactionDto;
import com.transaction.repository.TransactionRepository;
import com.transaction.service.external.AirlineExternalService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 *  Aaron Regan - aaronregan20@gmail.com
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class TransactionServiceTest {
    
    private TransactionService subjectUnderTest;

    @MockBean
    TransactionRepository transactionRepository;

    @MockBean
    AirlineExternalService airlineExternalService;

    @Before
    public void setUp() {
        subjectUnderTest = new TransactionService(transactionRepository, airlineExternalService);
    }

    @Test
    public void checkTransactionTimeInFutureReturnsTrue(){
        TransactionDto transactionDto = new TransactionDto(new BigDecimal("12.3343"), ZonedDateTime.now().plusHours(1L));

        assertThat(subjectUnderTest.checkTransactionTimeInFuture(transactionDto)).isTrue();
    }

/*    @Test
    public void checkTransactionTimeInFutureReturnsFalse(){
        transactionStorage.clear();
        transactionDto = new TransactionDto(new BigDecimal("12.3343"), ZonedDateTime.now().minusSeconds(2L));

        assertThat(subjectUnderTest.checkTransactionTimeInFuture(transactionDto)).isFalse();
    }

    @Test
    public void checkTransactionTimeIsOlderThan60SecondsTrue(){
        transactionStorage.clear();
        transactionDto = new TransactionDto(new BigDecimal("12.3343"), ZonedDateTime.now().minusSeconds(59L));

        assertThat(subjectUnderTest.checkTransactionTimeOlderThanOneMinute(transactionDto)).isFalse();
    }

    @Test
    public void checkTransactionTimeIsOlderThan60SecondsFalse(){
        transactionStorage.clear();
        transactionDto = new TransactionDto(new BigDecimal("12.3343"), ZonedDateTime.now().minusSeconds(61L));

        assertThat(subjectUnderTest.checkTransactionTimeOlderThanOneMinute(transactionDto)).isTrue();
    }

    @Test
    public void addsNewTransactionSuccessfully(){
        transactionStorage.clear();
        transactionDto = new TransactionDto(new BigDecimal("12.3343"), ZonedDateTime.now().minusSeconds(61L));

        assertThat(subjectUnderTest.storeNewTransaction(transactionDto)).isTrue();
    }

    @Test
    public void removeOldTransactionsSuccessfully(){
        TransactionDto transactionDto0 = new TransactionDto(new BigDecimal("12"), ZonedDateTime.now().minusSeconds(20L));
        TransactionDto transactionDto1 = new TransactionDto(new BigDecimal("13"), ZonedDateTime.now().minusSeconds(59L));
        TransactionDto transactionDto2 = new TransactionDto(new BigDecimal("14"), ZonedDateTime.now().minusSeconds(61L));
        TransactionDto transactionDto3 = new TransactionDto(new BigDecimal("15"), ZonedDateTime.now().minusSeconds(70L));

        transactionStorage.clear();
        transactionStorage.add(transactionDto0);
        transactionStorage.add(transactionDto1);
        transactionStorage.add(transactionDto2);
        transactionStorage.add(transactionDto3);

        subjectUnderTest.cleanTransactionsOlderThan60Seconds();

        assertThat(transactionStorage.size()).isEqualTo(2);
    }

    @Test
    public void clearTransactionsStorageSuccessfully(){
        TransactionDto transactionDto0 = new TransactionDto(new BigDecimal("12"), ZonedDateTime.now().minusSeconds(20L));
        TransactionDto transactionDto1 = new TransactionDto(new BigDecimal("13"), ZonedDateTime.now().minusSeconds(59L));
        TransactionDto transactionDto2 = new TransactionDto(new BigDecimal("14"), ZonedDateTime.now().minusSeconds(61L));
        TransactionDto transactionDto3 = new TransactionDto(new BigDecimal("15"), ZonedDateTime.now().minusSeconds(70L));

        transactionStorage.clear();
        transactionStorage.add(transactionDto0);
        transactionStorage.add(transactionDto1);
        transactionStorage.add(transactionDto2);
        transactionStorage.add(transactionDto3);

        subjectUnderTest.clearTransactionStorage();

        assertThat(transactionStorage.size()).isEqualTo(0);
    }

    @Test
    public void getTransactionsStatisticsSuccessfully(){
        TransactionDto transactionDto0 = new TransactionDto(new BigDecimal("5"), ZonedDateTime.now().minusSeconds(20L));
        TransactionDto transactionDto1 = new TransactionDto(new BigDecimal("10"), ZonedDateTime.now().minusSeconds(59L));
        TransactionDto transactionDto2 = new TransactionDto(new BigDecimal("10"), ZonedDateTime.now().minusSeconds(61L));
        TransactionDto transactionDto3 = new TransactionDto(new BigDecimal("5"), ZonedDateTime.now().minusSeconds(70L));

        transactionStorage.clear();
        transactionStorage.add(transactionDto0);
        transactionStorage.add(transactionDto1);
        transactionStorage.add(transactionDto2);
        transactionStorage.add(transactionDto3);

        StatisticsDto results = subjectUnderTest.getTransactionstatisticsForLast60Seconds();

        assertThat(results.avg).isEqualTo("7.50");
        assertThat(results.max).isEqualTo("10.00");
        assertThat(results.min).isEqualTo("5.00");
        assertThat(results.count).isEqualTo(2L);
        assertThat(results.sum).isEqualTo("15.00");
    }

    @Test
    public void getTransactionsStatisticsHandlesEmptyListSuccessfully(){
        transactionStorage.clear();

        StatisticsDto results = subjectUnderTest.getTransactionstatisticsForLast60Seconds();

        assertThat(results.avg).isEqualTo("0.00");
        assertThat(results.max).isEqualTo("0.00");
        assertThat(results.min).isEqualTo("0.00");
        assertThat(results.count).isEqualTo(0L);
        assertThat(results.sum).isEqualTo("0.00");
    }*/
}
