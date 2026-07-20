# GalleryVote

GalleryVote is a web application for running image competitions. People can create an account, open themed competitions, upload original images, vote, and follow the leaderboard.

The original school-project version is preserved in the Git tag `legacy-servlet-v1`. The current application is GalleryVote V2.

## What you can do

- Register, sign in, and sign out
- Create an image competition with start and end times
- Search and browse competitions
- Upload JPEG, PNG, GIF, and WebP images
- Preview an image before uploading it
- Vote once for each eligible submission
- See live vote totals and leaderboard positions
- Prevent people from voting for their own images
- Prevent duplicate votes
- Use the site on desktop and mobile screens

## Built with

- Java 21 and Spring Boot
- Angular 20 and TypeScript
- PostgreSQL for users, competitions, submissions, and votes
- MinIO for uploaded images
- Docker Compose for local setup
- Maven and npm for builds
- GitHub Actions for automatic checks

## What you need

Install these before starting:

- Java 21 or newer
- Node.js 22 or newer
- Docker Desktop
- Git

You do not need to install Maven separately. The project includes it.

## Run the project on Windows

Open the project folder in IntelliJ IDEA.

### 1. Start PostgreSQL and MinIO

Open a terminal in the main project folder:

```powershell
docker compose up -d postgres minio
```

Docker Desktop must be running first.

### 2. Start the Java application

Open this file in IntelliJ:

```text
src/main/java/com/galleryvote/GalleryVoteApplication.java
```

Click the green Run button beside `main`.

You can also start it from the terminal:

```powershell
.\mvnw.cmd spring-boot:run
```

### 3. Start the website

Open a second terminal:

```powershell
cd frontend
npm ci
npm start
```

Open [http://localhost:4200](http://localhost:4200).

## Test the main workflow

1. Click **Join now** and register Alice.
2. Click **Create contest**.
3. Create a competition and choose its voting dates.
4. Open the competition and upload an image.
5. Sign out and register Bob.
6. Open Alice's competition as Bob.
7. Vote for Alice's image after voting has opened.
8. Confirm that the leaderboard shows Bob's vote.
9. Try voting again and confirm that the second vote is rejected.
10. Upload an image as Bob and confirm that Bob cannot vote for his own image.

## Run the checks

Test the Java application from the main project folder:

```powershell
.\mvnw.cmd clean test
```

Check the website build:

```powershell
cd frontend
npm ci
npm run build
```

## Useful addresses

| Page | Address |
|---|---|
| GalleryVote website | [http://localhost:4200](http://localhost:4200) |
| Java health check | [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health) |
| API documentation | [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) |
| MinIO management page | [http://localhost:9001](http://localhost:9001) |

The development MinIO username and password are both `minioadmin`.

## Project folders

```text
frontend/                 Angular website
src/main/java/            Java application
src/main/resources/       Settings and database setup
src/test/                 Automated Java tests
.github/workflows/        GitHub automatic checks
compose.yml               Local PostgreSQL and MinIO services
```

## Keeping passwords safe

The passwords in `compose.yml` are only for local development. Real deployment passwords must be supplied through private environment settings. Do not commit `.env` files, real passwords, or cloud access keys.

Uploaded images, local settings, build results, IDE files, and installed packages are excluded from Git.

## Stop the local services

```powershell
docker compose down
```

To keep your database and uploaded images for the next run, do not add `-v` to that command.

## License

No license has been selected yet. All rights are reserved by the repository owner.
