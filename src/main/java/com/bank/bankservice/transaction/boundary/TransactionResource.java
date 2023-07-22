package com.bank.bankservice.transaction.boundary;

import com.bank.bankservice.transaction.control.TransactionController;
import com.bank.bankservice.transaction.entity.OperationResponse;
import com.bank.bankservice.transaction.entity.TransactionRequest;
import com.bank.bankservice.transaction.entity.TransactionResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank/transactions")
public class TransactionResource {

    @Autowired
    TransactionController transactionController;

    @PatchMapping(path = "/accounts/{accountId}/deposit",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperationResponse> deposit(@PathVariable String accountId, @RequestBody @Valid TransactionRequest transactionRequest) {
        return new ResponseEntity<>(transactionController.deposit(accountId, transactionRequest), HttpStatus.OK);
    }

    @PatchMapping(path = "/accounts/{accountId}/withdraw",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperationResponse> withdraw(@PathVariable String accountId, @RequestBody @Valid TransactionRequest transactionRequest) {
        return new ResponseEntity<>(transactionController.withdraw(accountId, transactionRequest), HttpStatus.OK);
    }

    @GetMapping(
            value="/accounts/{accountId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<TransactionResponse>> getAllTransactionsFromAccount(@PathVariable String accountId) {
        List<TransactionResponse> transactionResponseList = transactionController.getAccountTransactions(accountId);
        if(transactionResponseList.isEmpty()){
            return new ResponseEntity<>(transactionResponseList, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(transactionResponseList, HttpStatus.OK);
    }

    @GetMapping(
            value="/{transactionId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable String transactionId) {
        TransactionResponse transactionResponse = transactionController.getTransaction(transactionId);
        return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
    }

}
