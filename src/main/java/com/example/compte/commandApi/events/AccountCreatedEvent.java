package com.example.compte.commandApi.events;

import com.example.compte.commandApi.enums.AccountStatus;
import lombok.Getter;

public class AccountCreatedEvent extends BaseEvent<String>{

    @Getter
    private double initialBalance;
    @Getter
    private  String currency;
    @Getter
    private AccountStatus status;



    public AccountCreatedEvent(String id, double initialBalance, String currency, AccountStatus status) {
        super(id);
        this.initialBalance = initialBalance;
        this.currency = currency;
        this.status = status;
    }
}
