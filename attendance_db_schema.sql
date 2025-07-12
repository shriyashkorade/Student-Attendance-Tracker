
-- Create database
CREATE DATABASE IF NOT EXISTS attendancemanagement;
USE attendancemanagement;

-- Admin table
CREATE TABLE admin (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Teachers table
CREATE TABLE teachers (
    teacher_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

-- Students table
CREATE TABLE students (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    roll_no VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    full_name VARCHAR(100) NOT NULL
);

-- Subjects table
CREATE TABLE subjects (
    subject_id INT AUTO_INCREMENT PRIMARY KEY,
    subject_name VARCHAR(100) NOT NULL,
    teacher_id INT,
    subject_type ENUM('core', 'elective') NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES teachers(teacher_id)
);

-- Student Electives table
CREATE TABLE student_electives (
    roll_no VARCHAR(20),
    subject_id INT,
    PRIMARY KEY (roll_no, subject_id),
    FOREIGN KEY (roll_no) REFERENCES students(roll_no),
    FOREIGN KEY (subject_id) REFERENCES subjects(subject_id)
);

-- Attendance table
CREATE TABLE attendance (
    attendance_id INT AUTO_INCREMENT PRIMARY KEY,
    roll_no VARCHAR(20),
    subject_id INT,
    lecture_no TINYINT,
    date DATE,
    status ENUM('present', 'absent') NOT NULL,
    FOREIGN KEY (roll_no) REFERENCES students(roll_no),
    FOREIGN KEY (subject_id) REFERENCES subjects(subject_id)
);
