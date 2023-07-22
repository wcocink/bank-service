package com.bank.bankservice;

import com.bank.bankservice.account.control.AccountRepository;
import com.bank.bankservice.account.entity.Account;
import com.bank.bankservice.customer.control.CustomerRepository;
import com.bank.bankservice.customer.entity.Customer;
import com.bank.bankservice.transaction.control.TransactionRepository;
import com.bank.bankservice.utils.AbstractIntegrationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
public class AccountIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenValidRequest_whenCreateAccount_thenCreateAccount() throws Exception {

        Customer customer = new Customer();
        customer.setName("Test1");
        Customer c = customerRepository.save(customer);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .post("/bank/accounts/"+c.getId())
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.balance").value(0))
                .andExpect(jsonPath("$.customer.id").value(c.getId()))
                .andExpect(jsonPath("$.customer.name").value(c.getName()));
    }

    @Test
    public void givenInvalidCustomerId_whenCreateAccount_thenReturnError() throws Exception {

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .post("/bank/accounts/99999999999999999")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(jsonPath("$.message").value("Customer not found."));
    }

    @Test
    public void givenValidRequestWithInitialBalance_whenCreateAccount_thenCreateAccount() throws Exception {

        Customer customer = new Customer();
        customer.setName("Test1");
        Customer c = customerRepository.save(customer);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .post("/bank/accounts/"+c.getId())
                .content("{\"initialBalance\":\"150.50\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.balance").value(150.50))
                .andExpect(jsonPath("$.customer.id").value(c.getId()))
                .andExpect(jsonPath("$.customer.name").value(c.getName()));
    }

    @Test
    public void givenValidAccountId_whenGetAccount_thenGetAccount() throws Exception {

        Customer customer = new Customer();
        customer.setName("Test1");
        Customer c = customerRepository.save(customer);

        Account account = new Account();
        account.setCustomer(c);
        account.setBalance(new BigDecimal(515));
        Account a = accountRepository.save(account);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/bank/accounts/"+a.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.accountId").value(a.getId()))
                .andExpect(jsonPath("$.balance").value(515))
                .andExpect(jsonPath("$.customer.id").value(c.getId()))
                .andExpect(jsonPath("$.customer.name").value(c.getName()));
    }

    @Test
    public void givenInvalidAccountId_whenGetAccount_thenReturnError() throws Exception {

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/bank/accounts/999999999999999999")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(jsonPath("$.message").value("Account not found."));
    }

}
