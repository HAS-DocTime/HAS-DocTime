ALTER TABLE time_slot
ADD CONSTRAINT uq_time_slot UNIQUE (start_time, end_time, department_id);