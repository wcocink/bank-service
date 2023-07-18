package com.bank.bankservice.customer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name="CUSTOMER")
@Data
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
