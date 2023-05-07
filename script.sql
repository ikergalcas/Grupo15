-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: grupo15
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cambio_divisa`
--

DROP TABLE IF EXISTS `cambio_divisa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cambio_divisa` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cambio` double NOT NULL,
  `origen_id` int NOT NULL,
  `destino_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cambio_divisa_divisa1_idx` (`origen_id`),
  KEY `fk_cambio_divisa_divisa2_idx` (`destino_id`),
  CONSTRAINT `fk_cambio_divisa_divisa1` FOREIGN KEY (`origen_id`) REFERENCES `divisa` (`id`),
  CONSTRAINT `fk_cambio_divisa_divisa2` FOREIGN KEY (`destino_id`) REFERENCES `divisa` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cambio_divisa`
--

LOCK TABLES `cambio_divisa` WRITE;
/*!40000 ALTER TABLE `cambio_divisa` DISABLE KEYS */;
INSERT INTO `cambio_divisa` VALUES (1,0.91,1,2),(2,1.1,2,1),(3,1.13,1,3),(4,0.88,3,1),(5,1.25,2,3),(6,0.8,3,2);
/*!40000 ALTER TABLE `cambio_divisa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat`
--

DROP TABLE IF EXISTS `chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cerrado` tinyint NOT NULL,
  `cliente_id` int NOT NULL,
  `empleado_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_chat_cliente1_idx` (`cliente_id`),
  KEY `fk_chat_empleado1_idx` (`empleado_id`),
  CONSTRAINT `fk_chat_cliente1` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `fk_chat_empleado1` FOREIGN KEY (`empleado_id`) REFERENCES `empleado` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat`
--

LOCK TABLES `chat` WRITE;
/*!40000 ALTER TABLE `chat` DISABLE KEYS */;
INSERT INTO `chat` VALUES (1,0,1,1);
/*!40000 ALTER TABLE `chat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nif` varchar(45) NOT NULL,
  `primer_nombre` varchar(45) DEFAULT NULL,
  `segundo_nombre` varchar(45) DEFAULT NULL,
  `primer_apellido` varchar(45) DEFAULT NULL,
  `segundo_apellido` varchar(45) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `calle` varchar(45) DEFAULT NULL,
  `numero` varchar(45) DEFAULT NULL,
  `puerta` varchar(45) DEFAULT NULL,
  `ciudad` varchar(45) DEFAULT NULL,
  `pais` varchar(45) DEFAULT NULL,
  `region` varchar(45) DEFAULT NULL,
  `CP` varchar(45) DEFAULT NULL,
  `contrasena` varchar(45) NOT NULL,
  `rolcliente_id` int NOT NULL,
  `empresa_id` int DEFAULT NULL,
  `acceso` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `NIF_UNIQUE` (`nif`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_cliente_rolcliente1_idx` (`rolcliente_id`),
  KEY `fk_cliente_Empresa1_idx` (`empresa_id`),
  CONSTRAINT `fk_cliente_Empresa1` FOREIGN KEY (`empresa_id`) REFERENCES `empresa` (`id`),
  CONSTRAINT `fk_cliente_rolcliente1` FOREIGN KEY (`rolcliente_id`) REFERENCES `rol_cliente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'123456789','Alvaro',NULL,NULL,NULL,'2002-11-21',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'autorizado',2,1,1),(2,'987654321','Maria','','','','1995-08-17','','','','','','','','socio',3,1,1),(3,'112233445','Rociop','','','','2000-04-06','','','','','','','','individual',1,NULL,NULL),(4,'12121212','Pedro',NULL,NULL,NULL,'2003-06-06',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'pedro',3,2,1),(5,'79396010X','Álvaro','',NULL,'','2002-11-21','','','','','','','','juanito',3,3,1),(8,'1123','socioNuevo','','','','1999-04-21','','','','','',NULL,'','juanito',3,1,1);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuenta`
--

DROP TABLE IF EXISTS `cuenta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuenta` (
  `id` int NOT NULL AUTO_INCREMENT,
  `numero_cuenta` varchar(24) NOT NULL,
  `fecha_apertura` datetime NOT NULL,
  `fecha_cierre` datetime DEFAULT NULL,
  `estado_cuenta_id` int NOT NULL,
  `tipo_cuenta_id` int NOT NULL,
  `divisa_id` int NOT NULL,
  `dinero` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `numeroCuenta_UNIQUE` (`numero_cuenta`),
  UNIQUE KEY `idCuenta_UNIQUE` (`id`),
  KEY `fk_cuenta_estadoCuenta1_idx` (`estado_cuenta_id`),
  KEY `fk_cuenta_tipoCuenta1_idx` (`tipo_cuenta_id`),
  KEY `fk_cuenta_divisa1_idx` (`divisa_id`),
  CONSTRAINT `fk_cuenta_divisa1` FOREIGN KEY (`divisa_id`) REFERENCES `divisa` (`id`),
  CONSTRAINT `fk_cuenta_estadoCuenta1` FOREIGN KEY (`estado_cuenta_id`) REFERENCES `estado_cuenta` (`id`),
  CONSTRAINT `fk_cuenta_tipoCuenta1` FOREIGN KEY (`tipo_cuenta_id`) REFERENCES `tipo_cuenta` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuenta`
--

LOCK TABLES `cuenta` WRITE;
/*!40000 ALTER TABLE `cuenta` DISABLE KEYS */;
INSERT INTO `cuenta` VALUES (1,'1111','2022-12-24 00:00:00',NULL,1,2,2,272.1),(2,'2222','2022-12-25 00:00:00',NULL,2,2,2,362),(3,'3333','2023-05-05 00:00:00',NULL,1,2,1,360.25),(4,'4444','2023-02-14 00:00:00',NULL,1,1,2,537.622);
/*!40000 ALTER TABLE `cuenta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuenta_cliente`
--

DROP TABLE IF EXISTS `cuenta_cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuenta_cliente` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cliente_id` int NOT NULL,
  `cuenta_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cuenta_cliente_cliente1_idx` (`cliente_id`),
  KEY `fk_cuenta_cliente_cuenta1_idx` (`cuenta_id`),
  CONSTRAINT `fk_cuenta_cliente_cliente1` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `fk_cuenta_cliente_cuenta1` FOREIGN KEY (`cuenta_id`) REFERENCES `cuenta` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuenta_cliente`
--

LOCK TABLES `cuenta_cliente` WRITE;
/*!40000 ALTER TABLE `cuenta_cliente` DISABLE KEYS */;
INSERT INTO `cuenta_cliente` VALUES (1,1,1),(2,2,2),(3,3,3),(4,1,4),(5,2,4),(7,8,4);
/*!40000 ALTER TABLE `cuenta_cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuenta_sospechosa`
--

DROP TABLE IF EXISTS `cuenta_sospechosa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuenta_sospechosa` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cuenta_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cuenta_sospechosa_cuenta1_idx` (`cuenta_id`),
  CONSTRAINT `fk_cuenta_sospechosa_cuenta1` FOREIGN KEY (`cuenta_id`) REFERENCES `cuenta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuenta_sospechosa`
--

LOCK TABLES `cuenta_sospechosa` WRITE;
/*!40000 ALTER TABLE `cuenta_sospechosa` DISABLE KEYS */;
/*!40000 ALTER TABLE `cuenta_sospechosa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `divisa`
--

DROP TABLE IF EXISTS `divisa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `divisa` (
  `id` int NOT NULL AUTO_INCREMENT,
  `moneda` enum('euro','USD','libra') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `divisa`
--

LOCK TABLES `divisa` WRITE;
/*!40000 ALTER TABLE `divisa` DISABLE KEYS */;
INSERT INTO `divisa` VALUES (1,'euro'),(2,'USD'),(3,'libra');
/*!40000 ALTER TABLE `divisa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleado`
--

DROP TABLE IF EXISTS `empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empleado` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `rol_empleado_id` int NOT NULL,
  `numero_empleado` int NOT NULL,
  `contraseña` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_empleado_rolEmpleado1_idx` (`rol_empleado_id`),
  CONSTRAINT `fk_empleado_rolEmpleado1` FOREIGN KEY (`rol_empleado_id`) REFERENCES `rol_empleado` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleado`
--

LOCK TABLES `empleado` WRITE;
/*!40000 ALTER TABLE `empleado` DISABLE KEYS */;
INSERT INTO `empleado` VALUES (1,'Iker',2,123456789,'iker'),(2,'Serra',1,987654321,'serra');
/*!40000 ALTER TABLE `empleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empresa`
--

DROP TABLE IF EXISTS `empresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empresa` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `cif` varchar(45) NOT NULL,
  `contrasena` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idEmpresa_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empresa`
--

LOCK TABLES `empresa` WRITE;
/*!40000 ALTER TABLE `empresa` DISABLE KEYS */;
INSERT INTO `empresa` VALUES (1,'Apple','1234','apple'),(2,'Microsoft','4321','microsoft'),(3,'MediaMarkt','1122','samsung');
/*!40000 ALTER TABLE `empresa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estado_cuenta`
--

DROP TABLE IF EXISTS `estado_cuenta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estado_cuenta` (
  `id` int NOT NULL,
  `estado_cuenta` enum('activa','bloqueada','inactiva') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado_cuenta`
--

LOCK TABLES `estado_cuenta` WRITE;
/*!40000 ALTER TABLE `estado_cuenta` DISABLE KEYS */;
INSERT INTO `estado_cuenta` VALUES (1,'activa'),(2,'bloqueada'),(3,'inactiva');
/*!40000 ALTER TABLE `estado_cuenta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estado_solicitud`
--

DROP TABLE IF EXISTS `estado_solicitud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estado_solicitud` (
  `id` int NOT NULL AUTO_INCREMENT,
  `estado` enum('pendiente','denegada','aceptada') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado_solicitud`
--

LOCK TABLES `estado_solicitud` WRITE;
/*!40000 ALTER TABLE `estado_solicitud` DISABLE KEYS */;
INSERT INTO `estado_solicitud` VALUES (1,'pendiente'),(2,'denegada'),(3,'aceptada');
/*!40000 ALTER TABLE `estado_solicitud` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mensaje`
--

DROP TABLE IF EXISTS `mensaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mensaje` (
  `id` int NOT NULL AUTO_INCREMENT,
  `texto` varchar(500) NOT NULL,
  `fecha_envio` datetime DEFAULT NULL,
  `remitente` varchar(45) NOT NULL,
  `chat_id` int NOT NULL,
  `remitente_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_mensaje_chat1_idx` (`chat_id`),
  KEY `fk_mensaje_remitente1_idx` (`remitente_id`),
  CONSTRAINT `fk_mensaje_chat1` FOREIGN KEY (`chat_id`) REFERENCES `chat` (`id`),
  CONSTRAINT `fk_mensaje_remitente1` FOREIGN KEY (`remitente_id`) REFERENCES `remitente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mensaje`
--

LOCK TABLES `mensaje` WRITE;
/*!40000 ALTER TABLE `mensaje` DISABLE KEYS */;
INSERT INTO `mensaje` VALUES (1,'holi','2023-04-28 01:03:05','',1,2),(2,'q paza bro','2023-04-28 01:03:10','',1,2);
/*!40000 ALTER TABLE `mensaje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimiento`
--

DROP TABLE IF EXISTS `movimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movimiento` (
  `id` int NOT NULL AUTO_INCREMENT,
  `time_stamp` datetime DEFAULT NULL,
  `importe_origen` double NOT NULL,
  `importe_destino` double NOT NULL,
  `cuenta_origen_id` int NOT NULL,
  `tipo_movimiento_id` int NOT NULL,
  `cuenta_destino_id` int NOT NULL,
  `moneda_origen_id` int NOT NULL,
  `moneda_destino_id` int NOT NULL,
  `cliente_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_movimientos_cuenta1_idx` (`cuenta_origen_id`),
  KEY `fk_movimientos_tipoMovimiento1_idx` (`tipo_movimiento_id`),
  KEY `fk_movimientos_cuenta2_idx` (`cuenta_destino_id`),
  KEY `fk_movimiento_divisa1_idx` (`moneda_origen_id`),
  KEY `fk_movimiento_divisa2_idx` (`moneda_destino_id`),
  KEY `fk_movimiento_cliente1_idx` (`cliente_id`),
  CONSTRAINT `fk_movimiento_cliente1` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `fk_movimiento_divisa1` FOREIGN KEY (`moneda_origen_id`) REFERENCES `divisa` (`id`),
  CONSTRAINT `fk_movimiento_divisa2` FOREIGN KEY (`moneda_destino_id`) REFERENCES `divisa` (`id`),
  CONSTRAINT `fk_movimientos_cuenta1` FOREIGN KEY (`cuenta_origen_id`) REFERENCES `cuenta` (`id`),
  CONSTRAINT `fk_movimientos_cuenta2` FOREIGN KEY (`cuenta_destino_id`) REFERENCES `cuenta` (`id`),
  CONSTRAINT `fk_movimientos_tipoMovimiento1` FOREIGN KEY (`tipo_movimiento_id`) REFERENCES `tipo_movimiento` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimiento`
--

LOCK TABLES `movimiento` WRITE;
/*!40000 ALTER TABLE `movimiento` DISABLE KEYS */;
INSERT INTO `movimiento` VALUES (27,'2023-04-30 00:03:55',579.804,527.622,4,2,4,1,2,2),(28,'2023-04-30 00:31:42',10,10,1,1,4,2,2,1);
/*!40000 ALTER TABLE `movimiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `remitente`
--

DROP TABLE IF EXISTS `remitente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `remitente` (
  `id` int NOT NULL AUTO_INCREMENT,
  `remitente` enum('cliente','empleado') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `remitente`
--

LOCK TABLES `remitente` WRITE;
/*!40000 ALTER TABLE `remitente` DISABLE KEYS */;
INSERT INTO `remitente` VALUES (1,'cliente'),(2,'empleado');
/*!40000 ALTER TABLE `remitente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol_cliente`
--

DROP TABLE IF EXISTS `rol_cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol_cliente` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tipo` enum('individual','autorizado','socio') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol_cliente`
--

LOCK TABLES `rol_cliente` WRITE;
/*!40000 ALTER TABLE `rol_cliente` DISABLE KEYS */;
INSERT INTO `rol_cliente` VALUES (1,'individual'),(2,'autorizado'),(3,'socio');
/*!40000 ALTER TABLE `rol_cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol_empleado`
--

DROP TABLE IF EXISTS `rol_empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol_empleado` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tipo` enum('gestor','asistente') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol_empleado`
--

LOCK TABLES `rol_empleado` WRITE;
/*!40000 ALTER TABLE `rol_empleado` DISABLE KEYS */;
INSERT INTO `rol_empleado` VALUES (1,'gestor'),(2,'asistente');
/*!40000 ALTER TABLE `rol_empleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solicitud`
--

DROP TABLE IF EXISTS `solicitud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `solicitud` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cliente_id` int DEFAULT NULL,
  `empleado_id` int DEFAULT NULL,
  `tipo_solicitud_id` int NOT NULL,
  `estado_solicitud_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_solicitud_cliente1_idx` (`cliente_id`),
  KEY `fk_solicitud_empleado1_idx` (`empleado_id`),
  KEY `fk_solicitud_tipo_solicitud1_idx` (`tipo_solicitud_id`),
  KEY `fk_solicitud_estado_solicitud1_idx` (`estado_solicitud_id`),
  CONSTRAINT `fk_solicitud_cliente1` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `fk_solicitud_empleado1` FOREIGN KEY (`empleado_id`) REFERENCES `empleado` (`id`),
  CONSTRAINT `fk_solicitud_estado_solicitud1` FOREIGN KEY (`estado_solicitud_id`) REFERENCES `estado_solicitud` (`id`),
  CONSTRAINT `fk_solicitud_tipo_solicitud1` FOREIGN KEY (`tipo_solicitud_id`) REFERENCES `tipo_solicitud` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solicitud`
--

LOCK TABLES `solicitud` WRITE;
/*!40000 ALTER TABLE `solicitud` DISABLE KEYS */;
INSERT INTO `solicitud` VALUES (1,5,2,2,1),(4,2,2,5,1);
/*!40000 ALTER TABLE `solicitud` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_cuenta`
--

DROP TABLE IF EXISTS `tipo_cuenta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_cuenta` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tipo` enum('empresa','individual') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idtipoCuenta_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_cuenta`
--

LOCK TABLES `tipo_cuenta` WRITE;
/*!40000 ALTER TABLE `tipo_cuenta` DISABLE KEYS */;
INSERT INTO `tipo_cuenta` VALUES (1,'empresa'),(2,'individual');
/*!40000 ALTER TABLE `tipo_cuenta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_movimiento`
--

DROP TABLE IF EXISTS `tipo_movimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_movimiento` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tipo` enum('pago','cambioDivisa','sacarDinero') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_movimiento`
--

LOCK TABLES `tipo_movimiento` WRITE;
/*!40000 ALTER TABLE `tipo_movimiento` DISABLE KEYS */;
INSERT INTO `tipo_movimiento` VALUES (1,'pago'),(2,'cambioDivisa'),(3,'sacarDinero');
/*!40000 ALTER TABLE `tipo_movimiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_solicitud`
--

DROP TABLE IF EXISTS `tipo_solicitud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_solicitud` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tipo` enum('alta_cliente','alta_empresa','activa_empresa','desbloqueo_empresa','desbloqueo_individual','activa_individual') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_solicitud`
--

LOCK TABLES `tipo_solicitud` WRITE;
/*!40000 ALTER TABLE `tipo_solicitud` DISABLE KEYS */;
INSERT INTO `tipo_solicitud` VALUES (1,'alta_cliente'),(2,'alta_empresa'),(3,'activa_empresa'),(4,'desbloqueo_empresa'),(5,'desbloqueo_individual'),(6,'activa_individual');
/*!40000 ALTER TABLE `tipo_solicitud` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-30 14:22:41
