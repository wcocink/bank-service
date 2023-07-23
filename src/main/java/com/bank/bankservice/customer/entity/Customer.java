package com.bank.bankservice.customer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Entity(name="CUSTOMER")
@Data
public class Customer implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
