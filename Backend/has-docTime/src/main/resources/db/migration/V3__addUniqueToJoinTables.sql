ALTER TABLE patient_symptom
ADD UNIQUE (patient_id, symptom_id);

ALTER TABLE department_symptom
ADD UNIQUE (department_id, symptom_id);

ALTER TABLE appointment_symptom
ADD UNIQUE (appointment_id, symptom_id);

ALTER TABLE patient_chronic_illness
ADD UNIQUE (patient_id, chronic_illness_id);
