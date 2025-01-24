# Alten Ecommerce API Project

## Overview

This is a Spring Boot-based application designed for managing an e-commerce platform. The application is currently configured to run on port **8080**.

## Features

- **In-memory database**: The application uses an H2 in-memory database, which can be accessed via the H2 Console at:\
  [http://localhost:8080/h2-console/](http://localhost:8080/h2-console/)
- **Authentication**: User authentication is integrated, ensuring secure access to the API endpoints.

## How to Run the Postman Collection

To interact with the API, a Postman collection is provided with pre-configured requests. Below are the steps to run the collection:

1. **Start the Application**:

    - Make sure the Spring Boot application is running on port 8080.

2. **Access the Postman Collection**:

    - Open Postman.
    - import the file "alten-ecommerce-api.postman_collection" file.

3. **Create a User**:

    - Start by running the "Create User" request in the Postman collection. This step is essential to generate user credentials.

4. **Automatic Authentication**:

    - The collection is configured to automatically handle login.
    - After creating a user, subsequent requests will trigger a login request to generate an authentication token.
    - The token is automatically added to the `Authorization` header of all requests, ensuring authenticated access to the API endpoints.

5. **Run Any Endpoint**:

    - Once a user is created, you can run any request in the collection. The authentication process ensures all requests are properly secured.

## Notes

- Ensure you check the H2 database console for data persistence during the application runtime.
- Default database credentials for the H2 Console are configured in `application.properties`. Typically:
    - **JDBC URL**: `jdbc:h2:mem:testdb`
    - **Username**: `sa`
    - **Password**: password

With these steps, you should be able to run and test all API endpoints seamlessly using Postman.


