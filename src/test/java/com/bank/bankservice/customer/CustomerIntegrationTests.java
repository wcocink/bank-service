package com.bank.bankservice.customer;

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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
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

    @MockBean
    KafkaTemplate kafkaTemplate;

    @MockBean
    KafkaAdmin kafkaAdmin;

    @Test
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
    public void givenCustomersAlreadyCreated_whenGetCustomers_thenGetCustomerList() throws Exception {
        Customer customer = new Customer();
        customer.setName("Test1");
        customerRepository.save(customer);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/bank/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
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
