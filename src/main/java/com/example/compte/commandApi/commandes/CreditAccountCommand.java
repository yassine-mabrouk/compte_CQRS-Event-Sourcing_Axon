package com.example.compte.commandApi.commandes;

import lombok.Getter;

public class CreditAccountCommand extends BaseCommand<String>{
 @Getter
 private double ammount;
 @Getter
 private  String currency;

    public CreditAccountCommand(String id, double ammount, String currency) {

        super(id);
        this.ammount = ammount;
        this.currency = currency;
    }
}
