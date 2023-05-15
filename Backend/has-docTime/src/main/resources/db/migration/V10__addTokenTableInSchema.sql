CREATE TABLE token(
    id INT PRIMARY KEY AUTO_INCREMENT,
    token_string VARCHAR(300),
    is_expired BOOL DEFAULT FALSE,
    user_id INT NOT NULL,
    FOREIGN KEY(user_id) REFERENCES user(id)
);