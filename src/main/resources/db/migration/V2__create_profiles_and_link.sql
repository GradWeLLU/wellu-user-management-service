-- V2__create_profiles_and_link.sql

-- Create the profiles table
CREATE TABLE profiles (
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          age INT,
                          weight DOUBLE PRECISION,
                          height DOUBLE PRECISION
    -- BMI can be computed in the app or later
);

-- Add profile_id column to users (owning side)
ALTER TABLE users
    ADD COLUMN profile_id UUID UNIQUE;

-- Create foreign key to enforce one-to-one
ALTER TABLE users
    ADD CONSTRAINT fk_user_profile
        FOREIGN KEY (profile_id) REFERENCES profiles(id);

-- Optional index for faster joins
CREATE INDEX idx_users_profile_id ON users(profile_id);
