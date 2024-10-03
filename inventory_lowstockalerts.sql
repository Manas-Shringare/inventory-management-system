-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: inventory
-- ------------------------------------------------------
-- Server version	8.1.0

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
-- Table structure for table `lowstockalerts`
--

DROP TABLE IF EXISTS `lowstockalerts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lowstockalerts` (
  `alert_id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(255) DEFAULT NULL,
  `current_quantity` int DEFAULT NULL,
  `reorder_threshold` int DEFAULT NULL,
  `alert_timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`alert_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lowstockalerts`
--

LOCK TABLES `lowstockalerts` WRITE;
/*!40000 ALTER TABLE `lowstockalerts` DISABLE KEYS */;
INSERT INTO `lowstockalerts` VALUES (1,'Glucose',10,15,'2023-12-09 05:51:57'),(2,'Glucose',2,15,'2023-12-09 05:54:31'),(3,'Tortillas - Flour, 8',5,10,'2023-12-09 06:06:03'),(4,'Tuna - Yellowfin',8,9,'2023-12-09 06:06:40'),(5,'Tamarind Paste',9,10,'2023-12-09 06:07:13'),(6,'Mint - Fresh',2,8,'2023-12-09 06:07:36'),(7,'Beef - Baby, Liver',1,8,'2023-12-09 06:08:05'),(8,'Water - Tonic',12,20,'2023-12-09 06:09:32'),(9,'Ecolab - Solid Fusion',3,10,'2023-12-09 06:09:55'),(10,'Table Cloth 62x120 Colour',2,15,'2023-12-09 06:10:26'),(11,'Coriander - Seed',5,9,'2023-12-09 06:11:08'),(12,'Stock - Veal, Brown',1,4,'2023-12-09 06:11:38'),(13,'Hand Sanitizer',2,4,'2023-12-09 22:13:42');
/*!40000 ALTER TABLE `lowstockalerts` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-10 23:11:17
