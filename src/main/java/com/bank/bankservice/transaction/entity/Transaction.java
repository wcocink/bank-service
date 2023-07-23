package com.bank.bankservice.transaction.entity;

import com.bank.bankservice.account.entity.Account;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name="TRANSACTION")
@Data
public class Transaction {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    private String transactionType;

    private LocalDateTime date;

    private BigDecimal value;

    @ManyToOne
    private Account account;

}
