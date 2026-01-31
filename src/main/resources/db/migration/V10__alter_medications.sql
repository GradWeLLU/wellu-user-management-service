
ALTER TABLE medications
ALTER COLUMN dosage TYPE DOUBLE PRECISION
USING dosage::DOUBLE PRECISION;

ALTER TABLE medications
ALTER COLUMN frequency TYPE INT
      USING frequency::integer;

ALTER TABLE medications
    ADD COLUMN start_date TIMESTAMP NOT NULL DEFAULT now(),
    ADD COLUMN end_date TIMESTAMP;
