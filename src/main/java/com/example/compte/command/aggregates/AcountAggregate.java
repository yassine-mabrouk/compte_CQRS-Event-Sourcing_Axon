package com.example.compte.command.aggregates;

import com.example.compte.commandApi.commandes.CreateAccountCommand;
import com.example.compte.commandApi.commandes.CreditAccountCommand;
import com.example.compte.commandApi.commandes.DebitAccountCommand;
import com.example.compte.commandApi.enums.AccountStatus;
import com.example.compte.commandApi.events.AccountActivatedEvent;
import com.example.compte.commandApi.events.AccountCreatedEvent;
import com.example.compte.commandApi.events.AccountCreditedEvent;
import com.example.compte.commandApi.events.AccountDebitedEvent;
import com.example.compte.commandApi.exception.AmountNegativeException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate   // pour creer un agregat
public class AcountAggregate {

     @AggregateIdentifier // c'est identifiant de agregate
   private String accountId;
    private double initialBalance;
    private  String currency;
    private AccountStatus accountStatus ;

    public AcountAggregate() {  // est obligatoire
    }
    @CommandHandler // pour creer un commande executer si il ya une commande dans le bus de commande
    // c'est la fonction de desicion
    public AcountAggregate(CreateAccountCommand createAccountCommand) {
        if (createAccountCommand.getInitialBalance()<0) throw new RuntimeException("Cannot create account with negative balance !!");
        // ok
        // creer un evenemt
        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCommand.getId(),
                createAccountCommand.getInitialBalance(),
                createAccountCommand.getCurrency()
        ));
    }
            // fonction d'evolution pour muter etat du compte
            @EventSourcingHandler
            public void on(AccountCreatedEvent event){
                this.initialBalance= event.getInitialBalance();
                this.currency=event.getCurrency();
                this.accountStatus=AccountStatus.CREATED;
                this.accountId=event.getId();
              // creer un event pour activer le compte
              AggregateLifecycle.apply(new AccountActivatedEvent(
                    event.getId(),
                    AccountStatus.ACTIVATED
              ) )  ;
            }
            // encore muter etat de compte
            @EventSourcingHandler
            public void on (AccountActivatedEvent event){
                this.accountStatus = event.getStatus();

            }
            //============== Credit operation ==================
               @CommandHandler
            // fonctionn de desicion
            public  void  handele (CreditAccountCommand creditAccountCommand){
               if(creditAccountCommand.getAmmount()<0) throw new AmountNegativeException("Amount should not be negative ");
              AggregateLifecycle.apply( new AccountCreditedEvent(
                      creditAccountCommand.getId(),
                      creditAccountCommand.getAmmount(),
                      creditAccountCommand.getCurrency()
              ) );
            }

            // changer etat du compte => fonction de evolution
            @EventSourcingHandler
           public void on (AccountCreditedEvent event){
                 this.initialBalance+= event.getAmount();
            }


    //============== Credit operation ==================
            @CommandHandler
            public void handele (DebitAccountCommand debitAccountCommand){
             if (debitAccountCommand.getAmount()<0) throw new AmountNegativeException("Amount should not be negative ");
             AggregateLifecycle.apply(new AccountDebitedEvent (
                debitAccountCommand.getId(),
                debitAccountCommand.getAmount(),
                     debitAccountCommand.getCurrency()
             ));
            }
            // changer etat
           @EventSourcingHandler
            public void on (AccountDebitedEvent evet){
            this.initialBalance-=evet.getAmount();
           }



}
