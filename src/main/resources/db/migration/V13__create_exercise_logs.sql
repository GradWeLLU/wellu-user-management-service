-- V13__create_exercise_logs.sql
-- Create table for ExerciseLog
CREATE TABLE exercise_logs (
                               id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                               workout_day UUID NOT NULL,
                               user_id UUID NOT NULL
);

-- Create table for ExerciseLogEntry
CREATE TABLE exercise_log_entries (
                                      id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                      exercise_id UUID NOT NULL,
                                      sets INT NOT NULL,
                                      reps INT NOT NULL,
                                      weights DOUBLE PRECISION NOT NULL,
                                      exercise_log_id UUID NOT NULL,
                                      CONSTRAINT fk_exercise_log
                                          FOREIGN KEY(exercise_log_id)
                                              REFERENCES exercise_logs(id)
                                              ON DELETE CASCADE
);

-- Optional indexes for faster queries
CREATE INDEX idx_exercise_log_user ON exercise_logs(user_id);
CREATE INDEX idx_exercise_log_day ON exercise_logs(workout_day);
CREATE INDEX idx_exercise_log_entry_exercise ON exercise_log_entries(exercise_id);
CREATE INDEX idx_exercise_log_entry_log_id ON exercise_log_entries(exercise_log_id);
