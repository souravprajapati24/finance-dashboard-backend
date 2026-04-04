# Finance Data Processing and Access Control Backend
A Spring Boot backend system for managing financial records with role-based access control and dashboard analytics.

## Overview

This project is a backend system designed for managing financial records with role-based access control. It enables different types of users to interact with financial data based on their assigned roles, ensuring secure and controlled access.

The system provides APIs for handling financial transactions such as income and expenses, along with dashboard-level insights including total income, total expenses, net balance, and trends over time. It is built with a focus on clean architecture, maintainability, and real-world backend practices.

The application uses JWT-based authentication for secure access, and includes features such as pagination, filtering, search, and soft delete functionality to enhance usability and data management.

## Tech Stack

- Java
- Spring Boot
- Spring Security (JWT Authentication)
- Spring Data JPA (Hibernate)
- PostgreSQL
- Maven
- SpringDoc OpenAPI (Swagger UI)

## Features

- Role-based user management with support for Admin, Analyst, and Viewer access levels
- JWT-based authentication and authorization using Spring Security
- User management APIs for creating, updating, deleting, and viewing users
- Role assignment and user status management by admin users
- Self-profile update support for authenticated users
- Financial record management for creating, viewing, updating, deleting, and restoring records
- Soft delete functionality for financial records, with separate APIs to view and restore deleted entries
- Pagination and sorting support for record listing and recent activity endpoints
- Search and filtering support for financial records by type, category, date range, and keyword
- Dashboard summary APIs for total income, total expense, net balance, category-wise totals, recent activity, and trends
- Input validation and structured API responses for reliable backend behavior
- Interactive API documentation using SpringDoc Swagger UI

## Roles & Permissions

| Role    | Permissions |
|--------|------------|
| Admin  | Full access (users, records, dashboard) |
| Analyst | View records and access dashboard insights |
| Viewer | View records only (read-only access) |

## API Endpoints

### Authentication
- POST /auth/login → Authenticate user and receive JWT token

### User Management (Admin Only)
- GET /users/all → Get all users (paginated)
- GET /users/user/{id} → Get user by ID
- POST /users/add → Create a new user
- PATCH /users/user/update/{id} → Update user details
- DELETE /users/user/delete/{id} → Delete user
- PATCH /users/role/{id} → Assign role to user
- PATCH /users/status/{id} → Change user status (active/inactive)

### Self User Operations
- PATCH /users/update/self/{id} → Update own profile

### Financial Records
- GET /records/all → Get all records (paginated)
- GET /records/record/{id} → Get record by ID
- POST /records/create → Create a new record (Admin)
- PUT /records/update/{id} → Update record (Admin)
- DELETE /records/delete/{id} → Delete record (Admin)

### Soft Delete Management (Admin Only)
- PATCH /records/soft-delete/{id} → Soft delete a record
- PATCH /records/restore/{id} → Restore a soft-deleted record
- GET /records/deleted/{id} → Get a specific soft-deleted record
- GET /records/soft-deleted → Get all soft-deleted records (paginated)

### Filtering and Search
- GET /records/type/{type} → Filter records by type
- GET /records/category/{category} → Filter by category
- GET /records/date-range → Filter by date range
- GET /records/filter → Combined filtering (type, category, date range)
- GET /records/search → Search records by keyword

### Dashboard (Analyst & Admin)
- GET /dashboard/total-income → Get total income
- GET /dashboard/total-expense → Get total expense
- GET /dashboard/net-balance → Get net balance
- GET /dashboard/category-totals → Get category-wise totals
- GET /dashboard/recent-activity → Get recent records (paginated)
- GET /dashboard/trends → Get financial trends (monthly/weekly)

## Authentication Guide

This application uses JWT (JSON Web Token) based authentication to secure APIs.

### Steps to Authenticate

1. Call the login API:
   POST /auth/login

2. Provide valid credentials (e.g., email/username and password)

3. On successful authentication, a JWT token will be returned in the response

4. You can use the token to access secured APIs using:
   - Postman
   - Swagger UI (interactive API documentation)

### Using Postman

5. Open Postman and select any secured API request

6. Go to the "Authorization" tab

7. Select type: **Bearer Token**

8. Paste your JWT token in the token field

   OR manually add header:
   Key: Authorization  
   Value: Bearer <your_token>

9. Send the request

### Using Swagger UI

10. Open Swagger UI:
    http://localhost:8080/swagger-ui/index.html

11. Click on the "Authorize" button (top-right corner)

12. Enter the token in the following format:
    Bearer <your_token>

13. Click "Authorize" and close the dialog

14. Now you can access all secured endpoints based on your role

### Notes

- The token must be included in the Authorization header for all protected APIs
- Access to endpoints is restricted based on user roles (Admin, Analyst, Viewer)

- ## Setup Instructions

### Prerequisites

Make sure you have the following installed:

- Java (JDK 21 recommended)
- Maven
- PostgreSQL
- Git

### Steps to Run the Project

1. Clone the repository:
   git clone <your-repository-url>

2. Navigate to the project directory:
   cd <project-folder>

3. Configure the database in `application.properties`:
   - Set your PostgreSQL database URL
   - Provide username and password

   Example:
   spring.datasource.url=jdbc:postgresql://localhost:5432/finance_db  
   spring.datasource.username=your_username  
   spring.datasource.password=your_password  

4. Build the project:
   mvn clean install

5. Run the application:
   mvn spring-boot:run

6. The application will start on:
   http://localhost:8080

### Access API Documentation

Swagger UI:
http://localhost:8080/swagger-ui/index.html

## Assumptions

- The system supports three predefined roles: Admin, Analyst, and Viewer, each with specific access permissions
- Only Admin users are allowed to create, update, delete, and manage users and financial records
- Analyst users can view financial records and access dashboard insights but cannot modify data
- Viewer users have read-only access to limited data and cannot access dashboard insights or perform any modifications

- Financial records are categorized as income or expense and are used to compute dashboard summaries
- Soft delete is implemented for financial records, meaning records are not permanently removed but marked as deleted and can be restored

- JWT-based authentication is used, and users must authenticate before accessing protected APIs
- All secured endpoints require a valid token in the Authorization header

- Pagination and sorting are applied to large datasets to improve performance and usability
- Filtering and search functionality are designed for flexible querying based on user inputs

- The database schema is designed for a single-user environment per record (no multi-tenant support)

## Enhancements Implemented

- Implemented JWT-based authentication for secure and stateless API access
- Added role-based access control using Spring Security to restrict endpoints based on user roles
- Implemented pagination and sorting for efficient handling of large datasets
- Added advanced filtering capabilities based on type, category, and date range
- Implemented keyword-based search functionality for financial records
- Designed and implemented soft delete functionality with support for restoring deleted records
- Integrated Swagger (SpringDoc OpenAPI) for interactive API documentation and testing
