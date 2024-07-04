CREATE TABLE IF NOT EXISTS authors (
                                       id BIGSERIAL PRIMARY KEY,
                                       name VARCHAR(100) NOT NULL,
    bio TEXT
    );

CREATE TABLE IF NOT EXISTS books(
                                    id BIGSERIAL PRIMARY KEY,
                                    title VARCHAR(100) NOT NULL,
    description TEXT,
    isbn unique VARCHAR(13),
    author_id BIGINT REFERENCES authors(id) ON DELETE CASCADE
    );
