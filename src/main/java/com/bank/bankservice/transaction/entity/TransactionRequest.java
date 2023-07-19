package com.bank.bankservice.transaction.entity;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=2)
    @NotBlank
    private BigDecimal amount;

}
