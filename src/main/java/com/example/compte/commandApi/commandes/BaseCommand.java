package com.example.compte.commandApi.commandes;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public  abstract class BaseCommand <T>{
  @TargetAggregateIdentifier // pour que ce Id represente agregate ou en vas effectuer commande
   @Getter
    protected T id ;

  public BaseCommand(T id) {
    this.id = id;
  }
}
