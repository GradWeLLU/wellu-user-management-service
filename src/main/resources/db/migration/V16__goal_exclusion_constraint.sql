CREATE EXTENSION IF NOT EXISTS btree_gist;

ALTER TABLE goals
    ADD COLUMN date_range daterange
        GENERATED ALWAYS AS (
            daterange(start_date, end_date, '[]')
            ) STORED;

ALTER TABLE goals
    ADD CONSTRAINT goal_no_overlap
    EXCLUDE USING gist (
    user_profile_id WITH =,
    goal_type WITH =,
    date_range WITH &&
)
WHERE (is_completed = false);