ALTER TABLE doctor
DROP FOREIGN KEY doctor_ibfk_1,
DROP FOREIGN KEY doctor_ibfk_2;

ALTER TABLE doctor
ADD CONSTRAINT doctor_ibfk_1
FOREIGN KEY (user_id) REFERENCES user(id)
ON DELETE CASCADE,
ADD CONSTRAINT doctor_ibfk_2
FOREIGN KEY (department_id) REFERENCES department(id)
ON DELETE CASCADE;

ALTER TABLE admin
DROP FOREIGN KEY admin_ibfk_1;

ALTER TABLE admin
ADD CONSTRAINT admin_ibfk_1
FOREIGN KEY (user_id) REFERENCES user(id)
ON DELETE CASCADE;

ALTER TABLE appointment
DROP FOREIGN KEY appointment_ibfk_1,
DROP FOREIGN KEY appointment_ibfk_2,
DROP FOREIGN KEY appointment_ibfk_3;

ALTER TABLE appointment
ADD CONSTRAINT appointment_ibfk_1
FOREIGN KEY (patient_id) REFERENCES user(id)
ON DELETE CASCADE,
ADD CONSTRAINT appointment_ibfk_2
FOREIGN KEY (doctor_id) REFERENCES doctor(id)
ON DELETE CASCADE,
ADD CONSTRAINT appointment_ibfk_3
FOREIGN KEY (booked_time_slot_id) REFERENCES time_slot(id)
ON DELETE CASCADE;

ALTER TABLE appointment_symptom
DROP FOREIGN KEY appointment_symptom_ibfk_1,
DROP FOREIGN KEY appointment_symptom_ibfk_2;

ALTER TABLE appointment_symptom
ADD CONSTRAINT appointment_symptom_ibfk_1
FOREIGN KEY(appointment_id) REFERENCES appointment(id)
ON DELETE CASCADE,
ADD CONSTRAINT appointment_symptom_ibfk_2
FOREIGN KEY(symptom_id) REFERENCES symptom(id)
ON DELETE CASCADE;

ALTER TABLE available_time_slot
DROP FOREIGN KEY available_time_slot_ibfk_1,
DROP FOREIGN KEY available_time_slot_ibfk_2;

ALTER TABLE available_time_slot
ADD CONSTRAINT available_time_slot_ibfk_1
FOREIGN KEY(time_slot_id) REFERENCES time_slot(id)
ON DELETE CASCADE,
ADD CONSTRAINT available_time_slot_ibfk_2
FOREIGN KEY(doctor_id) REFERENCES doctor(id)
ON DELETE CASCADE;

ALTER TABLE booked_time_slot
DROP FOREIGN KEY booked_time_slot_ibfk_1,
DROP FOREIGN KEY booked_time_slot_ibfk_2;

ALTER TABLE booked_time_slot
ADD CONSTRAINT booked_time_slot_ibfk_1
FOREIGN KEY(time_slot_id) REFERENCES time_slot(id)
ON DELETE CASCADE,
ADD CONSTRAINT booked_time_slot_ibfk_2
FOREIGN KEY(doctor_id) REFERENCES doctor(id)
ON DELETE CASCADE;

ALTER TABLE department_symptom
DROP FOREIGN KEY department_symptom_ibfk_1,
DROP FOREIGN KEY department_symptom_ibfk_2;

ALTER TABLE department_symptom
ADD CONSTRAINT department_symptom_ibfk_1
FOREIGN KEY(department_id) REFERENCES department(id)
ON DELETE CASCADE,
ADD CONSTRAINT department_symptom_ibfk_2
FOREIGN KEY(symptom_id) REFERENCES symptom(id)
ON DELETE CASCADE;

ALTER TABLE patient_chronic_illness
DROP FOREIGN KEY patient_chronic_illness_ibfk_1,
DROP FOREIGN KEY patient_chronic_illness_ibfk_2;

ALTER TABLE patient_chronic_illness
ADD CONSTRAINT patient_chronic_illness_ibfk_1
FOREIGN KEY(patient_id) REFERENCES user(id)
ON DELETE CASCADE,
ADD CONSTRAINT patient_chronic_illness_ibfk_2
FOREIGN KEY(chronic_illness_id) REFERENCES chronic_illness(id)
ON DELETE CASCADE;

ALTER TABLE patient_symptom
DROP FOREIGN KEY patient_symptom_ibfk_1,
DROP FOREIGN KEY patient_symptom_ibfk_2;

ALTER TABLE patient_symptom
ADD CONSTRAINT patient_symptom_ibfk_1
FOREIGN KEY(patient_id) REFERENCES user(id)
ON DELETE CASCADE,
ADD CONSTRAINT patient_symptom_ibfk_2
FOREIGN KEY(symptom_id) REFERENCES symptom(id)
ON DELETE CASCADE;

ALTER TABLE post_appointment_data
DROP FOREIGN KEY post_appointment_data_ibfk_1,
DROP FOREIGN KEY post_appointment_data_ibfk_2,
DROP FOREIGN KEY post_appointment_data_ibfk_3;

ALTER TABLE post_appointment_data
ADD CONSTRAINT post_appointment_data_ibfk_1
FOREIGN KEY(patient_id) REFERENCES user(id)
ON DELETE CASCADE,
ADD CONSTRAINT post_appointment_data_ibfk_2
FOREIGN KEY(doctor_id) REFERENCES doctor(id)
ON DELETE CASCADE,
ADD CONSTRAINT post_appointment_data_ibfk_3
FOREIGN KEY(booked_time_slot_id) REFERENCES time_slot(id)
ON DELETE CASCADE;

ALTER TABLE time_slot
DROP CONSTRAINT time_slot_ibfk_1;

ALTER TABLE time_slot
ADD CONSTRAINT time_slot_ibfk_1
FOREIGN KEY(department_id) REFERENCES department(id)
ON DELETE CASCADE;

DELETE u1 FROM user u1 INNER JOIN user u2 ON u1.email = u2.email WHERE u1.id > u2.id;