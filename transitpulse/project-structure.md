# Project Structure

The TransitPulse project is organized into two main components: the backend (Spring Boot) and the frontend (Angular). Below is a breakdown of the directory structure:

```plaintext
transitpulse/
├── backend/                     # Backend codebase (Spring Boot)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── transitpulse/
│   │   │   │           ├── controller/       # REST controllers
│   │   │   │           ├── model/            # Domain models
│   │   │   │           ├── repository/       # Data access layer (Cassandra repository interfaces)
│   │   │   │           ├── service/          # Service layer (business logic)
│   │   │   │           ├── config/           # Configuration classes (Kafka, Cassandra, etc.)
│   │   │   │           └── TransitPulseApplication.java  # Main Spring Boot application
│   │   ├── resources/
│   │   │   ├── application.properties        # Spring Boot application properties
│   │   │   ├── logback-spring.xml            # Logging configuration
│   │   │   └── templates/                    # (not applicable)
│   │   └── test/                             # Unit and integration tests
│   │       └── java/
│   │           └── com/
│   │               └── transitpulse/
│   │                   ├── controller/       # Test cases for controllers
│   │                   ├── service/          # Test cases for services
│   │                   └── repository/       # Test cases for repositories
│   ├── pom.xml                               # Maven build configuration
│   └── Dockerfile                            # Dockerfile for backend service
├── frontend/                    # Frontend codebase (Angular)
│   ├── src/
│   │   ├── app/
│   │   │   ├── components/                   # Angular components
│   │   │   ├── services/                     # Services for API calls
│   │   │   ├── models/                       # Data models for frontend
│   │   │   ├── app-routing.module.ts         # Routing module
│   │   │   ├── app.module.ts                 # Root module
│   │   │   └── app.component.ts              # Root component
│   │   ├── assets/                           # Static assets (images, icons, etc.)
│   │   ├── environments/                     # Environment configurations (development, production)
│   │   ├── index.html                        # Main HTML file
│   │   ├── styles.css                        # Global styles
│   │   └── main.ts                           # Main entry point for Angular
│   ├── angular.json                          # Angular CLI configuration
│   ├── package.json                          # npm package dependencies
│   └── Dockerfile                            # Dockerfile for frontend service
├── docker-compose.yml            # Docker Compose configuration for Kafka, Cassandra, etc.
└── README.md                     # Project README file
```

#### Key Directories and Files

* **backend/**: Contains the Spring Boot application, including controllers, services, and repository layers. The `config/` directory holds configuration files for Kafka, Cassandra, and other services.
* **frontend/**: Contains the Angular application, including components, services, and routing configuration. The `assets/` directory holds static resources like images and styles.
* **docker-compose.yml**: Defines the Docker services for running Kafka, Cassandra, and other dependencies. This file allows you to easily spin up the necessary infrastructure with a single command.
* **Dockerfiles**: Separate Dockerfiles are provided for both the backend and frontend services, enabling containerized deployment of the entire application.
* **README.md**: The project documentation, including setup instructions, configuration details, and an overview of the system architecture.
