DROP TABLE IF EXISTS users;
DROP DATABASE IF EXISTS todo_app_db;

CREATE TABLE users(
    id SERIAL PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(255)
)