
-- Insert sample users (username/password)
INSERT INTO users (username, password) VALUES
('alice', 'alicepass'),
('bob', 'bobpass');
-- Insert a sample image (uploaded by user_id 1, i.e., 'alice')
INSERT INTO images (file_path, user_id) VALUES ('uploads/sample1.jpg', 1);
-- User 2 ('bob') votes for image 1
INSERT INTO votes (user_id, image_id) VALUES (2, 1);