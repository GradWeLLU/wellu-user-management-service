-- Remove old generic columns
ALTER TABLE preferences
DROP COLUMN key,
DROP COLUMN value;

-- Add correct scalar columns
ALTER TABLE preferences
    ADD COLUMN preferred_workout_duration INTEGER NOT NULL,
ADD COLUMN preferred_difficulty VARCHAR(50);

CREATE TABLE preference_time_periods (
                                         preference_id UUID NOT NULL,
                                         time_period VARCHAR(50) NOT NULL,

                                         CONSTRAINT fk_pref_time_periods
                                             FOREIGN KEY (preference_id)
                                                 REFERENCES preferences(id)
                                                 ON DELETE CASCADE
);

CREATE TABLE preference_equipment (
                                      preference_id UUID NOT NULL,
                                      equipment VARCHAR(100) NOT NULL,

                                      CONSTRAINT fk_pref_equipment
                                          FOREIGN KEY (preference_id)
                                              REFERENCES preferences(id)
                                              ON DELETE CASCADE
);

CREATE TABLE preference_dietary_types (
                                          preference_id UUID NOT NULL,
                                          dietary_type VARCHAR(50) NOT NULL,

                                          CONSTRAINT fk_pref_dietary_types
                                              FOREIGN KEY (preference_id)
                                                  REFERENCES preferences(id)
                                                  ON DELETE CASCADE
);

CREATE TABLE preference_disliked_foods (
                                           preference_id UUID NOT NULL,
                                           food VARCHAR(100) NOT NULL,

                                           CONSTRAINT fk_pref_disliked_foods
                                               FOREIGN KEY (preference_id)
                                                   REFERENCES preferences(id)
                                                   ON DELETE CASCADE
);

CREATE TABLE preference_cuisines (
                                     preference_id UUID NOT NULL,
                                     cuisine VARCHAR(100) NOT NULL,

                                     CONSTRAINT fk_pref_cuisines
                                         FOREIGN KEY (preference_id)
                                             REFERENCES preferences(id)
                                             ON DELETE CASCADE
);
