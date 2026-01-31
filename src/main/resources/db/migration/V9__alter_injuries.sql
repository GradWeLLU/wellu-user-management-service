-- rename must be alone
ALTER TABLE injuries
    RENAME COLUMN severity TO severity_level;

-- add columns separately
ALTER TABLE injuries
    ADD COLUMN start_date TIMESTAMP NOT NULL DEFAULT now(),
    ADD COLUMN end_date TIMESTAMP,
    ADD COLUMN is_chronic BOOLEAN NOT NULL DEFAULT FALSE;