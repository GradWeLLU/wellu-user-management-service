ALTER TABLE badges
    RENAME COLUMN title TO badge_name;

ALTER TABLE badges
    RENAME COLUMN description TO badge_description;

ALTER TABLE badges
    ADD COLUMN  badge_image_URL VARCHAR;
