CREATE TABLE IF NOT EXISTS authors (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
    bio VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS books (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     title VARCHAR(100) NOT NULL,
    description TEXT,
    isbn VARCHAR(13) UNIQUE,
    author_id BIGINT,
    FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE CASCADE
    );
