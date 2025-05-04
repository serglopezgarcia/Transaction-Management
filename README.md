
# Transaction Management and Fraud Detection API

## Overview

This project is a Spring Boot application designed to manage financial transactions with fraud detection capabilities. It provides RESTful API endpoints for creating, retrieving, and deleting transactions, along with a simple fraud detection algorithm.

## Prerequisites

- Java 17
- Maven
- Docker (for database setup)

## Setup Instructions

### Clone the Repository

Clone the project repository to your local machine:

```bash  
git clone https://github.com/serglopezgarcia/Transaction-Management
```  
```bash  
cd transaction-management-and-fraud-detection
```  

### Docker Setup
For using Docker for database setup, ensure Docker is installed and running. Use the provided docker-compose.yml file to set up the database.
```shell  
docker-compose up -d
```

### Build the Project
Use Maven to clean and build the project:

```shell  
mvn clean install
```  

This command will compile the source code, run the tests, and package the application into a JAR file located in the target directory.

### Run the Application
Start the Spring Boot application using Maven:

```shell  
mvn spring-boot:run
```  
This command will start the application, allowing you to interact with the API endpoints.

### API Endpoints
* **Create Transaction:** POST `/transactions`
    * **Request Body:** JSON object containing account number, transaction type, amount
  ```json  
  {
     "accountNumber":  "02052025",
     "transactionType":  "Debit",
     "amount":  1000.00
  }
  ```    
* **Retrieve Transaction by ID:** GET `/transactions/{id}`
* **Retrieve Transactions for a Specific Account:** GET `/accounts/{accountNumber}/transactions`
* **Delete Transaction:** DELETE `/transactions/{id}`
    * **Note:** Transactions older than 24 hours cannot be deleted.

### Fraud Detection
The application includes a fraud detection algorithm that flags transactions by throwing an exception if the total amount for any account exceeds €10,000 within 24 hours.

### Run Tests
Use Maven to execute the test suite:

```shell  
mvn test
``` 
This command will run all unit tests, ensuring the application's functionality and compliance with the Test-Driven Development (TDD) approach.
