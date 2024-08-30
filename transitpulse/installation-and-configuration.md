---
description: To set up TransitPulse, follow the below steps.
---

# Installation & Configuration

### Prerequisites

Before setting up TransitPulse, ensure you have the following installed:

* **Java 8 or higher**
* **Apache Maven**
* **Node.js and npm**
* **Docker and Docker Compose**

***

### Installation Steps

**1. Clone the Repository**

First, clone the TransitPulse repository to your local machine:

```bash
git clone https://github.com/parithoshpoojary/TransitPulse.git
cd TransitPulse
```

**2. Set Up Apache Kafka**

You can set up Kafka either manually or using Docker.

**Option A: Manual Setup**

Download and install Apache Kafka from the [official website](https://kafka.apache.org/downloads). Once installed, follow these steps:

*   Start the ZooKeeper server (required by Kafka):

    ```bash
    bin/zookeeper-server-start.sh config/zookeeper.properties
    ```
*   Start the Kafka server:

    ```bash
    bin/kafka-server-start.sh config/server.properties
    ```
*   Create the `vehicle-location` topic:

    ```bash
    bin/kafka-topics.sh --create --topic vehicle-location --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
    ```

**Option B: Setup with Docker**

If you prefer using Docker, follow these steps:

*   Create a `docker-compose.yml` file in the root directory:

    ```yaml
    version: '2'
    services:
      zookeeper:
        image: wurstmeister/zookeeper:3.4.6
        ports:
          - "2181:2181"

      kafka:
        image: wurstmeister/kafka:latest
        ports:
          - "9092:9092"
        environment:
          KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
          KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
        volumes:
          - /var/run/docker.sock:/var/run/docker.sock
    ```
*   Start Kafka and ZooKeeper using Docker Compose:

    ```bash
    docker-compose up -d
    ```
*   Create the `vehicle-location` topic:

    ```bash
    docker exec -it <kafka-container-id> /bin/sh
    kafka-topics.sh --create --topic vehicle-location --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
    ```

**3. Set Up Cassandra**

You can set up Cassandra either manually or using Docker.

**Option A: Manual Setup**

Download and install Apache Cassandra from the [official website](https://cassandra.apache.org/download/).

*   Start the Cassandra server:

    ```bash
    cassandra -f
    ```
*   Set up the keyspace and tables for storing vehicle data:

    ```sql
    CREATE KEYSPACE transitpulse WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

    USE transitpulse;

    CREATE TABLE vehicle_location (
      vehicle_id UUID PRIMARY KEY,
      timestamp TIMESTAMP,
      latitude DOUBLE,
      longitude DOUBLE,
      speed DOUBLE,
      route_id TEXT
    );
    ```

**Option B: Setup with Docker**

If you prefer using Docker, follow these steps:

*   Create a `docker-compose.yml` file in the root directory (or update the existing one):

    ```yaml
    version: '2'
    services:
      cassandra:
        image: cassandra:latest
        ports:
          - "9042:9042"
    ```
*   Start Cassandra using Docker Compose:

    ```bash
    docker-compose up -d
    ```
*   Access the Cassandra CQL shell:

    ```bash
    docker exec -it <cassandra-container-id> cqlsh
    ```
* Set up the keyspace and tables as described above.

**4. Configure the Application**

Update the `application.properties` file in the Spring Boot project to configure Kafka and Cassandra settings:

```properties
# Kafka Configuration
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=transitpulse-group
spring.kafka.topic.vehicle-location=vehicle-location

# Cassandra Configuration
spring.data.cassandra.contact-points=127.0.0.1
spring.data.cassandra.port=9042
spring.data.cassandra.keyspace-name=transitpulse
spring.data.cassandra.schema-action=update
```

**5. Back-End (Spring Boot)**

Navigate to the `backend` directory and build the Spring Boot application:

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

The backend will start up and begin processing data from Kafka, storing it in Cassandra, and exposing RESTful APIs for the front-end.

**6. Front-End (Angular)**

Navigate to the `frontend` directory, install dependencies, and start the Angular application:

```bash
cd frontend
npm install
ng serve
```

The front-end will start on `http://localhost:4200` and will connect to the Spring Boot backend to visualize the real-time vehicle data on an interactive map.

***

### Configuration

**Kafka Configuration**

Ensure Kafka is properly configured in the `application.yml` file. Key configurations include:

* **Bootstrap Servers:** The address of the Kafka server (default: `localhost:9092`).
* **Consumer Group ID:** The ID for the Kafka consumer group that processes vehicle location data.
* **Topic Names:** The name of the topic (`vehicle-location`) that holds vehicle data.

**Cassandra Configuration**

Configure Cassandra in the `application.yml`file to connect to the correct database instance:

* **Contact Points:** The IP address of the Cassandra server (default: `127.0.0.1`).
* **Port:** The port on which Cassandra is running (default: `9042`).
* **Keyspace Name:** The keyspace name (`transitpulse`) where vehicle data is stored.
* **Schema Action:** Set to `update` to ensure that the database schema is updated automatically.

#### Running the Application

After completing the above steps, the TransitPulse system should be up and running. Access the front-end at `http://localhost:4200` to see real-time public transport vehicle tracking in action.
