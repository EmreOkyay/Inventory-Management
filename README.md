# Java Inventory Management System

This is a basic test-driven inventory management backend system written in **Java**. It's using technologies such as **Spring Boot**, **JPA/Hibernate**, **PostgreSQL**, and **Kafka** in a microservice-like architecture.

It provides core inventory features including user management, product handling, stock tracking, and order management. All communication between services is designed to be asynchronous using Kafka as the main message broker.

The project includes comprehensive unit tests, and is fully integrated with **GitHub Actions**, where each push is automatically tested and built as part of the CI pipeline.


## Features

- User management system
- Product management (CRUD)
- Order creation and tracking
- Stock level updates (increase/decrease)
- PostgreSQL database integration via JPA/Hibernate
- Kafka integration for asynchronous message-driven communication
- RESTful backend developed with Spring Boot
- Clean separation of layers (Controller, Service, Repository, Entity, DTO)
- Automated builds using GitHub Actions CI
- Includes unit tests


## Microservice-like Architecture

Even though the application runs as a single service, the architecture follows a microservice-like separation of concerns across multiple logical services such as:

- User Service  
- Product Service  
- Order Service  
- Stock Service  

These components interact and publish domain events asynchronously using Kafka topics.


## Kafka

Kafka is used as the main event streaming and message broker.  
Whenever products are added, updated, deleted, or purchased, or whenever a user registers, events are published and processed asynchronously to ensure loose coupling and better scalability.


## Database

The application uses PostgreSQL as the primary database engine and leverages Hibernate as its ORM layer to handle persistent data operations.


## CI Pipeline (GitHub Actions)

This project includes GitHub Actions automation where every PR or push to the main branch triggers a CI pipeline:

- Resolve and install dependencies
- Run unit tests
- Build the project

This ensures code quality and reliability for every update to the repository.


## Technologies Used

- Java 17+
- Spring Boot
- Spring Data JPA & Hibernate
- PostgreSQL
- Kafka
- JUnit / Mockito
- Docker
- GitHub Actions (CI)
