package com.bank.bankservice;

import com.bank.bankservice.account.control.AccountRepository;
import com.bank.bankservice.customer.control.CustomerRepository;
import com.bank.bankservice.customer.entity.Customer;
import com.bank.bankservice.customer.entity.CustomerRequest;
import com.bank.bankservice.transaction.control.TransactionRepository;
import com.bank.bankservice.utils.AbstractIntegrationTests;
import com.bank.bankservice.utils.JsonToString;
import org.junit.jupiter.api.Order;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
public class CustomerIntegrationTests extends AbstractIntegrationTests {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    @Order(1)
    public void givenValidRequest_whenCreateCustomer_thenCreateCustomer() throws Exception {

        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setName("Test1");

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .post("/bank/customers")
                .content(JsonToString.asJsonString(customerRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    @Order(2)
    public void givenInvalidRequest_whenCreateCustomer_thenReturnError() throws Exception {

        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setName("");

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .post("/bank/customers")
                .content(JsonToString.asJsonString(customerRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.name").value("You must send a name"));

    }

    @Test
    @Order(3)
    public void givenCustomersAlreadyCreated_whenGetCustomers_thenGetCustomerList() throws Exception {

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/bank/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @Order(4)
    public void givenNoCustomersCreated_whenGetCustomers_thenReturnMessage() throws Exception {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
        customerRepository.deleteAll();

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/bank/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(jsonPath("$.message").value("Customer not found."));
    }


}
