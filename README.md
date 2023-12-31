
# DaNangSOS Backend Setup Guide
## Prerequisites
- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html) installed (version 17 or later) 
- [Maven](https://maven.apache.org/download.cgi) installed => Maybe you don't need to download it because the project already has maven
- [MySQL 8](https://dev.mysql.com/downloads/mysql/) database server installed and running

# Step 1
- Make sure you have downloaded the correct JDK version and created environment variables for Java.
   You can try opening cmd /terminal by typing: java -version.
  - Mysql, please create two databases, dnsos, address_vn, script file in /src/main/resources/sql. I have turned off the feature
  hibernate.ddl-auto = create-drop, update The reason I did that is because I locked the database version using the fly way. If the database version is not correct, the system will not run. If you want to change the structure in the database, you must go through the file in src/main/resources/address.dev.db.migration.
# Step 2 run project in localhost
2.1 If you use the IDE to run the project running on localhost, nothing will change. You need to open the application.yaml file and then change the following two default parameters.
         username: ${USER_NAME:ltldev}
         password: ${USER_PASSWORD:123}
Please change: [ltldev, 123] to the account in your mysql:
         username: ${USER_NAME:root}
         password: ${USER_PASSWORD:123456}
2.2 Reload maven and then run the project.
Default path: http://localhost:8080/swagger-ui/index.html
# Step 3 run project in cmd
- You need to be in the path that contains the mvnw file.
- ./mvnw clean install
- ./mvnw spring-boot:run
- Default path: http://localhost:8080/swagger-ui/index.html
