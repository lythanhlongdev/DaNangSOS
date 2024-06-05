
# README.md
## DaNangSOS Backend Setup Guide
## Introduction
This file provides guidance on configuring and running a web application using Spring Boot. The application is configured with multiple environment variables to easily change settings when deploying in different environments.

## Detailed Instructions

## Method 1: Open source code with IDE to run the project

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
- SERVER_PORT: Configures the port for the embedded web server. Default value is 8080.
- ADDRESS: The IP address the server will listen on. Default value is 0.0.0.0.
#### First DataSource Configuration
+ DRIVER_1: The type of database driver. Default value is jdbc:mysql.
+ MYSQL_HOST_1: The host address of the MySQL database. Default value is localhost.
+ MYSQL_PORT_1: The port of the MySQL database. Default value is 3307.
+ DATABASE_NAME_1: The name of the database. Default value is demo_rrrs.
+ USER_NAME_1: The database username. Default value is root.
+ USER_PASSWORD_1: The database password. Default value is 123456.
#### Second DataSource Configuration
+ DRIVER_2: The type of database driver. Default value is jdbc:mysql.
+ MYSQL_HOST_2: The host address of the MySQL database. Default value is localhost.
+ MYSQL_PORT_2: The port of the MySQL database. Default value is 3307.
+ DATABASE_NAME_2: The name of the database. Default value is address_vn.
+ USER_NAME_2: The database username. Default value is root.
+ USER_PASSWORD_2: The database password. Default value is 123456.
+ CORS Configuration
+ URL: The URL of the allowed CORS origin. Default value is http://localhost.
#### JWT Configuration
+ jwt.expiration: The expiration time of the JWT (in seconds). The value is 2592000 (30 days).
+ jwt.expiration-refresh-token: The expiration time of the refresh token (in seconds). The value is 5184000 (60 days).
+ jwt.secretKey: The secret key for encoding the JWT. Default value is "h38+qax6e3ZNYFc5yHjqpwvJidmJUOGdnUUDLok+zVg=."
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
## Method 2: Running the project using the packaged <NameProject>.jar file

- Ensure you have JDK 17 and MySQL 8.0.28 installed.

### For Windows
#### Step 1: Create a file named `run.bat` and add the following content:
```bat
@echo off
rem Environment Variables for MySQL
set SERVER_PORT=8080
set MYSQL_PORT=3306
set MYSQL_HOST=localhost
set USER_NAME=root
set USER_PASSWORD=12345678
set DATABASE_NAME1=rrrs
set DATABASE_NAME2=address_vn
rem Run the project JAR
java -jar <NameProject>.jar
```
#### Step 2: Grant execution permission to the run.bat file. Open Command Prompt in the same directory as the .jar file and the run.bat file. Right-click on the run.bat file and choose "Run as administrator"
### For Linux
#### Step 1: Create a file named run.sh and add the following content or customize as needed:
```sh
#!/bin/bash
# Environment Variables for MySQL
export SERVER_PORT=8080
export MYSQL_PORT=3306
export MYSQL_HOST=localhost
export USER_NAME=root
export USER_PASSWORD=12345678
export DATABASE_NAME1=rrrs
export DATABASE_NAME2=address_vn

# Run the project JAR
java -jar <NameProject>.jar
```
##### Step 2: Open a terminal in the same directory as the .jar file and the run.sh file, then run the command:
```sh
./run.sh
```
## Method 3: Running the project using Docker
- You only need to install Docker on your machine; there's no need to install JDK 17 or MySQL 8.0.28.
### Step 1: Create a file named docker-compose.yaml and add the following content:
```yaml
version: '3.7'
services:
  mysql:
    image: mysql:8.0.28
    container_name: mysql
    restart: always
    ports:
      - 3307:3306
    environment: 
      MYSQL_ROOT_PASSWORD: 123456
      MYSQL_USER: ltldev
      MYSQL_PASSWORD: 123
    networks:
      - springboot
    volumes:
      - mysql-data:/var/lib/mysql

  dnsos1:
    image: ltldev/dnsos:1.1.9.3
    container_name: dnsos
    restart: always
    depends_on:
      - mysql
    ports:
      - 8090:8080
    networks:
      - springboot
    environment:
      - ADDRESS=0.0.0.0
      - SERVER_PORT=8080
      - MYSQL_INITDB_CHARSET:utf8
      - MYSQL_CHARSET:utf8
      - MYSQL_PORT_1=3306
      - MYSQL_HOST_1=mysql
      - USER_NAME_1=root
      - USER_PASSWORD_1=123456
      - DATABASE_NAME_1=rrrs
      - MYSQL_PORT_2=3306
      - MYSQL_HOST_2=mysql
      - USER_NAME_2=root
      - USER_PASSWORD_2=123456
      - DATABASE_NAME_2=address_vn

networks:
  springboot:
    driver: bridge

volumes:
  mysql-data:
    driver: local
    driver_opts:
      type: none
      # Open comment if using Linux
      #device: /home/mun/Docker/Disks/mysql/data
      # Open comment if using Windows
      #device: c:\docker\database\mysql\data
      o: bind
```
### Step 2: Open a terminal in the same directory as the "docker-compose.yaml" file, then run the command:
```
docker compose up
```
