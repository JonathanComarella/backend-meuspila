CREATE TABLE users (
       id TEXT PRIMARY KEY UNIQUE NOT NULL,
       email TEXT NOT NULL UNIQUE,
       password TEXT NOT NULL,
       firstname TEXT NOT NULL,
       lastname TEXT NOT NULL,
       role TEXT NOT NULL
);
