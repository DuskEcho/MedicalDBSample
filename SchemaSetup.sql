-- MySQL Script generated by MySQL Workbench
-- Mon Sep 30 11:24:15 2019
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering



SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema MedicalDB
-- -----------------------------------------------------

-- Set up user/pass for servlet use --

CREATE USER IF NOT EXISTS 'user'@'localhost' IDENTIFIED BY 'sesame80';
GRANT ALL ON medicaldb.* TO 'user'@'localhost';

-- -----------------------------------------------------
-- Schema MedicalDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `MedicalDB` DEFAULT CHARACTER SET utf8 ;
USE `MedicalDB` ;

-- -----------------------------------------------------
-- Table `MedicalDB`.`Patient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MedicalDB`.`Patient` (
  `PatientID` INT NOT NULL AUTO_INCREMENT,
  `FirstName` VARCHAR(45) NOT NULL,
  `LastName` VARCHAR(45) NOT NULL,
  `Bday` VARCHAR(45) NOT NULL,
  `Phone` VARCHAR(45) NOT NULL,
  `EmergencyFirstName` VARCHAR(45) NOT NULL,
  `EmergencyLastName` VARCHAR(45) NOT NULL,
  `Weight` DECIMAL(5,2) NOT NULL,
  `Height` INT NOT NULL,
  `BloodType` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`PatientID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MedicalDB`.`Physicians`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MedicalDB`.`Physicians` (
  `PhysicianID` INT NOT NULL AUTO_INCREMENT,
  `PhysicianName` VARCHAR(45) NOT NULL,
  `ContactNumber` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`PhysicianID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MedicalDB`.`Visit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MedicalDB`.`Visit` (
  `VisitID` INT NOT NULL AUTO_INCREMENT,
  `VisitDescription` VARCHAR(45) NOT NULL,
  `VisitDate` DATE NOT NULL,
  `Completed` TINYINT NOT NULL,
  `PatientID` INT NOT NULL,
  `PhysicianID` INT NOT NULL,
  PRIMARY KEY (`VisitID`),
  INDEX `PatientID_idx` (`PatientID` ASC) VISIBLE,
  INDEX `PhysicianFK_idx` (`PhysicianID` ASC) VISIBLE,
  CONSTRAINT `PatientID`
    FOREIGN KEY (`PatientID`)
    REFERENCES `MedicalDB`.`Patient` (`PatientID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `VisitPhysicianFK`
    FOREIGN KEY (`PhysicianID`)
    REFERENCES `MedicalDB`.`Physicians` (`PhysicianID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MedicalDB`.`Conditions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MedicalDB`.`Conditions` (
  `ConditionID` INT NOT NULL AUTO_INCREMENT,
  `ConditionDescription` VARCHAR(45) NOT NULL,
  `CurrentlyActive` TINYINT NOT NULL,
  `PatientID` INT NOT NULL,
  `TreatmentPlan` VARCHAR(45) NULL DEFAULT NULL,
  `DiagnosedVisitID` INT NOT NULL,
  PRIMARY KEY (`ConditionID`),
  INDEX `PatientID_idx` (`PatientID` ASC) INVISIBLE,
  INDEX `FK_DiagnosedVisitID_idx` (`DiagnosedVisitID` ASC) VISIBLE,
  CONSTRAINT `FK_PatientID`
    FOREIGN KEY (`PatientID`)
    REFERENCES `MedicalDB`.`Patient` (`PatientID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_DiagnosedVisitID`
    FOREIGN KEY (`DiagnosedVisitID`)
    REFERENCES `MedicalDB`.`Visit` (`VisitID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MedicalDB`.`Procedures`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MedicalDB`.`Procedures` (
  `ProcedureID` INT NOT NULL AUTO_INCREMENT,
  `ProcedureDescription` VARCHAR(45) NOT NULL,
  `ProcedureDate` DATE NOT NULL,
  `ConditionID` INT NOT NULL,
  `Result` VARCHAR(45) NOT NULL,
  `PatientID` INT NOT NULL,
  `PhysicianID` INT NOT NULL,
  PRIMARY KEY (`ProcedureID`),
  INDEX `PatientFK_idx` (`PatientID` ASC) VISIBLE,
  INDEX `ConditionFK_idx` (`ConditionID` ASC) VISIBLE,
  INDEX `PhysicianFK_idx` (`PhysicianID` ASC) VISIBLE,
  CONSTRAINT `PatientFK`
    FOREIGN KEY (`PatientID`)
    REFERENCES `MedicalDB`.`Patient` (`PatientID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `ConditionFK`
    FOREIGN KEY (`ConditionID`)
    REFERENCES `MedicalDB`.`Conditions` (`ConditionID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `PhysicianFK`
    FOREIGN KEY (`PhysicianID`)
    REFERENCES `MedicalDB`.`Physicians` (`PhysicianID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO physicians (PhysicianName, ContactNumber, PhysicianID)
VALUES ("Stephen Strange", "555-555-5551", 1),
        ("Mario", "555-555-5552", 2),
	("Meredith Grey", "555-555-5553", 3);

INSERT INTO patient
VALUES (1, "Frank", "Redbeard", 1950-01-01, "111-111-1111", "", "", 185.0, 59, "O"),
 (2, "Jack", "Doe", 1935-06-15, "222-222-2222", "Matka", "Pluku", 149.0, 58, "AB-"),
 (3, "Matka", "Pluku", 1936-05-15, "333-333-3333", "", "", 185.0, 59, "O-"),
 (4, "Karina", "Nose", 1984-02-21, "444-444-4444", "Hiro", "Nose", 105.6, 51, "A+"),
 (5, "Hasan", "Minhaj", 1985-09-23, "555-555-5555", "Beena", "Patel", 162.4, 60, "B+"),
 (6, "Bruce", "Wayne", 1955-02-19, "666-666-6666", "Selina", "Kyle", 210.0, 61, "AB+"),
 (7, "Pamela", "Isley", 1966-06-01, "777-777-7777", "Cary", "Bates", 185.0, 57, "B-"),
 (8, "Greta", "Thunberg", 2003-01-03, "888-888-8888", "Olaf", "Thunberg", 120.0, 55, "O+"),
 (9, "Philip", "Fry", 1974-08-14, "999-999-9999", "Turanga", "Leela", 165.0, 57, "A+"),
 (10, "John", "Bercow", 1963-01-19, "111-222-3333", "Sally", "Bercow", 160.0, 55, "O-");

INSERT INTO visit (VisitDescription, VisitDate, Completed, PatientID, PhysicianID)
VALUES ("Leg complaint, eye complaint", "2010-09-01", false, 1, 2),
	("headache or migraine", "2010-08-26", true, 2, 3),
	("chest pain", "2010-08-26", true, 2, 1),
    ("soreness in arm", "2010-08-29", false, 3, 3),
    ("pain when walking", "2010-09-02", true, 4, 3),
    ("difficulty sleeping", "2010-09-01", true, 5, 3),
    ("Difficulty breathing, difficulty walking", "2010-07-16", true, 6, 1),
    ("Follow up to visit", "2010-07-23", true, 6, 1),
    ("Discoloration in veins", "2010-08-29", false, 7, 3),
    ("'fingers smell like chicken'", "2010-09-04", true, 9, 2);

INSERT INTO conditions (ConditionDescription, CurrentlyActive, PatientID, TreatmentPlan, DiagnosedVisitID)
VALUES ("Leg infection", true, 1, "Removal, replace with peg", 1),
	("Headache from dehydration", false, 2, "resolved prior to visit", 2),
	("Bruised ribs", false, 2, "rest", 2),
    ("Broken arm", true, 3, "requires arm cast for recovery", 4),
    ("Ankle sprain", false, 4, "RICE", 5),
    ("Stress", false, 5, "vacation", 6),
    ("Fractured ribs", false, 6, "Referred to hospital for immediate care", 7),
    ("Possible spine abnormality", false, 6, "Referred to hospital for immediate care", 7),
    ("Broken leg", false, 6, "Referred to hospital for immediate care", 7),
    ("Unknown pathogen", true, 7, "Referred to lab for analysis", 9),
    ("Patient is an imbecile", false, 9, "Don't sniff fingers after eating chicken", 10);
    
    insert into procedures (ProcedureDescription, ProcedureDate, ConditionID, Result, PatientID, PhysicianID)
VALUES 	("antibiotics", "2010-09-01", 1, "patient to seek further care", 1, 2),
    ("wound clean/wrap", "2010-09-01", 1, "patient to seek further care", 1, 2),
    ("tylenol", "2010-09-01", 2, "patient to seek further care", 1, 2),
    ("Ice Wrap", "2010-08-26", 3, "patient returned home", 2, 1),
	("tylenol", "2010-08-26", 3, "patient returned home", 2, 3),
    ("tylenol", "2010-08-29", 4, "patient to seek further care", 3, 1),
	("Ice Wrap", "2010-09-02", 5, "patient returned home", 4, 3),
	("tylenol", "2010-09-02", 5, "patient returned home", 4, 3),
	("tylenol", "2010-07-16", 7, "patient referred to ER", 6, 1),
	("splint", "2010-07-16", 8, "patient referred to ER", 6, 1),
	("tylenol", "2010-07-16", 9, "patient referred to ER", 6, 1),
	("splint", "2010-07-16", 8, "patient referred to ER", 6, 1),
	("blood draw", "2010-08-29", 9, "patient referred to lab", 7, 3);

    
