
# README.md
## DaNangSOS Backend Setup Guide
## Introduction
This file provides guidance on configuring and running a web application using Spring Boot. The application is configured with multiple environment variables to easily change settings when deploying in different environments.

## Detailed Instructions

### Method 1: Open source code with IDE to run the project

### Step 1: Ensure the environment has JDK 17 and MySQL 8.0.28
Before running the project, make sure your environment has:
- JDK 17
- MySQL 8.0.28

### Step 2: Create databases in MySQL
Open MySQL and create two databases with the following commands:
```sql
CREATE DATABASE rrrs;
CREATE DATABASE address_vn;
```
### Step 3: Open the application.yaml file
Navigate to the src/main/resources directory and open the "application.yaml" file.
### Step 4: Change the parameters as required
- [SERVER_PORT]: Configures the port for the embedded web server. Default value is 8080.
- [ADDRESS]: The IP address the server will listen on. Default value is 0.0.0.0.
First DataSource Configuration
+ DRIVER_1: The type of database driver. Default value is jdbc:mysql.
+ MYSQL_HOST_1: The host address of the MySQL database. Default value is localhost.
+ MYSQL_PORT_1: The port of the MySQL database. Default value is 3307.
+ DATABASE_NAME_1: The name of the database. Default value is demo_rrrs.
+ USER_NAME_1: The database username. Default value is root.
+ USER_PASSWORD_1: The database password. Default value is 123456.
Second DataSource Configuration
+ DRIVER_2: The type of database driver. Default value is jdbc:mysql.
+ MYSQL_HOST_2: The host address of the MySQL database. Default value is localhost.
+ MYSQL_PORT_2: The port of the MySQL database. Default value is 3307.
+ DATABASE_NAME_2: The name of the database. Default value is address_vn.
+ USER_NAME_2: The database username. Default value is root.
+ USER_PASSWORD_2: The database password. Default value is 123456.
+ CORS Configuration
+ URL: The URL of the allowed CORS origin. Default value is http://localhost.
JWT Configuration
+ jwt.expiration: The expiration time of the JWT (in seconds). The value is 2592000 (30 days).
+ jwt.expiration-refresh-token: The expiration time of the refresh token (in seconds). The value is 5184000 (60 days).
+ jwt.secretKey: The secret key for encoding the JWT. Default value is h38+qax6e3ZNYFc5yHjqpwvJidmJUOGdnUUDLok+zVg=.
### Step 5: Run the project
##### Run with IDE
+ Open the project in your IDE (IntelliJ IDEA, Eclipse, etc.).
+ Run the application as a Spring Boot Application.
##### Run with Command Line
+ Navigate to the directory containing the mvnw file.
```
./mvnw clean install
./mvnw spring-boot:run
```
