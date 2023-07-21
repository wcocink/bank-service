package com.bank.bankservice.account.entity;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {

    @DecimalMin(value = "0")
    @Digits(integer=10, fraction=2)
    private BigDecimal initialBalance;

}
