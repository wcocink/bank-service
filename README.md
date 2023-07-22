# Bank Service

Bank Service is a very short example about a prototype for a bank architecture with several interactions

## Installation

Use the maven command to create tha jar version + run the unit and integrated tests

```bash
mvn clean package
```
The project is totally containerized in docker, so, after the package command, just do the following:

```bash
docker-compose up
```
This will make the container start with the bank-service and all the necessary applications. (PostgreSQL, Kafka, Kafdrop and Zookeeper)

## Usage

After the installation process, the service must be running in localhost:8080

To make an easy usage of the API, there is also a swagger-ui which can be accessed by:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

also, the kafkdrop is available on:

[http://localhost:19000/](http://localhost:19000/)


## Architectural Goal

As it is a simple prototype of a bank-service, I assumed that in the future the service is going to have many costumers, accounts and trasactions.

For this reason, at the moment which the user requests a transaction, the only validation that is being done (in terms of business rules) is if the requester's account has enough funds to complete that. On the positive cenario, the requester receives an OK from the api with the current account's balance and then, a message to a kafka queue will be sent. This message will be consumed assynchronously to create a entry in the Transaction table with more transaction details.

## Unit/Integrated Tests
The Unit tests are being done with JUnit and Mockito
The Integration tests are being done with Junit and Testcontainer

The testcontainer creates a container in docker during the test execution to make a fully end to end tests from the REST API until its response.

## External Technologies Used

* PostgreSQL Database
* Kafka
* Kafdrop (UI for kafka messages)
* Zookeeper


## Coding Frameworks Used

* Java 17
* Spring boot 3.1
* Spring Data JPA
* Lombok
* Mapstruct
* Swagger
* Junit
* Testcontainers
