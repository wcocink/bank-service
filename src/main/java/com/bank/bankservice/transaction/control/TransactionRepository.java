package com.bank.bankservice.transaction.control;

import com.bank.bankservice.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {


    //List<Transaction> findByAccountIdAndDateGreaterThanEqualAndDateLessThanEqual(Long accountId, Date startDate, Date endDate);

    @Query(value = "from TRANSACTION t where (t.date BETWEEN :fromDate AND :toDate) AND t.account.id = :accountId")
    List<Transaction> getAllBetweenDates(@Param("accountId") Long accountId,
                                         @Param("fromDate") LocalDateTime startDate,
                                         @Param("toDate") LocalDateTime endDate);

}
