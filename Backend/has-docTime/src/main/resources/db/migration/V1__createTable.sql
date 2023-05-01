CREATE TABLE user(
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    dob DATE NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(20) NOT NULL,
    blood_group VARCHAR(4) NOT NULL,
    contact VARCHAR(13) NOT NULL,
    height FLOAT,
    weight FLOAT,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(68) NOT NULL,
    role VARCHAR(10) NOT NULL
);

CREATE TABLE department(
	id INT PRIMARY KEY AUTO_INCREMENT,
    	name VARCHAR(50) NOT NULL,
    	building VARCHAR(100) NOT NULL,
    	time_duration INT NOT NULL,
    	UNIQUE (name)
	);

CREATE TABLE doctor(
	id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    qualification VARCHAR(50) NOT NULL,
    department_id INT NOT NULL,
    cases_solved INT DEFAULT 0,
    is_available BOOL DEFAULT TRUE,
    FOREIGN KEY(user_id) REFERENCES user(id),
    FOREIGN KEY(department_id) REFERENCES department(id)
);

CREATE TABLE admin(
	id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    FOREIGN KEY(user_id) REFERENCES user(id)
);

CREATE TABLE symptom(
	id INT PRIMARY KEY AUTO_INCREMENT,
   	name VARCHAR(100) NOT NULL,
    	UNIQUE (name)
    );

CREATE TABLE chronic_illness(
	id INT PRIMARY KEY AUTO_INCREMENT,
    	name VARCHAR(100) NOT NULL,
    	UNIQUE (name)
    );

CREATE TABLE patient_symptom(
	patient_id INT,
    	symptom_id INT,
    	FOREIGN KEY (patient_id) REFERENCES user(id),
    	FOREIGN KEY (symptom_id) REFERENCES symptom(id)
    );

CREATE TABLE department_symptom(
	department_id INT,
    	symptom_id INT,
    	FOREIGN KEY (department_id) REFERENCES department(id),
    	FOREIGN KEY (symptom_id) REFERENCES symptom(id)
    );

CREATE TABLE time_slot(
	id INT PRIMARY KEY AUTO_INCREMENT,
	start_time TIME,
	end_time TIME,
	department_id INT,
	FOREIGN KEY (department_id) REFERENCES department(id)
);

CREATE TABLE available_time_slot(
	time_slot_id INT,
	doctor_id INT,
	FOREIGN KEY (time_slot_id) REFERENCES time_slot(id),
	FOREIGN KEY (doctor_id) REFERENCES doctor(id)
);

CREATE TABLE booked_time_slot(
	time_slot_id INT,
	doctor_id INT,
	FOREIGN KEY (time_slot_id) REFERENCES time_slot(id),
	FOREIGN KEY (doctor_id) REFERENCES doctor(id)
);

CREATE TABLE appointment(
	id INT PRIMARY KEY AUTO_INCREMENT,
	patient_id INT NOT NULL,
	doctor_id INT NOT NULL,
	booked_time_slot_id INT NOT NULL,
	description VARCHAR(1000),
	FOREIGN KEY(patient_id) REFERENCES user(id),
	FOREIGN KEY(doctor_id) REFERENCES doctor(id),
	FOREIGN KEY(booked_time_slot_id) REFERENCES booked_time_slot(time_slot_id)
);

CREATE TABLE post_appointment_data(
	id INT PRIMARY KEY AUTO_INCREMENT,
	patient_id INT NOT NULL,
	doctor_id INT NOT NULL,
	booked_time_slot_id INT NOT NULL,
 	disease VARCHAR(100),
	medicine VARCHAR(1000),
	FOREIGN KEY(patient_id) REFERENCES user(id),
	FOREIGN KEY(doctor_id) REFERENCES doctor(id),
	FOREIGN KEY(booked_time_slot_id) REFERENCES booked_time_slot(time_slot_id)
);

CREATE TABLE appointment_symptom(
	appointment_id INT,
    	symptom_id INT,
    	FOREIGN KEY (appointment_id) REFERENCES appointment(id),
    	FOREIGN KEY (symptom_id) REFERENCES symptom(id)
);

CREATE TABLE patient_chronic_illness(
	patient_id INT,
    	chronic_illness_id INT,
    	years_of_illness FLOAT NOT NULL,
    	FOREIGN KEY (patient_id) REFERENCES user(id),
    	FOREIGN KEY (chronic_illness_id) REFERENCES chronic_illness(id)
 );