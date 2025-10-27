# Real Estate Management System (REMS)
**DIGT3101 - Software Development Lifecycle**  
Team: Simon Keleta (219746312) | Suhaib Irfan (218817114)

## Overview
A Java-based MVC enterprise application for managing commercial shopping mall properties.
Handles inventory, appointment scheduling, lease management, billing, and maintenance requests.

## Tech Stack
- **Language:** Java 17
- **ORM:** Hibernate 6 / JPA
- **Database:** MySQL 8
- **Testing:** JUnit 5 + Mockito
- **Build:** Maven

## Setup
1. Install MySQL 8 and run the schema:
   ```bash
   mysql -u root -p < src/main/resources/schema.sql
