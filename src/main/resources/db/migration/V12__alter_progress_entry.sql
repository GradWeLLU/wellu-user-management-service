ALTER TABLE progress_entries
DROP COLUMN metric;

ALTER TABLE progress_entries
DROP COLUMN value;


ALTER TABLE progress_entries
    ADD COLUMN weight DOUBLE PRECISION NOT NULL,
    ADD COLUMN calories_burnt DOUBLE PRECISION NOT NULL,
    ADD COLUMN workout_completed INTEGER NOT NULL,
    ADD COLUMN notes TEXT;



-- 5. Fix foreign key constraint
ALTER TABLE progress_entries
DROP CONSTRAINT IF EXISTS fk_progress_profile,
DROP CONSTRAINT IF EXISTS fk_progress_userprofile;

ALTER TABLE progress_entries
    ADD CONSTRAINT fk_progress_userprofile
        FOREIGN KEY (user_profile_id)
            REFERENCES profiles(id)
            ON DELETE CASCADE;

-- 6. Index for performance (recommended)
CREATE INDEX IF NOT EXISTS idx_progress_entries_user_profile
    ON progress_entries(user_profile_id);
