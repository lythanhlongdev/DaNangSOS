# DaNangSOS Backend Setup Guide

## Prerequisites
- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html) installed (version 17 or later) 
- [Maven](https://maven.apache.org/download.cgi) installed (Note: You may not need to download it separately as the project already includes Maven)
- [MySQL 8](https://dev.mysql.com/downloads/mysql/) database server installed and running

# Step 1: Verify JDK and Create Databases
- Ensure you have downloaded the correct version of JDK and created environment variables for Java. You can check by opening cmd/terminal and typing: `java -version`.
- For MySQL, create two databases: `dn_sos` and `address_vn`. The script file is located in `/src/main/resources/sql`.
  The `hibernate.ddl-auto` feature has been turned off (`create-drop`, `update`) to prevent automatic structure updates.
  If you need to modify the structure, use the file in `src/main/resources/address.dev.db.migration`.

# Step 2: Run Project on Localhost

## 2.1 Using IDE
- If using an IDE to run the project on localhost, no changes are needed. Open the `application.yaml` file and modify the following two default parameters:
        + username: ${USER_NAME:ltldev}
        + password: ${USER_PASSWORD:123}
Please change: [ltldev, 123] to the account in your mysql:
   username: ${USER_NAME:root}
   password: ${USER_PASSWORD:123456}
- Reload Maven and run the project.
- Default path: http://localhost:8080/swagger-ui/index.html
