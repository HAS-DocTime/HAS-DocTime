ALTER TABLE appointment
DROP FOREIGN KEY appointment_ibfk_3;

ALTER TABLE appointment
ADD FOREIGN KEY (booked_time_slot_id) REFERENCES time_slot(id);

ALTER TABLE post_appointment_data
DROP FOREIGN KEY post_appointment_data_ibfk_3;

ALTER TABLE post_appointment_data
ADD FOREIGN KEY (booked_time_slot_id) REFERENCES time_slot(id);
