package com.transaction.repository;

import com.transaction.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM transaction t WHERE t.timestamp < ?1",
            nativeQuery = true)
    void deleteIfOlderThanTimestamp(ZonedDateTime timestamp);
}
