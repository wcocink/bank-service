package com.bank.bankservice.account.entity;

import com.bank.bankservice.customer.entity.Customer;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity(name="ACCOUNT")
@Data
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal amount;

    @ManyToOne
    private Customer customer;

}
