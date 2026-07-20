# Architecture decisions

## Modular monolith first

Business capabilities share one deployable process and one transactional database. This keeps voting correctness simple while package boundaries allow later extraction if measured load requires it. Kafka and Kubernetes are deliberately excluded until asynchronous workloads justify them.

## Voting correctness

The service checks contest timing and prevents self-votes. A database unique constraint on `(user_id, submission_id)` handles concurrent duplicate requests. The leaderboard aggregates with an indexed relational query; a future Top-K cache is an optimization, not the source of truth.

## Security

Passwords use Argon2, APIs use short-lived signed JWTs, DTOs are validated, credentials are environment-driven, and uploads are restricted by allow-listed MIME type plus decoded image content. Object names are UUIDs and user filenames are never used as storage paths.
