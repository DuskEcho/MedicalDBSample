-- MySQL Script generated by MySQL Workbench
-- Mon Sep 30 09:51:51 2019
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema MedicalDB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema MedicalDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `MedicalDB` DEFAULT CHARACTER SET utf8 ;
USE `MedicalDB` ;

-- -----------------------------------------------------
-- Table `MedicalDB`.`Patient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MedicalDB`.`Patient` (
  `PatientID` INT NOT NULL,
  `FirstName` VARCHAR(45) NOT NULL,
  `LastName` VARCHAR(45) NOT NULL,
  `Bday` VARCHAR(45) NOT NULL,
  `Address` VARCHAR(45) NOT NULL,
  `Phone` VARCHAR(45) NOT NULL,
  `EmergencyFirstName` VARCHAR(45) NOT NULL,
  `EmergencyLastName` VARCHAR(45) NOT NULL,
  `InsuranceProvider` VARCHAR(45) NOT NULL,
  `Weight` DECIMAL(2) NOT NULL,
  `Height` DECIMAL(2) NOT NULL,
  `BloodType` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`PatientID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MedicalDB`.`Condition`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MedicalDB`.`Condition` (
  `ConditionID` INT NOT NULL,
  `ConditionDescription` VARCHAR(45) NOT NULL,
  `CurrentlyActive` TINYINT NOT NULL,
  `PatientID` INT NOT NULL,
  `TreatmentPlan` VARCHAR(45) NULL,
  `DiagnosedVisitID` INT NULL,
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
-- Table `MedicalDB`.`Visit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MedicalDB`.`Visit` (
  `VisitID` INT NOT NULL,
  `VisitDescription` VARCHAR(45) NOT NULL,
  `VisitDate` DATE NOT NULL,
  `Completed` TINYINT NOT NULL,
  `PatientID` INT NOT NULL,
  `ConditionID` INT NULL,
  PRIMARY KEY (`VisitID`),
  INDEX `PatientID_idx` (`PatientID` ASC) VISIBLE,
  INDEX `FK_Condition_idx` (`ConditionID` ASC, `PatientID` ASC) VISIBLE,
  CONSTRAINT `PatientID`
    FOREIGN KEY (`PatientID`)
    REFERENCES `MedicalDB`.`Patient` (`PatientID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_Condition`
    FOREIGN KEY (`ConditionID` , `PatientID`)
    REFERENCES `MedicalDB`.`Condition` (`ConditionID` , `ConditionID`)
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
  `ConditionID` INT NULL,
  `Result` VARCHAR(45) NOT NULL,
  `PatientID` INT NOT NULL,
  PRIMARY KEY (`ProcedureID`),
  INDEX `PatientFK_idx` (`PatientID` ASC) VISIBLE,
  INDEX `ConditionFK_idx` (`ConditionID` ASC) VISIBLE,
  CONSTRAINT `PatientFK`
    FOREIGN KEY (`PatientID`)
    REFERENCES `MedicalDB`.`Patient` (`PatientID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `ConditionFK`
    FOREIGN KEY (`ConditionID`)
    REFERENCES `MedicalDB`.`Condition` (`ConditionID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;