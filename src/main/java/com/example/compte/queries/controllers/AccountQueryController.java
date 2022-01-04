package com.example.compte.queries.controllers;

import com.example.compte.commandApi.queries.GetAccountById;
import com.example.compte.commandApi.queries.GetAllAccountQuery;
import com.example.compte.queries.entities.Account;
import com.example.compte.queries.repositories.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/query/account")
@AllArgsConstructor
@Slf4j
public class AccountQueryController {

    QueryGateway queryGateway;
    AccountRepository accountRepository ;
    @GetMapping(path = "/allAccounts")
    public List<Account> getAllAccounts(){
    //    List<Account> response = queryGateway.query(new GetAllAccountQuery() ,ResponseTypes.multipleInstancesOf(Account.class)).join();
      //    return response;
        return accountRepository.findAll();
    }
    @GetMapping(path = "/byId/{id}")
    public Account getAccountById(@PathVariable String id ){
        return queryGateway.query(new GetAccountById(id) ,ResponseTypes.instanceOf(Account.class)).join();

    }


}
