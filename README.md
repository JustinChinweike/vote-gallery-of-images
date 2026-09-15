# GalleryVote

GalleryVote is a full-stack platform for running timed image competitions. Users can create competitions, upload images, vote on submissions, and follow live leaderboard results.

## Features

- User registration and JWT authentication
- Timed image competitions
- Competition search
- Image uploads with preview and content validation
- Live vote totals and leaderboard rankings
- Duplicate-vote prevention
- Self-vote prevention
- Responsive design for desktop and mobile

## Built With

- Java 21 and Spring Boot
- Spring Security
- Hibernate/JPA
- Angular 20 and TypeScript
- PostgreSQL
- MinIO
- Docker Compose
- JUnit and Mockito
- GitHub Actions

## Requirements

- Java 21 or newer
- Node.js 22 or newer
- Docker Desktop
- Git

Maven does not need to be installed separately because the Maven Wrapper is included.

## Running the Project on Windows

### 1. Start PostgreSQL and MinIO

Make sure Docker Desktop is running, then open a terminal in the project root:

```powershell
docker compose up -d postgres minio
```

### 2. Start the Backend

Run the application from IntelliJ IDEA or use:

```powershell
.\mvnw.cmd spring-boot:run
```

### 3. Start the Frontend

Open another terminal:

```powershell
cd frontend
npm ci
npm start
```

Open [http://localhost:4200](http://localhost:4200) in your browser.

## Running the Tests

Run the backend tests from the project root:

```powershell
.\mvnw.cmd clean test
```

Build the frontend:

```powershell
cd frontend
npm ci
npm run build
```

## Local Services

| Service | Address |
|---|---|
| Frontend | [http://localhost:4200](http://localhost:4200) |
| Backend health check | [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health) |
| Swagger UI | [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) |
| MinIO Console | [http://localhost:9001](http://localhost:9001) |

The default MinIO credentials for local development are:

```text
Username: minioadmin
Password: minioadmin
```

## Project Structure

```text
frontend/                 Angular frontend
src/main/java/            Spring Boot backend
src/main/resources/       Configuration and database resources
src/test/                 Backend tests
.github/workflows/        GitHub Actions workflows
compose.yml               PostgreSQL and MinIO services
```

## Voting Rules

Voting rules are enforced by the backend and database. The application prevents:

- Duplicate votes
- Voting for your own submission
- Voting outside the competition period

## Development Credentials

Credentials stored in `compose.yml` are intended only for local development.

Production credentials should be supplied through environment variables. Real passwords, access keys, and `.env` files should not be committed to the repository.

## Stopping the Services

```powershell
docker compose down
```

This stops the containers while preserving the database and uploaded images.
