# GalleryVote V2

GalleryVote is a secure image-competition platform. People create themed contests, submit original images, vote once per submission, and follow live leaderboards.

## Architecture

- Java 21, Spring Boot, Spring Security, Spring Data JPA, Bean Validation
- PostgreSQL managed by Flyway
- Angular 20 client in `frontend/`
- MinIO locally; the same object-storage boundary can target S3
- OpenAPI UI at `/swagger-ui.html`; health and metrics under `/actuator`
- JUnit 5, Mockito, Testcontainers dependencies, Docker Compose, and GitHub Actions

The application is a modular monolith organized by business capability (`auth`, `user`, `contest`, `submission`, and `vote`). PostgreSQL's unique constraint is the final authority for vote idempotency.

## Run locally

Requirements: Java 21+, Docker with Compose, and Node 22+.

```bash
docker compose up -d postgres minio
./mvnw spring-boot:run
cd frontend
npm ci
npm start
```

Open `http://localhost:4200`. MinIO's console is at `http://localhost:9001`.

Configuration is supplied through `GALLERY_DB_URL`, `GALLERY_DB_USER`, `GALLERY_DB_PASSWORD`, `GALLERY_JWT_SECRET`, and the `GALLERY_STORAGE_*` variables. Defaults are development-only.

## API

- `POST /api/auth/register`, `POST /api/auth/login`
- `GET|POST /api/contests`
- `GET|POST /api/contests/{id}/submissions`
- `POST /api/submissions/{id}/votes`
- `GET /api/contests/{id}/leaderboard`

All endpoints except authentication and operational documentation require `Authorization: Bearer <token>`.

## Roadmap

V2's foundation is intentionally a modular monolith. Next increments add moderation/reporting, cursor feeds, Top-K and sliding-window trending services, image delivery through presigned URLs, richer contest administration, and full PostgreSQL integration/concurrency coverage.

The original servlet application is preserved in Git tag `legacy-servlet-v1`.
