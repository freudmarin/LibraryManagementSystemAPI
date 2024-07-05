This project is a Library Management System API developed using Spring Boot, Java, and SQL. It provides a RESTful interface for managing books and authors within a library system. 
The API supports operations such as creating, retrieving, updating, and deleting books and authors.


## Technologies Used
- **Java 21**:
- **Spring Boot v3.3.1**:
- **Maven**:
- **JUnit and Mockito**:
- **MockMvc**: Used for Unit and Integration Testing of presentation layer (controllers)
- **Spring Security**: For securing the application via Basic Authentication.
- **Hibernate**: For ORM mapping between Java objects and SQL database.
- **JPA**
- **H2 Database**: An in-memory database used for integration testing 
- **Swagger**: For API documentation and testing
- **MapStruct**: For mapping between entities and DTOs
## Architecture Decisions
- **RESTful API Design**: The application follows REST principles to ensure a scalable and maintainable architecture.
- **Layered Architecture**: The application is structured into layers (Controller, Service, Repository) to separate concerns and enhance code readability.
- **DTO Pattern**: Data Transfer Objects (DTOs) are used for transferring data between processes, reducing the number of calls and simplifying the objects exchanged.
- **In-Memory Authentication**: Utilizes Spring Security for Basic Authentication with in-memory user details for simplicity in development and testing phases.
- **Exception Handling**: Centralized exception handling mechanism for cleaner error management across the application.
- **Unit Testing**: Utilizes JUnit and Mockito for unit testing of service and controller classes.


## The Library Management System API is organized into several key packages, each serving a distinct role within the application:

- `com.marin.librarymanagementsystemapi.controllers`: Contains the controller classes that handle HTTP requests and responses.
- `com.marin.librarymanagementsystemapi.services`: Includes service classes that contain business logic and interact with the repository layer.
- `com.marin.librarymanagementsystemapi.repositories`: Contains the repository interfaces, providing a layer of abstraction over data access and manipulation.
- `com.marin.librarymanagementsystemapi.dtos`: Consists of Data Transfer Objects (DTOs) used to transfer data between the application's layers without exposing internal entities.
- `com.marin.librarymanagementsystemapi.models`: Contains the entity classes that represent the application's data model and are mapped to the database.
- `com.marin.librarymanagementsystemapi.config`: Includes configuration classes that setup and customize different aspects of the Spring Boot application, such as security configurations.

## Assumptions

- ISBN is unique for each book. For handling validation of ISBNs a custom annotation `@ValidISBN` is used.
- For integration testing, an in-memory H2 database is used to simulate the behavior of a real database. Another schema is created for testing purposes.
- The list of books for a given author in not displayed in the response when retrieving an author. fetch = FetchType.LAZY is used to avoid loading all books when retrieving an author.


## Running the Project
1. **Clone the Repository** git clone https://github.com/freudmarin/LibraryManagementSystemAPI.git
2. **Navigate to Project Directory** 
3. **Open the project in your IDE (IntelliJ IDEA, Eclipse, etc.)**
4. **Clean and build the project using Maven**
    mvn clean compile
5. Connect to postgres database
    - Create a database named `LibraryManagementSystem`
    - Update the `application.properties` file with your database credentials
    - Run the application 
6. **Run the Application** mvn spring-boot:run
   The application will start and be accessible at `http://localhost:8080`.
7. **Run the Tests** mvn test
## Accessing the API
- The API is secured with Basic Authentication. Use the following credentials for testing:
    - **Username**: marin
    - **Password**: test
- Access the Swagger UI for API documentation and testing at `http://localhost:8080/swagger-ui.html`.


@Author: Marin Dulja
