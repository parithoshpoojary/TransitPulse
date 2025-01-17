# Architecture

The architecture of TransitPulse is designed for scalability and efficiency. Below is a detailed explanation of each component within the system.

## <mark style="color:purple;">High Level Design (HLD)</mark>

### Introduction

The TransitPulse system is designed to provide real-time tracking of public transport vehicles and display their locations on an interactive map. This system integrates several technologies including Apache Kafka for real-time data streaming, Spring Boot for backend data processing, and Angular for front-end visualization. The High-Level Design (HLD) provides an overview of the system architecture, explaining how data flows from the vehicles to the end-user interface and highlighting the key components involved.

### System Overview

#### Key Components

1. **Public Transport Vehicles (Data Sources)**
2. **Kafka for Data Streaming**
3. **Spring Boot for Backend Processing**
4. **Database (Cassandra)**
5. **Angular for Front-End Visualization**

Each of these components plays a crucial role in the system, enabling the seamless collection, processing, and presentation of real-time data.

#### High-Level Design Diagram

_The HLD diagram here provides a visual representation of the overall system, depicting how each component interacts with the others. It shows the flow of data from the public transport vehicles through Kafka and Spring Boot, optionally into a database, and finally to the Angular front-end for visualization._



<figure><img src="../.gitbook/assets/TransitPulse HLD.png" alt=""><figcaption></figcaption></figure>

### Detailed Explanation of Each Component

#### 1. Public Transport Vehicles (Data Sources)

**Role:** Public transport vehicles are equipped with GPS devices that transmit real-time location data. This data is the foundational input for the TransitPulse system.

* **GPS Tracking:** Each vehicle is equipped with a GPS unit that constantly updates its location data, including latitude, longitude, speed, and direction.
* **Data Transmission:** This data is sent over a network (e.g., cellular) to the Kafka broker in real-time. Each vehicle's data is tagged with a unique identifier, allowing the system to track individual vehicles.

#### 2. Kafka for Data Streaming

**Role:** Apache Kafka acts as the central messaging system, streaming real-time data from the vehicles to the backend for processing.

* **Kafka Broker:** The Kafka broker is responsible for receiving and managing the data streams. It handles the incoming data from the vehicles and ensures that it is properly queued for processing.
* **Kafka Topics:** Data is organized into topics, such as `vehicle-location`, where each topic represents a specific type of data stream. In this system, the primary topic is the `vehicle-location` topic, which holds all the location data from the vehicles.
* **Scalability and Fault-Tolerance:** Kafka is chosen for its ability to handle high-throughput data streams, ensuring that the system can scale to track thousands of vehicles simultaneously. Kafka’s distributed nature also provides fault-tolerance, meaning the system can continue operating even if some components fail.

#### 3. Spring Boot for Backend Processing

**Role:** The backend, built with Spring Boot, consumes the data from Kafka, processes it, and makes it available to the front-end or other systems via RESTful APIs.

* **Kafka Consumer:** The Kafka Consumer component in Spring Boot listens to the `vehicle-location` topic and consumes the real-time data as it arrives.
* **Data Processing:** Once the data is consumed, it undergoes processing. This includes:
  * **Validation:** Ensuring that the data is complete and accurate.
  * **Transformation:** Converting the raw data into a more usable format, if necessary.
  * **Aggregation (Optional):** Aggregating data from multiple sources or over time to provide more insightful information (e.g., average speed of a vehicle over time).
* **Business Logic:** The backend also includes business logic to handle scenarios such as:
  * **Alerts:** Generating alerts if a vehicle deviates from its expected route.
  * **Geo-fencing:** Detecting when a vehicle enters or exits a predefined geographic area.
* **REST API:** Processed data is exposed via RESTful APIs, allowing the front-end to fetch and display the information in real-time. The API is designed to be scalable and secure, supporting high volumes of requests.

#### 4. Cassandra Database

**Role:** Cassandra serves as the primary database for storing historical data, enabling efficient querying and analysis of large volumes of data generated by public transport vehicles.

* **Data Storage:** Cassandra is used to store processed vehicle location data, providing high availability and scalability. The data stored includes:
  * **Vehicle Location History:** Storing the historical GPS coordinates of each vehicle, allowing for route playback and historical analysis.
  * **Performance Metrics:** Metrics such as average speed, total distance traveled, and time spent in transit, stored for reporting and analysis.
* **Cassandra Features:**
  * **Scalability:** Cassandra is a distributed NoSQL database designed to handle large volumes of data across many servers without any single point of failure.
  * **High Write Throughput:** Cassandra's architecture is optimized for high write operations, making it ideal for storing real-time streaming data from thousands of vehicles.
  * **Fault-Tolerance:** With built-in replication, Cassandra ensures data availability even in the event of node failures.
* **Integration with Spring Boot:** Spring Boot integrates with Cassandra using Spring Data Cassandra, enabling smooth interaction with the database. The system leverages Cassandra’s powerful querying capabilities to efficiently retrieve and analyze historical data.

#### 5. Angular for Front-End Visualization

**Role:** The front-end, built with Angular, provides an interactive map that visualizes the real-time location of public transport vehicles.

* **HTTP Client Service:** The Angular application uses an HTTP client service to fetch data from the Spring Boot REST API. This service regularly polls the API to ensure that the map is updated with the latest vehicle locations.
* **Map Component:** The map component is the core of the front-end. It visualizes the real-time data, showing the current positions of the vehicles on a map.
  * **Markers:** Each vehicle is represented by a marker on the map. The marker's position is updated in real-time as new data is received.
  * **Vehicle Information:** Clicking on a marker provides additional information about the vehicle, such as its speed, direction, and route.
  * **Real-Time Updates:** The map updates automatically as new data is received from the backend, providing a live view of the public transportation network.
* **User Interface (UI):** The UI is designed to be intuitive and responsive, providing a seamless experience across devices. Users can filter vehicles by route, type, or other criteria, and view detailed information on each vehicle.

#### Data Flow Overview

The data flow in the TransitPulse system can be summarized as follows:

1. **Data Collection:** Public transport vehicles transmit real-time GPS data, including their location, speed, and route information.
2. **Data Streaming:** This data is sent to the Kafka broker, where it is organized into topics like `vehicle-location`.
3. **Data Processing:** Spring Boot’s Kafka Consumer retrieves the data from the Kafka topic, processes it (validation, transformation, business logic), and prepares it for storage or API exposure.
4. **Data Storage in Cassandra:** Processed data is stored in Cassandra for historical analysis and reporting. The database's scalability and high availability make it ideal for handling large volumes of real-time data.
5. **Data Visualization:** The Angular front-end fetches the processed data via the REST API and displays it on an interactive map, updating in real-time.

### System Scalability and Performance Considerations

#### Scalability

* **Kafka’s Role:** Kafka’s distributed architecture ensures that the system can handle increasing numbers of vehicles without degradation in performance. By adding more Kafka brokers, the system can scale horizontally, managing more data streams efficiently.
* **Spring Boot Microservices:** The backend processing is designed as microservices, allowing individual components to scale independently. For example, if data processing becomes a bottleneck, additional instances of the processing service can be deployed.
* **Database Scaling:** If a database is used, it can be scaled using techniques like sharding (for NoSQL databases) or replication (for relational databases) to handle larger datasets.

#### Performance

* **Low Latency:** The system is designed for low-latency data processing, ensuring that vehicle location data is processed and displayed with minimal delay.
* **Efficient Data Handling:** Kafka’s ability to handle high-throughput data streams ensures that even during peak times, the system remains responsive.
* **Asynchronous Processing:** By processing data asynchronously, the system ensures that data processing does not block other operations, maintaining overall system performance.

### Security Considerations

* **Data Encryption:** Data transmitted between vehicles and the Kafka broker should be encrypted to prevent unauthorized access.
* **Authentication and Authorization:** Access to the REST API should be secured using authentication mechanisms such as OAuth2. Additionally, role-based access control (RBAC) should be implemented to ensure that only authorized users can access or modify data.
* **Data Anonymization:** If required, the system can anonymize vehicle data to protect privacy, especially if dealing with personal or sensitive information.

### Conclusion

The TransitPulse system is a robust, scalable solution for real-time public transport tracking. By leveraging the strengths of Kafka, Spring Boot, and Angular, it provides a seamless, real-time experience from data collection to visualization. This High-Level Design (HLD) document outlines the key components and data flow within the system, providing a clear understanding of how TransitPulse operates and how it can be further developed or customized to meet specific needs.
