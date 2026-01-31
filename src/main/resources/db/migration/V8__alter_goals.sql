ALTER TABLE goals
    RENAME COLUMN title TO goal_type;

alter table goals
DROP column description;

alter table goals
DROP column status;

alter table goals
add column start_date TIMESTAMP NOT NULL DEFAULT now();

alter table goals
    add column end_date TIMESTAMP NOT NULL DEFAULT now();

alter table goals
    add column is_completed BOOLEAN DEFAULT FALSE NOT NULL;