package com.bank.bankservice.customer.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerRequest {

    @NotNull(message = "You must send a name")
    @NotEmpty(message = "You must send a name")
    private String name;
}
