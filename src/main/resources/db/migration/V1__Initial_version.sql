CREATE TABLE IF NOT EXISTS accounts (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR ( 50 ) UNIQUE NOT NULL,
    password VARCHAR ( 50 ) NOT NULL,
    email VARCHAR ( 255 ) UNIQUE NOT NULL,
    created_on VARCHAR (80),
    last_login VARCHAR (80)
);

CREATE TABLE IF NOT EXISTS content (
    content_id SERIAL PRIMARY KEY,
    created_by serial,
    title VARCHAR (50) NOT NULL,
    content VARCHAR (255) NOT NULL
);

