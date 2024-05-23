-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema ssafytrip
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ssafytrip
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ssafytrip` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `ssafytrip` ;

-- -----------------------------------------------------
-- Table `ssafytrip`.`sido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`sido` (
  `sido_code` INT NOT NULL,
  `sido_name` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`sido_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ssafytrip`.`gugun`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`gugun` (
  `gugun_code` INT NOT NULL,
  `gugun_name` VARCHAR(30) NULL DEFAULT NULL,
  `sido_code` INT NOT NULL,
  PRIMARY KEY (`gugun_code`, `sido_code`),
  INDEX `gugun_to_sido_sido_code_fk_idx` (`sido_code` ASC) VISIBLE,
  CONSTRAINT `gugun_to_sido_sido_code_fk`
    FOREIGN KEY (`sido_code`)
    REFERENCES `ssafytrip`.`sido` (`sido_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ssafytrip`.`attraction_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`attraction_info` (
  `content_id` INT NOT NULL,
  `content_type_id` INT NULL DEFAULT NULL,
  `title` VARCHAR(100) NULL DEFAULT NULL,
  `addr1` VARCHAR(100) NULL DEFAULT NULL,
  `addr2` VARCHAR(50) NULL DEFAULT NULL,
  `zipcode` VARCHAR(50) NULL DEFAULT NULL,
  `tel` VARCHAR(50) NULL DEFAULT NULL,
  `first_image` VARCHAR(200) NULL DEFAULT NULL,
  `first_image2` VARCHAR(200) NULL DEFAULT NULL,
  `readcount` INT NULL DEFAULT NULL,
  `sido_code` INT NULL DEFAULT NULL,
  `gugun_code` INT NULL DEFAULT NULL,
  `latitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `longitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `mlevel` VARCHAR(2) NULL DEFAULT NULL,
  PRIMARY KEY (`content_id`),
  INDEX `attraction_to_content_type_id_fk_idx` (`content_type_id` ASC) VISIBLE,
  INDEX `attraction_to_sido_code_fk_idx` (`sido_code` ASC) VISIBLE,
  INDEX `attraction_to_gugun_code_fk_idx` (`gugun_code` ASC) VISIBLE,
  CONSTRAINT `attraction_to_content_type_id_fk`
    FOREIGN KEY (`content_type_id`)
    REFERENCES `ssafytrip`.`content_type` (`content_type_id`),
  CONSTRAINT `attraction_to_gugun_code_fk`
    FOREIGN KEY (`gugun_code`)
    REFERENCES `ssafytrip`.`gugun` (`gugun_code`),
  CONSTRAINT `attraction_to_sido_code_fk`
    FOREIGN KEY (`sido_code`)
    REFERENCES `ssafytrip`.`sido` (`sido_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ssafytrip`.`attraction_description`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`attraction_description` (
  `content_id` INT NOT NULL,
  `homepage` VARCHAR(100) NULL DEFAULT NULL,
  `overview` VARCHAR(10000) NULL DEFAULT NULL,
  `telname` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`content_id`),
  CONSTRAINT `attraction_detail_to_attraciton_id_fk`
    FOREIGN KEY (`content_id`)
    REFERENCES `ssafytrip`.`attraction_info` (`content_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ssafytrip`.`attraction_detail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`attraction_detail` (
  `content_id` INT NOT NULL,
  `cat1` VARCHAR(3) NULL DEFAULT NULL,
  `cat2` VARCHAR(5) NULL DEFAULT NULL,
  `cat3` VARCHAR(9) NULL DEFAULT NULL,
  `created_time` VARCHAR(14) NULL DEFAULT NULL,
  `modified_time` VARCHAR(14) NULL DEFAULT NULL,
  `booktour` VARCHAR(5) NULL DEFAULT NULL,
  PRIMARY KEY (`content_id`),
  CONSTRAINT `attraction_detail_to_basic_content_id_fk`
    FOREIGN KEY (`content_id`)
    REFERENCES `ssafytrip`.`attraction_info` (`content_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `ssafytrip`.`memberinfo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`memberinfo` (
  `member_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `id` VARCHAR(100) NULL DEFAULT NULL,
  `password` VARCHAR(100) NULL DEFAULT NULL,
  `email` VARCHAR(100) NULL DEFAULT NULL,
  `profile_img_src` VARCHAR(100) NULL DEFAULT NULL,
  `social_id` VARCHAR(255) NULL DEFAULT NULL,
  `role` VARCHAR(255) NULL DEFAULT NULL,
  `nickname` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`member_id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 28
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafytrip`.`hotplaceinfo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`hotplaceinfo` (
  `hotPlace_id` INT NOT NULL AUTO_INCREMENT,
  `member_id` INT NOT NULL,
  `hot_place_name` VARCHAR(500) NOT NULL,
  `address` VARCHAR(500) NOT NULL,
  `contents` VARCHAR(500) NULL DEFAULT NULL,
  `image_address` VARCHAR(500) NULL DEFAULT NULL,
  `lat` DOUBLE NOT NULL,
  `lng` DOUBLE NOT NULL,
  `views` INT NOT NULL,
  `likes` INT NOT NULL,
  `created_date` DATETIME NOT NULL,
  `visited_date` DATETIME NOT NULL,
  PRIMARY KEY (`hotPlace_id`),
  INDEX `fk_hotplaceinfo_memberinfo1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_hotplaceinfo_memberinfo1`
    FOREIGN KEY (`member_id`)
    REFERENCES `ssafytrip`.`memberinfo` (`member_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 23
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafytrip`.`likes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`likes` (
  `like_id` BIGINT NOT NULL AUTO_INCREMENT,
  `member_id` BIGINT NOT NULL,
  `hotPlace_id` BIGINT NOT NULL,
  PRIMARY KEY (`like_id`),
  INDEX `member_id` (`member_id` ASC) VISIBLE,
  INDEX `hotPlace_id` (`hotPlace_id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafytrip`.`plan`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`plan` (
  `plan_id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(50) NOT NULL,
  `member_id` INT NOT NULL,
  `start_date` DATETIME NULL DEFAULT NULL,
  `end_date` DATETIME NULL DEFAULT NULL,
  `share_count` INT NULL DEFAULT NULL,
  PRIMARY KEY (`plan_id`),
  INDEX `member_fk` (`member_id` ASC) VISIBLE,
  CONSTRAINT `member_fk`
    FOREIGN KEY (`member_id`)
    REFERENCES `ssafytrip`.`memberinfo` (`member_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 70
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafytrip`.`plan_attraction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`plan_attraction` (
  `plan_attraction_id` INT NOT NULL AUTO_INCREMENT,
  `seq` INT NOT NULL,
  `memo` VARCHAR(500) NULL DEFAULT NULL,
  `plan_id` INT NOT NULL,
  `content_id` INT NOT NULL,
  PRIMARY KEY (`plan_attraction_id`),
  INDEX `plan_fk` (`plan_id` ASC) VISIBLE,
  INDEX `attraction_fk` (`content_id` ASC) VISIBLE,
  CONSTRAINT `attraction_fk`
    FOREIGN KEY (`content_id`)
    REFERENCES `ssafytrip`.`attraction_info` (`content_id`),
  CONSTRAINT `plan_fk`
    FOREIGN KEY (`plan_id`)
    REFERENCES `ssafytrip`.`plan` (`plan_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 170
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `ssafytrip`.`tripinfoshare`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ssafytrip`.`tripinfoshare` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `member_id` INT NULL DEFAULT NULL,
  `title` VARCHAR(100) NULL DEFAULT NULL,
  `contents` VARCHAR(500) NULL DEFAULT NULL,
  `views` INT NULL DEFAULT NULL,
  `likes` INT NULL DEFAULT NULL,
  `created_date` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `member_id_fk` (`member_id` ASC) VISIBLE,
  CONSTRAINT `member_id_fk`
    FOREIGN KEY (`member_id`)
    REFERENCES `ssafytrip`.`memberinfo` (`member_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 13
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
