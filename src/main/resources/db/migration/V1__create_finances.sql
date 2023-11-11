CREATE TABLE finances (
     id TEXT PRIMARY KEY UNIQUE NOT NULL,
     description TEXT NOT NULL,
     amount NUMERIC NOT NULL,
     typeFinances TEXT NOT NULL,
     repeat BOOLEAN,
     active BOOLEAN
);