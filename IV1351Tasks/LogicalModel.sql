CREATE TABLE conceptual_instrument (
 instrument_type VARCHAR(50) NOT NULL
);

ALTER TABLE conceptual_instrument ADD CONSTRAINT PK_conceptual_instrument PRIMARY KEY (instrument_type);


CREATE TABLE instrument (
 serial_instrument_id CHAR(5) NOT NULL,
 brand VARCHAR(50) NOT NULL,
 instrument_type VARCHAR(50),
 isAvailable BIT(1),
 price INT
);

ALTER TABLE instrument ADD CONSTRAINT PK_instrument PRIMARY KEY (serial_instrument_id);


CREATE TABLE person (
 person_nummer CHAR(12) NOT NULL,
 first_name VARCHAR(100) NOT NULL,
 last_name VARCHAR(100) NOT NULL,
 email VARCHAR(100) NOT NULL,
 phone_number VARCHAR(14),
 birth_year INT NOT NULL,
 city VARCHAR(100) NOT NULL,
 zipcode VARCHAR(100) NOT NULL,
 street VARCHAR(100) NOT NULL
);

ALTER TABLE person ADD CONSTRAINT PK_person PRIMARY KEY (person_nummer);


CREATE TABLE student (
 student_id CHAR(14) NOT NULL,
 sibling_student_id CHAR(14),
 contact_person_nummer CHAR(12),
 person_nummer CHAR(12) NOT NULL
);

ALTER TABLE student ADD CONSTRAINT PK_student PRIMARY KEY (student_id);


CREATE TABLE student_enrollment (
 application_id CHAR(16) NOT NULL,
 skill_level SMALLINT NOT NULL,
 instrument_name VARCHAR(60),
 ensemble_name VARCHAR(60),
 first_name VARCHAR(100) NOT NULL,
 last_name VARCHAR(100) NOT NULL,
 phone_number CHAR(14) NOT NULL,
 email CHAR(10) NOT NULL,
 student_id CHAR(14) NOT NULL
);

ALTER TABLE student_enrollment ADD CONSTRAINT PK_student_enrollment PRIMARY KEY (application_id);


CREATE TABLE student_payment (
 student_payment_id CHAR(22) NOT NULL,
 student_id CHAR(14) NOT NULL,
 discount INT
);

ALTER TABLE student_payment ADD CONSTRAINT PK_student_payment PRIMARY KEY (student_payment_id);


CREATE TABLE instructor (
 instructor_id CHAR(14) NOT NULL,
 can_teach_ensembles BIT(1) NOT NULL,
 person_nummer CHAR(12)
);

ALTER TABLE instructor ADD CONSTRAINT PK_instructor PRIMARY KEY (instructor_id);


CREATE TABLE instrument_rental (
 rental_id CHAR(28) NOT NULL,
 starting_date DATE NOT NULL,
 ending_date DATE NOT NULL,
 student_payment_id CHAR(22) NOT NULL,
 serial_instrument_id CHAR(5),
 active BIT(1)
);

ALTER TABLE instrument_rental ADD CONSTRAINT PK_instrument_rental PRIMARY KEY (rental_id);


CREATE TABLE lesson (
 lesson_id CHAR(20) NOT NULL,
 price INT NOT NULL,
 type VARCHAR(50) NOT NULL,
 instructor_id CHAR(14),
 datum DATE,
 tid TIME(10)
);

ALTER TABLE lesson ADD CONSTRAINT PK_lesson PRIMARY KEY (lesson_id);


CREATE TABLE lesson_enrollment (
 application_id CHAR(16),
 lesson_id CHAR(20),
 student_payment_id CHAR(22)
);


CREATE TABLE sibling_discount (
 student_id CHAR(14),
 student_payment_id CHAR(22),
 sibling_student_id CHAR(14)
);


CREATE TABLE taught_instrument (
 instructor_id CHAR(14),
 instrument_type VARCHAR(50)
);


CREATE TABLE ensemble_lesson (
 genre VARCHAR(50) NOT NULL,
 ensemble_id CHAR(12) NOT NULL,
 max_number_of_students INT NOT NULL,
 min_number_of_students INT NOT NULL,
 lesson_id CHAR(20),
 current_amount_of_students INT NOT NULL
);


CREATE TABLE group_lesson (
 skill_level VARCHAR(50) NOT NULL,
 instrument_type VARCHAR(50) NOT NULL,
 max_number_of_students INT NOT NULL,
 min_number_of_students INT NOT NULL,
 lesson_id CHAR(20),
 current_amount_of_students INT NOT NULL
);


CREATE TABLE individual_lesson (
 skill_level VARCHAR(50) NOT NULL,
 instrument VARCHAR(50) NOT NULL,
 lesson_id CHAR(20)
);


ALTER TABLE instrument ADD CONSTRAINT FK_instrument_0 FOREIGN KEY (instrument_type) REFERENCES conceptual_instrument (instrument_type);


ALTER TABLE student ADD CONSTRAINT FK_student_0 FOREIGN KEY (person_nummer) REFERENCES person (person_nummer);


ALTER TABLE student_enrollment ADD CONSTRAINT FK_student_enrollment_0 FOREIGN KEY (student_id) REFERENCES student (student_id);


ALTER TABLE student_payment ADD CONSTRAINT FK_student_payment_0 FOREIGN KEY (student_id) REFERENCES student (student_id);


ALTER TABLE instructor ADD CONSTRAINT FK_instructor_0 FOREIGN KEY (person_nummer) REFERENCES person (person_nummer);


ALTER TABLE instrument_rental ADD CONSTRAINT FK_instrument_rental_0 FOREIGN KEY (student_payment_id) REFERENCES student_payment (student_payment_id);
ALTER TABLE instrument_rental ADD CONSTRAINT FK_instrument_rental_1 FOREIGN KEY (serial_instrument_id) REFERENCES instrument (serial_instrument_id);


ALTER TABLE lesson ADD CONSTRAINT FK_lesson_0 FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id);


ALTER TABLE lesson_enrollment ADD CONSTRAINT FK_lesson_enrollment_0 FOREIGN KEY (application_id) REFERENCES student_enrollment (application_id);
ALTER TABLE lesson_enrollment ADD CONSTRAINT FK_lesson_enrollment_1 FOREIGN KEY (lesson_id) REFERENCES lesson (lesson_id);
ALTER TABLE lesson_enrollment ADD CONSTRAINT FK_lesson_enrollment_2 FOREIGN KEY (student_payment_id) REFERENCES student_payment (student_payment_id);


ALTER TABLE sibling_discount ADD CONSTRAINT FK_sibling_discount_0 FOREIGN KEY (student_id) REFERENCES student (student_id);
ALTER TABLE sibling_discount ADD CONSTRAINT FK_sibling_discount_1 FOREIGN KEY (student_payment_id) REFERENCES student_payment (student_payment_id);


ALTER TABLE taught_instrument ADD CONSTRAINT FK_taught_instrument_0 FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id);
ALTER TABLE taught_instrument ADD CONSTRAINT FK_taught_instrument_1 FOREIGN KEY (instrument_type) REFERENCES conceptual_instrument (instrument_type);


ALTER TABLE ensemble_lesson ADD CONSTRAINT FK_ensemble_lesson_0 FOREIGN KEY (lesson_id) REFERENCES lesson (lesson_id);


ALTER TABLE group_lesson ADD CONSTRAINT FK_group_lesson_0 FOREIGN KEY (lesson_id) REFERENCES lesson (lesson_id);


ALTER TABLE individual_lesson ADD CONSTRAINT FK_individual_lesson_0 FOREIGN KEY (lesson_id) REFERENCES lesson (lesson_id);


