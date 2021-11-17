DROP TABLE IF EXISTS university;

DROP TABLE IF EXISTS student;

CREATE TABLE university
(
    university_id INT NOT NULL AUTO_INCREMENT,
    university_name VARCHAR(20) NOT NULL UNIQUE,
    CONSTRAINT university_pk PRIMARY KEY (university_id)
);

CREATE TABLE student
(
    student_id INT NOT NULL AUTO_INCREMENT,
    firstname varchar(255) NOT NULL,
    lastname varchar(255) NOT NULL,
    email varchar(255) NOT NULL UNIQUE,
    course int NOT NULL,
    university_id int NOT NULL,
    CONSTRAINT student_pk PRIMARY KEY (student_id),
    CONSTRAINT student_university_fk FOREIGN KEY (university_id) REFERENCES university(university_id)
);