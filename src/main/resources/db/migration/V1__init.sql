-- Enable UUID generation (PostgreSQL)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),

                       first_name VARCHAR(100) NOT NULL,
                       last_name  VARCHAR(100) NOT NULL,
                       country    VARCHAR(100) NOT NULL,

                       email VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,

                       role   VARCHAR(50) NOT NULL,
                       status VARCHAR(50) NOT NULL,

                       is_verified BOOLEAN NOT NULL DEFAULT FALSE,

                       created_at TIMESTAMP NOT NULL DEFAULT now(),
                       updated_at TIMESTAMP,
                       last_login TIMESTAMP
);

-- Optional but HIGHLY recommended indexes
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_users_status ON users(status);
