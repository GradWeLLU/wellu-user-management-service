CREATE TABLE meal_logs (
                           id UUID PRIMARY KEY,
                           user_id UUID NOT NULL,
                           meal_date DATE NOT NULL,
                           CONSTRAINT fk_meal_logs_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE meal_entries (
                              id UUID PRIMARY KEY,
                              meal_type VARCHAR(255) NOT NULL,
                              meal_name VARCHAR(255) NOT NULL,
                              calories INT,
                              protein INT,
                              carbs INT,
                              fats INT,
                              notes VARCHAR(1000),
                              meal_log_id UUID NOT NULL,
                              CONSTRAINT fk_meal_entries_log FOREIGN KEY (meal_log_id) REFERENCES meal_logs(id) ON DELETE CASCADE
);

CREATE TABLE meal_entry_food_items (
                                       meal_entry_id UUID NOT NULL,
                                       food_item VARCHAR(255) NOT NULL,
                                       CONSTRAINT fk_meal_entry_food_items_entry FOREIGN KEY (meal_entry_id) REFERENCES meal_entries(id) ON DELETE CASCADE
);

CREATE INDEX idx_meal_logs_user ON meal_logs(user_id);
CREATE INDEX idx_meal_logs_date ON meal_logs(meal_date);
CREATE INDEX idx_meal_entries_log_id ON meal_entries(meal_log_id);
CREATE INDEX idx_meal_entry_food_items_entry_id ON meal_entry_food_items(meal_entry_id);
