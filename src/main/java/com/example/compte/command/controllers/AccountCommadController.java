package com.example.compte.command.controllers;


import com.example.compte.commandApi.commandes.CreateAccountCommand;
import com.example.compte.commandApi.commandes.CreditAccountCommand;
import com.example.compte.commandApi.commandes.DebitAccountCommand;
import com.example.compte.commandApi.dtos.CreateAccountRequestDto;
import com.example.compte.commandApi.dtos.CreditAccountRequestDto;
import com.example.compte.commandApi.dtos.DebitAccountRequestDto;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/command/account")
public class AccountCommadController {
    private CommandGateway commandGateway;
    private EventStore eventStore;
  @PostMapping(path="/create")
    public  CompletableFuture<String> createAccount (@RequestBody CreateAccountRequestDto request){
      CompletableFuture<String> commandResponse = commandGateway.send(new CreateAccountCommand(
              UUID.randomUUID().toString(),
              request.getInitialBalance(),
              request.getCurrency()
      ));
      return commandResponse;
    }
    @Transactional
    @PutMapping(path="/credit")
    public    CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDto request){
        CompletableFuture<String> commandResponse = commandGateway.send(new CreditAccountCommand(
                request.getAccountId(),
                request.getAmount(),
                request.getCurrency()
        ));
        return commandResponse;
    }

   @GetMapping(path="/eventStore/{id}")
   public Stream eventStore (@PathVariable String id ){
    return  eventStore.readEvents(id).asStream();
   }

    //  @ExceptionHandler(Exception.class)
     public ResponseEntity<String> getErrorResponse(Exception e){
       ResponseEntity<String> entity= new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
       return entity;
    }


}
