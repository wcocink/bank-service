package com.bank.bankservice.entity;

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
