-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema grupo15
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema grupo15
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `grupo15` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `grupo15` ;

-- -----------------------------------------------------
-- Table `grupo15`.`rolcliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `grupo15`.`rolcliente` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tipo` ENUM('individual', 'autorizado', 'socio') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `grupo15`.`Empresa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `grupo15`.`Empresa` (
  `idEmpresa` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idEmpresa`),
  UNIQUE INDEX `idEmpresa_UNIQUE` (`idEmpresa` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `grupo15`.`cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `grupo15`.`cliente` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `NIF` VARCHAR(45) NOT NULL,
  `primerNombre` VARCHAR(45) NOT NULL,
  `segundoNombre` VARCHAR(45) NULL DEFAULT NULL,
  `primerApellido` VARCHAR(45) NOT NULL,
  `segundoApellido` VARCHAR(45) NULL DEFAULT NULL,
  `fechaNacimiento` DATETIME NOT NULL,
  `calle` VARCHAR(45) NOT NULL,
  `numero` VARCHAR(45) NOT NULL,
  `puerta` VARCHAR(45) NULL DEFAULT NULL,
  `ciudad` VARCHAR(45) NOT NULL,
  `pais` VARCHAR(45) NOT NULL,
  `region` VARCHAR(45) NULL DEFAULT NULL,
  `CP` VARCHAR(45) NOT NULL,
  `contrasena` VARCHAR(45) NOT NULL,
  `rolcliente_id` INT NOT NULL,
  `Empresa_idEmpresa` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `NIF_UNIQUE` (`NIF` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_cliente_rolcliente1_idx` (`rolcliente_id` ASC) VISIBLE,
  INDEX `fk_cliente_Empresa1_idx` (`Empresa_idEmpresa` ASC) VISIBLE,
  CONSTRAINT `fk_cliente_rolcliente1`
    FOREIGN KEY (`rolcliente_id`)
    REFERENCES `grupo15`.`rolcliente` (`id`),
  CONSTRAINT `fk_cliente_Empresa1`
    FOREIGN KEY (`Empresa_idEmpresa`)
    REFERENCES `grupo15`.`Empresa` (`idEmpresa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `grupo15`.`rolempleado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `grupo15`.`rolempleado` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tipo` ENUM('gestor', 'asistente') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `grupo15`.`empleado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `grupo15`.`empleado` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL DEFAULT NULL,
  `rolEmpleado_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_empleado_rolEmpleado1_idx` (`rolEmpleado_id` ASC) VISIBLE,
  CONSTRAINT `fk_empleado_rolEmpleado1`
    FOREIGN KEY (`rolEmpleado_id`)
    REFERENCES `grupo15`.`rolempleado` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `grupo15`.`chat`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `grupo15`.`chat` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `cerrado` INT NOT NULL,
  `cliente_id` INT NOT NULL,
  `empleado_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_chat_cliente1_idx` (`cliente_id` ASC) VISIBLE,
  INDEX `fk_chat_empleado1_idx` (`empleado_id` ASC) VISIBLE,
  CONSTRAINT `fk_chat_cliente1`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `grupo15`.`cliente` (`id`),
  CONSTRAINT `fk_chat_empleado1`
    FOREIGN KEY (`empleado_id`)
    REFERENCES `grupo15`.`empleado` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `grupo15`.`estadocuenta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `grupo15`.`estadocuenta` (
  `id` INT NOT NULL,
  `estadoCuenta` ENUM('activa', 'bloqueada', 'cerrada') NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `grupo15`.`tipoCuenta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `grupo15`.`tipoCuenta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tipo` ENUM('empresa', 'individual') NULL,
  `tipoCuentacol` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idtipoCuenta_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `grupo15`.`cuenta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `grupo15`.`cuenta` (
  `idCuenta` INT NOT NULL AUTO_INCREMENT,
  `numeroCuenta` VARCHAR(24) NOT NULL,
  `fechaApertura` DATETIME NOT NULL,
  `fechaCierre` DATETIME NULL DEFAULT NULL,
  `cliente_id` INT NOT NULL,
  `estadoCuenta_id` INT NOT NULL,
  `tipoCuenta_id` INT NOT NULL,
  PRIMARY KEY (`idCuenta`),
  UNIQUE INDEX `numeroCuenta_UNIQUE` (`numeroCuenta` ASC) VISIBLE,
  UNIQUE INDEX `idCuenta_UNIQUE` (`idCuenta` ASC) VISIBLE,
  INDEX `fk_cuenta_cliente_idx` (`cliente_id` ASC) VISIBLE,
  INDEX `fk_cuenta_estadoCuenta1_idx` (`estadoCuenta_id` ASC) VISIBLE,
  INDEX `fk_cuenta_tipoCuenta1_idx` (`tipoCuenta_id` ASC) VISIBLE,
  CONSTRAINT `fk_cuenta_cliente`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `grupo15`.`cliente` (`id`),
  CONSTRAINT `fk_cuenta_estadoCuenta1`
    FOREIGN KEY (`estadoCuenta_id`)
    REFERENCES `grupo15`.`estadocuenta` (`id`),
  CONSTRAINT `fk_cuenta_tipoCuenta1`
    FOREIGN KEY (`tipoCuenta_id`)
    REFERENCES `grupo15`.`tipoCuenta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `grupo15`.`mensaje`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `grupo15`.`mensaje` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `texto` VARCHAR(500) NOT NULL,
  `fechaEnvio` DATETIME NOT NULL,
  `remitente` VARCHAR(45) NOT NULL,
  `chat_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_mensaje_chat1_idx` (`chat_id` ASC) VISIBLE,
  CONSTRAINT `fk_mensaje_chat1`
    FOREIGN KEY (`chat_id`)
    REFERENCES `grupo15`.`chat` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `grupo15`.`tipomovimiento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `grupo15`.`tipomovimiento` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tipo` ENUM('pago', 'cambioDivisa', 'sacarDinero') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `grupo15`.`movimientos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `grupo15`.`movimientos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `timeStamp` VARCHAR(45) NOT NULL,
  `monedaOrigen` VARCHAR(45) NOT NULL,
  `importeOrigen` INT NOT NULL,
  `importeDestino` INT NOT NULL,
  `cuenta_idCuenta` INT NOT NULL,
  `tipoMovimiento_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_movimientos_cuenta1_idx` (`cuenta_idCuenta` ASC) VISIBLE,
  INDEX `fk_movimientos_tipoMovimiento1_idx` (`tipoMovimiento_id` ASC) VISIBLE,
  CONSTRAINT `fk_movimientos_cuenta1`
    FOREIGN KEY (`cuenta_idCuenta`)
    REFERENCES `grupo15`.`cuenta` (`idCuenta`),
  CONSTRAINT `fk_movimientos_tipoMovimiento1`
    FOREIGN KEY (`tipoMovimiento_id`)
    REFERENCES `grupo15`.`tipomovimiento` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `grupo15`.`solicitud`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `grupo15`.`solicitud` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tipo` VARCHAR(45) NOT NULL,
  `estado` VARCHAR(45) NOT NULL,
  `cliente_id` INT NOT NULL,
  `empleado_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_solicitud_cliente1_idx` (`cliente_id` ASC) VISIBLE,
  INDEX `fk_solicitud_empleado1_idx` (`empleado_id` ASC) VISIBLE,
  CONSTRAINT `fk_solicitud_cliente1`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `grupo15`.`cliente` (`id`),
  CONSTRAINT `fk_solicitud_empleado1`
    FOREIGN KEY (`empleado_id`)
    REFERENCES `grupo15`.`empleado` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
