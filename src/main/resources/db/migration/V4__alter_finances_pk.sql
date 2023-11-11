ALTER TABLE finances
    ADD CONSTRAINT fk_user_email
        FOREIGN KEY (user_email)
            REFERENCES users(email);
