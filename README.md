# Training Institute Counsellor Portal

A Spring Boot web application for managing training institute counsellor activities. The application is built using Spring Boot, Spring Data JPA, Thymeleaf, and MySQL, and can be containerized and deployed using Docker.

## Technologies Used

* Java 17
* Spring Boot
* Spring Data JPA
* Thymeleaf
* MySQL
* Maven
* Docker

## Project Structure

```text
training-institute-counsellor-portal/
├── src/
├── pom.xml
├── Dockerfile
├── mvnw
├── mvnw.cmd
├── .gitignore
└── README.md
```

## Features

The application provides a web-based interface for managing counsellor-related operations for a training institute.

The project demonstrates:

* Spring Boot web application development
* Thymeleaf-based UI
* Database integration using Spring Data JPA
* MySQL database connectivity
* Maven-based build and dependency management
* Docker containerization

## Prerequisites

Install the following before running the application:

* Java 17 or later
* Maven 3.x
* MySQL
* Docker

## Database Configuration

Configure the MySQL database connection in your application configuration.

For example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
```

Do not commit real database passwords or other sensitive credentials to a public GitHub repository. Use environment variables or external configuration for secrets.

## Run the Application Using Maven

Clone the repository:

```bash
git clone https://github.com/Chaitanya4K/training-institute-counsellor-portal.git
```

Go to the project directory:

```bash
cd training-institute-counsellor-portal
```

Build the application:

```bash
mvn clean package
```

Run the Spring Boot application:

```bash
java -jar target/<application-name>.jar
```

The application can then be accessed through:

```text
http://localhost:8080/
```

## Run Using Docker

Build the Docker image:

```bash
docker build -t counsellor-portal .
```

Run the container:

```bash
docker run -d -p 8080:8080 --name counsellor-portal-container counsellor-portal
```

Check the running container:

```bash
docker ps
```

View container logs:

```bash
docker logs counsellor-portal-container
```

Access the application:

```text
http://localhost:8080/
```

## Docker Workflow

```text
Spring Boot Source Code
        ↓
      Maven
        ↓
   Spring Boot JAR
        ↓
     Dockerfile
        ↓
    Docker Image
        ↓
 Docker Container
        ↓
  Application :8080
```

## GitHub

The project source code is maintained in GitHub and can be used for learning and demonstrating a basic Spring Boot and Docker deployment workflow.

## Author

Chaitanya
