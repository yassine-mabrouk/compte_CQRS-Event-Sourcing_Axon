package com.example.compte.commandApi.events;

import lombok.Getter;

public class AccountCreditedEvent extends BaseEvent<String> {
    @Getter
    private double initialBalance;
    @Getter
    private String currency;
    public AccountCreditedEvent(String id, double initialBalance, String currency) {
        super(id);
        this.initialBalance = initialBalance;
        this.currency = currency;
    }
}
