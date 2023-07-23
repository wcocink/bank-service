package com.bank.bankservice.transaction.control;

import com.bank.bankservice.account.entity.Account;
import com.bank.bankservice.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findTransactionsByAccountId(Long id);

    Optional<Transaction> findTransactionById(String id);

    @Query(value = " select t from TRANSACTION t where t.account = :account and " +
            " (:transactionType is null or t.transactionType = :transactionType) and " +
            " (:value is null or t.value = :value) and " +
            " ( ( cast(:startDate as timestamp ) ) is null or t.date >= :startDate ) and " +
            " ( ( cast(:endDate as timestamp ) ) is null or t.date <= :endDate ) ")
    List<Transaction> findByAccountIdAndOptionals(@Param("account") Account account,
                                                  @Param("transactionType") Optional<String> transactionType,
                                                  @Param("value") Optional<BigDecimal> value,
                                                  @Param("startDate") LocalDateTime startDate,
                                                  @Param("endDate") LocalDateTime endDate);
}
