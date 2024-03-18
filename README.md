# Kafka Dual Write DEMO README

Welcome to the Kafka Dual Write Demo! This project demonstrates the application of transactional outbox pattern to address Kafka Dual Write problem.

Basically, when you send a message, it is stored in a table called message but at the same time there is an entry inserted into table called outbox in a  transactional context. 

A scheduled service reads from the outbox table, sends message to Kafka and delete the message from outbox table.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Before you begin, ensure you have the following installed:

- Java JDK 8 or higher
- Maven
- Docker and Docker Compose

### Building the Project

1. Clone the repository to your local machine.
2. Navigate to the project directory and build the project using Maven:

   ```sh
   mvn clean install

### Running the Application with Docker Compose

1. Build the Docker images with the following command (ensure Docker Daemon is running):

   ```sh
   docker-compose build --no-cache

2. Start the application along with its dependencies by running:

   ```sh
   docker-compose up

### Sending Messages

1. To send messages to the Kafka topic through the Spring Boot application, use the following curl command:

   ```sh
   curl -X POST http://localhost:8080/messages -H 'Content-Type: text/plain' -d 'YourMessageHere'

Replace 'YourMessageHere' with the message you wish to send.

### Simulating Kafka Network Problems
To simulate a network problem with Kafka, you can stop and restart the Kafka container using Docker Compose:

1. Stopping Kafka:

   ```sh
   docker-compose stop kafka

2. Starting Kafka again:

   ```sh
   docker-compose stop kafka

### Environment Variables for Simulation
The application supports two environment variables to simulate specific behaviors:

* DEMO_SIMULATEDELETIONFAILURE: Set to "true" to simulate a failure in deleting messages from the outbox table after they have been sent to Kafka. The default value is "false".

* DEMO_CONSUMERIDEMPOTENCY: Set to "true" to enable idempotent processing on the consumer side. This simulates consumer side idempotency. The default value is "false".

You can modify these variables in the docker-compose.yml file according to your testing needs.