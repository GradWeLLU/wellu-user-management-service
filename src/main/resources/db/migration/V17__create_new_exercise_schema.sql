DROP TABLE exercise_log_entries;
DROP TABLE exercise_logs CASCADE ;

CREATE TABLE exercise_logs (
                               id UUID PRIMARY KEY,
                               user_id UUID NOT NULL,
                               workout_date DATE NOT NULL,
                               CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE exercise_entries (
                                  id UUID PRIMARY KEY,
                                  exercise_name VARCHAR(255) NOT NULL,
                                  type VARCHAR(50) NOT NULL,
                                  duration_minutes INT,
                                  distance_km DOUBLE PRECISION,
                                  burned_calories INT,
                                  exercise_log_id UUID NOT NULL,
                                  CONSTRAINT fk_log FOREIGN KEY (exercise_log_id) REFERENCES exercise_logs(id)
);

CREATE TABLE exercise_sets (
                               id UUID PRIMARY KEY,
                               reps INT,
                               weight DOUBLE PRECISION,
                               exercise_entry_id UUID NOT NULL,
                               CONSTRAINT fk_entry FOREIGN KEY (exercise_entry_id) REFERENCES exercise_entries(id)
);