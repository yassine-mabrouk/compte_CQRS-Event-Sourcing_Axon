package com.example.compte.queries.service;

import com.example.compte.commandApi.enums.OperationType;
import com.example.compte.commandApi.events.AccountActivatedEvent;
import com.example.compte.commandApi.events.AccountCreatedEvent;
import com.example.compte.commandApi.events.AccountCreditedEvent;
import com.example.compte.commandApi.events.AccountDebitedEvent;
import com.example.compte.commandApi.queries.GetAccountById;
import com.example.compte.commandApi.queries.GetAllAccountQuery;
import com.example.compte.queries.entities.Account;
import com.example.compte.queries.entities.Operation;
import com.example.compte.queries.repositories.AccountRepository;
import com.example.compte.queries.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Slf4j // pour loger
@Transactional
@Service
public class AccountServiceHandler {

    AccountRepository accountRepository ;
    OperationRepository operationRepository;

    // take the data in the event and stored in the account
    @EventHandler
    private void on(AccountCreatedEvent event ){
        log.info("===========================");
        log.info("AccountCreatedEvent Recieved ");
        Account account =new Account();
        account.setId(event.getId());
        account.setBalance(event.getInitialBalance());
        account.setCurrency(event.getCurrency());
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }
    // take the data in the event and stored in the account
    @EventHandler
    private void on(AccountActivatedEvent event ){
        log.info("===========================");
        log.info("AccountActivatedEvent Recieved ");
        Account account =accountRepository.findById(event.getId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }
    // take the data in the event for debit  and stored in the account
    @EventHandler
    private void on(AccountDebitedEvent event ){
        log.info("===========================");
        log.info("AccountDebitedEvent Recieved ");
        Operation operation=new Operation();
        Account account =accountRepository.findById(event.getId()).get();
        account.setBalance(account.getBalance()-event.getAmount());

        operation.setDataOperation(new Date());
        operation.setType(OperationType.DEBIT);
        operation.setAmount(event.getAmount());
        operation.setAccount(account);
        operationRepository.save(operation);
        accountRepository.save(account);
    }
    // take the data in the event for debit  and stored in the account
    @EventHandler
    private void on(AccountCreditedEvent event ){
        log.info("===========================");
        log.info("AccountCreditedEvent Recieved ");
        Operation operation=new Operation();
        Account account =accountRepository.findById(event.getId()).get();
        account.setBalance(account.getBalance()+event.getAmount());

        operation.setDataOperation(new Date());
        operation.setType(OperationType.CREDIT);
        operation.setAmount(event.getAmount());
        operation.setAccount(account);
        operationRepository.save(operation);
        accountRepository.save(account);
    }


}
