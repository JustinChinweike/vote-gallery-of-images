-- Users table: stores user accounts
CREATE TABLE users (
user_id SERIAL PRIMARY KEY,
username VARCHAR(50) UNIQUE NOT NULL,
password VARCHAR(50) NOT NULL
);
-- Images table: stores image file path and uploader reference
CREATE TABLE images (
image_id SERIAL PRIMARY KEY,
file_path VARCHAR(255) NOT NULL,
user_id INT NOT NULL REFERENCES users(user_id)
);
-- Votes table: stores each vote (one vote per user per image)
CREATE TABLE votes (
vote_id SERIAL PRIMARY KEY,
user_id INT NOT NULL REFERENCES users(user_id),
image_id INT NOT NULL REFERENCES images(image_id) ON DELETE CASCADE,
UNIQUE (user_id, image_id) -- prevent duplicate votes by the same user on
an image
);