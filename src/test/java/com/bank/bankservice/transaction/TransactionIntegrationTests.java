package com.bank.bankservice.transaction;

import com.bank.bankservice.account.control.AccountRepository;
import com.bank.bankservice.account.entity.Account;
import com.bank.bankservice.customer.control.CustomerRepository;
import com.bank.bankservice.customer.entity.Customer;
import com.bank.bankservice.transaction.control.TransactionRepository;
import com.bank.bankservice.transaction.entity.Transaction;
import com.bank.bankservice.utils.AbstractIntegrationTests;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
public class TransactionIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @MockBean
    KafkaTemplate kafkaTemplate;

    @MockBean
    KafkaAdmin kafkaAdmin;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenInvalidAccount_whenWithdrawRequest_ThenReturnError() throws Exception {

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .patch("/bank/transactions/accounts/9999999999999/withdraw")
                .content("{\"amount\": 400}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(jsonPath("$.message").value("Account not found."));
    }

    @Test
    public void givenInvalidAccount_whenDepositRequest_ThenReturnError() throws Exception {

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .patch("/bank/transactions/accounts/9999999999999/deposit")
                .content("{\"amount\": 400}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(jsonPath("$.message").value("Account not found."));
    }

    @Test
    public void givenValidAccount_whenDepositRequest_ThenReturnOk() throws Exception {

        Customer customer = new Customer();
        customer.setName("Test1");
        Customer c = customerRepository.save(customer);

        Account account = new Account();
        account.setCustomer(c);
        account.setBalance(new BigDecimal("101.48"));
        Account a = accountRepository.save(account);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .patch("/bank/transactions/accounts/"+a.getId()+"/deposit")
                .content("{\"amount\": 458.21}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.balanceAfterTransaction").value(559.69));
    }

    @Test
    public void givenSufficientBalanceInAccount_whenWithdrawRequest_ThenReturnOk() throws Exception {

        Customer customer = new Customer();
        customer.setName("Test1");
        Customer c = customerRepository.save(customer);

        Account account = new Account();
        account.setCustomer(c);
        account.setBalance(new BigDecimal(312));
        Account a = accountRepository.save(account);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .patch("/bank/transactions/accounts/"+a.getId()+"/withdraw")
                .content("{\"amount\": 150.21}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.balanceAfterTransaction").value(161.79));
    }

    @Test
    public void givenInsufficientBalanceInAccount_whenWithdrawRequest_ThenReturnError() throws Exception {

        Customer customer = new Customer();
        customer.setName("Test1");
        Customer c = customerRepository.save(customer);

        Account account = new Account();
        account.setCustomer(c);
        account.setBalance(new BigDecimal(312));
        Account a = accountRepository.save(account);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .patch("/bank/transactions/accounts/"+a.getId()+"/withdraw")
                .content("{\"amount\": 400}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Your balance is not enough to complete this transaction."));
    }

    @Test
    public void givenValidTransactionId_whenGetTransaction_thenGetTransaction() throws Exception {

        Customer customer = new Customer();
        customer.setName("Test1");
        Customer c = customerRepository.save(customer);

        Account account = new Account();
        account.setCustomer(c);
        account.setBalance(new BigDecimal(515));
        Account a = accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setTransactionType("WITHDRAW");
        transaction.setValue(new BigDecimal("101.10"));
        transaction.setDate(LocalDateTime.now());
        transaction.setAccount(a);
        Transaction t = transactionRepository.save(transaction);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/bank/transactions/"+t.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(t.getId()))
                .andExpect(jsonPath("$.transactionType").value("WITHDRAW"))
                .andExpect(jsonPath("$.value").value(101.10));
    }

    @Test
    public void givenInValidTransactionId_whenGetTransaction_thenReturnError() throws Exception {


        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/bank/transactions/56174451795641654156")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(jsonPath("$.message").value("The transaction was not found."));
    }

    @Test
    public void givenInvalidAccountId_whenGetTransactionByAccount_thenReturnError() throws Exception {

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/bank/transactions/accounts/99999999999999999")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(jsonPath("$.message").value("Account not found."));
    }

    @Test
    public void givenValidAccountId_whenGetTransactionByAccountHasNoTransactions_thenReturnError() throws Exception {

        Customer customer = new Customer();
        customer.setName("Test1");
        Customer c = customerRepository.save(customer);

        Account account = new Account();
        account.setCustomer(c);
        account.setBalance(new BigDecimal(515));
        Account a = accountRepository.save(account);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/bank/transactions/accounts/"+a.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void givenValidAccountId_whenGetTransactionByAccountId_thenGetTransactions() throws Exception {

        Customer customer = new Customer();
        customer.setName("Test1");
        Customer c = customerRepository.save(customer);

        Account account = new Account();
        account.setCustomer(c);
        account.setBalance(new BigDecimal(515));
        Account a = accountRepository.save(account);

        Transaction transaction1 = new Transaction();
        transaction1.setTransactionType("WITHDRAW");
        transaction1.setValue(new BigDecimal("101.10"));
        transaction1.setDate(LocalDateTime.now());
        transaction1.setAccount(a);
        transactionRepository.save(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setTransactionType("DEPOSIT");
        transaction2.setValue(new BigDecimal("510.5"));
        transaction2.setDate(LocalDateTime.now());
        transaction2.setAccount(a);
        transactionRepository.save(transaction2);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/bank/transactions/accounts/"+a.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void givenValidAccountIdAndOptionalDates_whenGetTransactionByAccountIdAndOptionals_thenGetTransactions() throws Exception {

        transactionRepository.deleteAll();
        accountRepository.deleteAll();
        customerRepository.deleteAll();

        Customer customer = new Customer();
        customer.setName("Test1");
        Customer c = customerRepository.save(customer);

        Account account = new Account();
        account.setCustomer(c);
        account.setBalance(new BigDecimal(515));
        Account a = accountRepository.save(account);

        Transaction transaction1 = new Transaction();
        transaction1.setTransactionType("WITHDRAW");
        transaction1.setValue(new BigDecimal("101.10"));
        transaction1.setDate(LocalDateTime.of(2023, Month.JULY,23,15,0,0));
        transaction1.setAccount(a);
        transactionRepository.save(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setTransactionType("WITHDRAW");
        transaction2.setValue(new BigDecimal("100"));
        transaction2.setDate(LocalDateTime.of(2023, Month.JULY,23,15,1,0));
        transaction2.setAccount(a);
        transactionRepository.save(transaction2);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/bank/transactions/accounts/"+a.getId()+"?startDate=2023-07-23&endDate=2023-07-24")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void givenValidAccountIdAndInvalidOptionalDates_whenGetTransactionByAccountIdAndOptionals_thenReturnError() throws Exception {

        transactionRepository.deleteAll();
        accountRepository.deleteAll();
        customerRepository.deleteAll();

        Customer customer = new Customer();
        customer.setName("Test1");
        Customer c = customerRepository.save(customer);

        Account account = new Account();
        account.setCustomer(c);
        account.setBalance(new BigDecimal(515));
        Account a = accountRepository.save(account);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/bank/transactions/accounts/"+a.getId()+"?startDate=2023-07-24&endDate=2023-07-23")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.message").value("The end date must be after start date."));
    }

    @Test
    public void givenValidAccountIdAndAllOptionals_whenGetTransactionByAccountIdAndOptionals_thenGetTransactions() throws Exception {

        transactionRepository.deleteAll();
        accountRepository.deleteAll();
        customerRepository.deleteAll();

        Customer customer = new Customer();
        customer.setName("Test1");
        Customer c = customerRepository.save(customer);

        Account account = new Account();
        account.setCustomer(c);
        account.setBalance(new BigDecimal(515));
        Account a = accountRepository.save(account);

        Transaction transaction1 = new Transaction();
        transaction1.setTransactionType("WITHDRAW");
        transaction1.setValue(new BigDecimal("101"));
        transaction1.setDate(LocalDateTime.of(2023, Month.JULY,23,15,0,0));
        transaction1.setAccount(a);
        transactionRepository.save(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setTransactionType("WITHDRAW");
        transaction2.setValue(new BigDecimal("100"));
        transaction2.setDate(LocalDateTime.of(2023, Month.JULY,24,15,1,0));
        transaction2.setAccount(a);
        transactionRepository.save(transaction2);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/bank/transactions/accounts/"+a.getId()+"?" +
                        "startDate=2023-07-23" +
                        "&endDate=2023-07-24" +
                        "&value=101" +
                        "&transactionType=WITHDRAW")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

}
