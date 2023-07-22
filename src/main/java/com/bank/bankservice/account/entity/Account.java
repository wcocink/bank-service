package com.bank.bankservice.account.entity;

import com.bank.bankservice.customer.entity.Customer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;

@Entity(name="ACCOUNT")
@Data
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal balance;

    @ManyToOne
    private Customer customer;

}
