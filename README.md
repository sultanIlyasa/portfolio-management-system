# 📈 Portfolio Management System

> ** Investment portfolio management platform built with modern Java and Spring Boot**

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)]()
[![Coverage](https://img.shields.io/badge/coverage-85%25-green.svg)]()
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

## 🎯 Project Overview

A comprehensive microservices-based platform for managing investment portfolios, providing real-time market data integration, transaction processing, and performance analytics. Built to demonstrate enterprise-level backend development skills using modern Java technologies.

### 🌟 **Key Features**

- **Portfolio Management** - Create, update, and monitor investment portfolios
- **Real-time Market Data** - Live stock prices and market information
- **Transaction Processing** - Buy/sell orders with comprehensive tracking
- **Performance Analytics** - Portfolio performance metrics and risk analysis
- **User Management** - Secure user authentication and profile management
- **Event-Driven Architecture** - Asynchronous processing with Kafka
- **RESTful APIs** - Well-designed APIs following industry standards

---
## 🏗️ System Architecture

### Microservices Architecture
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   API Gateway   │    │  User Service   │    │Portfolio Service│
│   (Port 8080)   │    │   (Port 8081)   │    │   (Port 8082)   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         └───────────────────────┼───────────────────────┘
                                 │
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│Transaction Svc  │    │ Market Data Svc │    │Analytics Service│
│   (Port 8083)   │    │   (Port 8084)   │    │   (Port 8085)   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         └───────────────────────┼───────────────────────┘
                                 │
              ┌─────────────────────────────────┐
              │        Message Broker           │
              │      (Apache Kafka)             │
              └─────────────────────────────────┘
```

### Technology Stack

#### **Backend Technologies**
- **Java 17** - Latest LTS with modern language features
- **Spring Boot 3.2** - Enterprise application framework
- **Spring Security 6** - Authentication and authorization
- **Spring Data JPA** - Data persistence layer
- **Apache Kafka** - Event streaming platform
- **Redis** - Distributed caching and session storage

#### **Databases**
- **PostgreSQL** - Primary RDBMS for transactional data
- **MongoDB** - Document store for market data and analytics
- **H2** - In-memory database for development and testing

#### **DevOps & Infrastructure**
- **Docker** - Containerization
- **Kubernetes** - Container orchestration
- **Maven** - Dependency management and build automation
- **JUnit 5** - Unit and integration testing
- **Testcontainers** - Integration testing with real databases

---
