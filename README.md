# Social Media API (Spring Boot)

This is the backend API for a custom full-stack social media application.

## Tech Stack
- **Java 21 / Spring Boot 3**
- **PostgreSQL** 
- **Spring JDBC Template** 
- **Spring Security** (Authentication & custom CORS handling)
- **Thumbnailator** (Image compression for avatar and artwork uploads) 
- **SLF4J** (Application logging)

## Features & Modules
1. **Auth (`/user/login`, `/user/register`)**: User registration, login, and password.
2. **Profile (`/user/profile`)**: User profile and avatar uploads (`multipart/form-data`).
3. **Artwork (`/user/artwork`)**: Image uploads, resizing, and fetching user galleries.
4. **Game (`/user/game`)**: Saves and obtains game history.

## Prerequisites
- Java 21+
- Maven
- PostgreSQL running on default port (5432)

## Setup & Run Instructions

1. **Database Setup**
    - Create a new PostgreSQL database named `socialmedia`.
    - Run the `database.sql` script to create the necessary tables.

2. **Configure Application**
    - Open `src/main/resources/application.properties`.
    - Update database credentials:
      ```properties
      spring.datasource.username=YOUR_DB_USERNAME
      spring.datasource.password=YOUR_DB_PASSWORD
      ```

3. **Run the Application**
    - Run the app directly via Maven:
      ```bash
      mvn spring-boot:run
      ```
    - The server will start on `http://localhost:3001`.

## Architectural Notes
- **CORS & Headers:** The frontend React app uses custom headers (`username`, `game`). The `SecurityConfig` is configured to allow these specific headers and handle preflight `OPTIONS` requests gracefully.
- **Image Storage:** Currently, images (avatars and artworks) are compressed using Thumbnailator and stored directly in the database as `byte[]` for easier initial deployment and MVP testing.
- **Future Roadmap:** Transition to JWT for robust token-based authentication and migrate image blobs to AWS S3 / local file system to reduce database I/O load.