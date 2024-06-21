## Backend Setup (Java, Spring Boot, Maven)

### Instructions

1. **Clone the Repository**

    ```bash
    git clone <repository-url>
    cd <repository-directory>
    ```

2. **Set Up Environment Variables**

    In the `application.properties` file in `src/main/resources` and add the necessary environment variables. Example:

    ```properties
    server.port=8080
    spring.datasource.url=jdbc:mysql://localhost:3306/yourdb
    spring.datasource.username=yourusername
    spring.datasource.password=yourpassword
    ```

    Alternatively, you can use environment variables directly by adding them to your system or using a `.env` file if you have a mechanism to load them.

    The backend should now be running on `http://localhost:8080`.
