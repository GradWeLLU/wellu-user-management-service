UPDATE goals
SET end_date = start_date
WHERE start_date > end_date;