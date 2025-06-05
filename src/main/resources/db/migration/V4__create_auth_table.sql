-- Create auth table
CREATE TABLE auth (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(100) NOT NULL,
    user_id BIGINT REFERENCES users(id)
);

-- Reset auth_id_seq
SELECT setval('auth_id_seq', 1, false); 