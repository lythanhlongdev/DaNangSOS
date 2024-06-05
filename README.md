# DaNangSOS Backend Setup Guide

# README.md

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

