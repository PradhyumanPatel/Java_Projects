CREATE DATABASE IF NOT EXISTS hotelmanagement;
USE hotelmanagement;

CREATE TABLE IF NOT EXISTS bookings (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  email VARCHAR(100) NOT NULL,
  phone VARCHAR(15) NOT NULL UNIQUE,
  aadhar VARCHAR(12) NOT NULL UNIQUE,
  age INT NOT NULL,
  lengthOfStay INT NOT NULL,
  members INT NOT NULL,
  checkInTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  checkOutTime TIMESTAMP NULL,
  CONSTRAINT chk_checkout_after_checkin CHECK (checkOutTime IS NULL OR checkOutTime >= checkInTime)
);
