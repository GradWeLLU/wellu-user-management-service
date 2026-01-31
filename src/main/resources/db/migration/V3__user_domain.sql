-- =====================================================
-- V3__user_domain_full.sql
-- Full User Domain (Health, Preferences, Goals, Progress)
-- =====================================================

-- ---------- 1. Extend profiles ----------
ALTER TABLE profiles
    ADD COLUMN bmi DOUBLE PRECISION;

-- ---------- 2. Health Profile (1–1) ----------
CREATE TABLE health_profiles (
                                 id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                 user_profile_id UUID NOT NULL UNIQUE,

                                 CONSTRAINT fk_healthprofile_profile
                                     FOREIGN KEY (user_profile_id)
                                         REFERENCES profiles(id)
                                         ON DELETE CASCADE
);

-- ---------- 3. Injuries ----------
CREATE TABLE injuries (
                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          name VARCHAR(100) NOT NULL,
                          description TEXT,
                          severity VARCHAR(50),

                          health_profile_id UUID NOT NULL,

                          CONSTRAINT fk_injury_healthprofile
                              FOREIGN KEY (health_profile_id)
                                  REFERENCES health_profiles(id)
                                  ON DELETE CASCADE
);

CREATE INDEX idx_injuries_health_profile
    ON injuries(health_profile_id);

-- ---------- 4. Allergies ----------
CREATE TABLE allergies (
                           id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                           name VARCHAR(100) NOT NULL,
                           reaction TEXT,

                           health_profile_id UUID NOT NULL,

                           CONSTRAINT fk_allergy_healthprofile
                               FOREIGN KEY (health_profile_id)
                                   REFERENCES health_profiles(id)
                                   ON DELETE CASCADE
);

CREATE INDEX idx_allergies_health_profile
    ON allergies(health_profile_id);

-- ---------- 5. Medications ----------
CREATE TABLE medications (
                             id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                             name VARCHAR(100) NOT NULL,
                             dosage VARCHAR(100),
                             frequency VARCHAR(100),

                             health_profile_id UUID NOT NULL,

                             CONSTRAINT fk_medication_healthprofile
                                 FOREIGN KEY (health_profile_id)
                                     REFERENCES health_profiles(id)
                                     ON DELETE CASCADE
);

CREATE INDEX idx_medications_health_profile
    ON medications(health_profile_id);

-- ---------- 6. Preferences ----------
CREATE TABLE preferences (
                             id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                             key VARCHAR(100) NOT NULL,
                             value VARCHAR(255),

                             user_profile_id UUID NOT NULL,

                             CONSTRAINT fk_preference_userprofile
                                 FOREIGN KEY (user_profile_id)
                                     REFERENCES profiles(id)
                                     ON DELETE CASCADE
);

CREATE INDEX idx_preferences_user_profile
    ON preferences(user_profile_id);

-- ---------- 7. Badges ----------
CREATE TABLE badges (
                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                        title VARCHAR(100) NOT NULL,
                        description TEXT,
                        earned_at TIMESTAMP NOT NULL DEFAULT now(),

                        user_profile_id UUID NOT NULL,

                        CONSTRAINT fk_badge_userprofile
                            FOREIGN KEY (user_profile_id)
                                REFERENCES profiles(id)
                                ON DELETE CASCADE
);

CREATE INDEX idx_badges_user_profile
    ON badges(user_profile_id);

-- ---------- 8. Goals ----------
CREATE TABLE goals (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       title VARCHAR(100) NOT NULL,
                       description TEXT,
                       target_value DOUBLE PRECISION,
                       current_value DOUBLE PRECISION,
                       status VARCHAR(50),

                       user_profile_id UUID NOT NULL,

                       CONSTRAINT fk_goal_userprofile
                           FOREIGN KEY (user_profile_id)
                               REFERENCES profiles(id)
                               ON DELETE CASCADE
);

CREATE INDEX idx_goals_user_profile
    ON goals(user_profile_id);

-- ---------- 9. Progress Entries ----------
CREATE TABLE progress_entries (
                                  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                  metric VARCHAR(100) NOT NULL,
                                  value DOUBLE PRECISION NOT NULL,
                                  recorded_at TIMESTAMP NOT NULL DEFAULT now(),

                                  user_profile_id UUID NOT NULL,

                                  CONSTRAINT fk_progress_userprofile
                                      FOREIGN KEY (user_profile_id)
                                          REFERENCES profiles(id)
                                          ON DELETE CASCADE
);

CREATE INDEX idx_progress_user_profile
    ON progress_entries(user_profile_id);
