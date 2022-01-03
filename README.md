# HPC_distributed-system_micro-services_with_JWT_security

## Application
- Créer une application qui permet de gérer des comptes bancaires permet de :
    - Ajouter un Compte
    - Activer un compte après création
    - Créditer un compte 
    - Débiter un compte
    - Consulter un compte
    - Consulter les comptes
    - Consulter les opérations d’un compte
    - Suivre en temps réel l’état d’un compte
    
# Architecture du projet
 <img src="images/Architecture.PNG" alt="">

## Technologie est Tools
- IntelliJ IDEA
- Java 8
- Maven
- Spring Boot
- Spring Boot DevTools
- Mysql database
- lombok
- Axon framework (CQRS && Event Sourcing)

## Commands Side
- Creer un compte
  <img src="images/createAccount.PNG" alt="">
  
- Consulter  BD mysql
  <img src="images/eventStore.PNG" alt="">
  
- Consulter Event Store pour un compte
  <img src="images/eventStoreForAcount.PNG" alt="">
  
## 6) Use
Ce projet est un projet Spring boot 
- git clone repo
- configuration Databsae pour chaque microservice (application.properties)
- Run app
## 7) Sources
pour plus de détails consulter les sources suivantes
- demo video  [Event Driven Micro Services Architecture ](https://www.youtube.com/watch?v=0MG8akH6cfU)
- Spring doc  [Spring cloud  doc](https://spring.io/projects/spring-cloud)
- Axon doc  [Axon  doc](https://axoniq.io/)
### Enjoy !!
 

