# Social Media API (Spring Boot)

This is the backend API for a full-stack **cool art social media** application.

## Tech Stack

* **Java 21 / Spring Boot 3**
* **PostgreSQL**
* **Spring JDBC Template**
* **Spring Security & JJWT** (Stateless JWT Authentication & custom CORS handling)
* **Thumbnailator** (Image compression for avatar and artwork uploads)
* **SLF4J** (Application logging)

## Features & Modules

1. **Auth (`/user/auth`)**: User registration and secure login with JWT issuance.
2. **Profile (`/user/profile`)**: User profile fetching and avatar uploads (`multipart/form-data`).
3. **Artwork (`/user/artworks`)**: Image uploads, automatic resizing, fetching user galleries, and ownership-validated deletion.
4. **Game (`/user/game`)**: Saves and retrieves dynamic game history payloads.

## Prerequisites

* Java 21+
* Maven
* PostgreSQL running on default port (5432)

## Setup & Run Instructions

1. **Database Setup**
* Create a new PostgreSQL database named `socialmedia`.
* Run the `database.sql` script to create the necessary tables.


2. **Environment Variables Configuration**
   This application enforces secure credential management via environment variables. Before running, set the following variables in your local environment or IDE run configuration:
* `DB_USER`: Your PostgreSQL username
* `DB_PASSWORD`: Your PostgreSQL password
* `JWT_SECRET`: A secure HMAC-SHA key (minimum 256-bit / 32 characters)
* `JWT_EXPIRATION_MS`: Token expiration time in milliseconds (e.g., `7200000` for 2 hours)

3. **Run the Application**
* Run the app directly via Maven using your terminal:
```bash
mvn spring-boot:run
```

* The server will start on `http://localhost:3001`.

## Architectural Notes

* **Security & Stateless Authentication:** The application uses JWT (JSON Web Tokens) for security. And then the `SecurityContextHolder` extracts and validates the user identity directly from the token via a custom filter.
* **CORS Configuration:** Configured to integrate with the React frontend.
* **Image Storage:** Currently, images (avatars and artworks) are compressed using Thumbnailator and stored directly in the database as `byte[]`.
* **Future Roadmap:** Migrate image blobs to cloud object storage (e.g., AWS S3) to reduce database I/O load and improve scalability.
