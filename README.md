# REMS — Real Estate Management System

A Java Swing desktop application for managing mall retail unit leases, tenant bookings, invoices, and maintenance requests.
Developed for DIGT3101

***

## Tech Stack

| Layer | Technology |
| :-- | :-- |
| Language | Java 17 |
| UI | Java Swing |
| Build Tool | Maven |
| Database | MySQL |
| Connectivity | JDBC |
| Architecture | MVC |


***

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- MySQL 8+


### Clone the Repository

    git clone https://github.com/YOUR_USERNAME/rems.git
    cd rems
    
## Installing MySQL

### Windows

1. Download MySQL Installer from [mysql.com/downloads](https://dev.mysql.com/downloads/installer/)
2. Run the installer and select **MySQL Server + MySQL Workbench**
3. Set root password when prompted
4. Verify installation:

mysql --version

### macOS

1. Install via Homebrew: brew install mysql
2. Start the service: brew services start mysql
3. Secure the install: mysql_secure_installation

### Linux (Ubuntu/Debian)

    sudo apt update
    sudo apt install mysql-server -y
    sudo systemctl start mysql
    sudo mysql_secure_installation
    
### Verify Connection

    mysql -u root -p
    Once connected, create the database:

    CREATE DATABASE rems;
    EXIT;
    
***

Then run the schema as before:

    mysql -u root -p rems < schema.sql
    
***
    
### Build and Run

    mvn clean compile
    mvn exec:java
    
***

## Default Credentials

| Role | Email | Password |
| :-- | :-- | :-- |
| Admin | admin@rems.com | admin123 |
| Agent | agent@rems.com | agent123 |
| Tenant | tenant@rems.com | tenant123 |


***

## Features

### Tenant

- Browse and filter available units by tier, rate, and size
- Book property viewings with an assigned agent
- View and track personal invoices
- Submit and monitor maintenance requests


### Agent

- View assigned appointments and tenant details
- Browse full unit inventory


### Admin

- Manage all units across all malls
- View all invoices and mark as paid
- Manage the full maintenance request queue

***
