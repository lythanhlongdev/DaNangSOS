
CREATE TABLE tokens (
                        token_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        token VARCHAR(255),
                        refresh_token VARCHAR(255),
                        token_type VARCHAR(50),
                        expiration_date DATETIME,
                        refresh_expiration_date DATETIME,
                        is_mobile TINYINT(1),
                        revoked BOOLEAN,
                        expired BOOLEAN,
                        user_id BIGINT,
                        FOREIGN KEY (user_id) REFERENCES users(user_id)
);

ALTER TABLE users MODIFY COLUMN password VARCHAR(255) NOT NULL;