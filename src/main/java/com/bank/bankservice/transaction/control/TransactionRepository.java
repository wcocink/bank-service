package com.bank.bankservice.transaction.control;

import com.bank.bankservice.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findTransactionsByAccountId(Long id);

    Optional<Transaction> findTransactionById(String id);
}
