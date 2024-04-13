-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: rrrs
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `cancel_histories`
--

INSERT INTO cancel_histories VALUES (1,'2024-03-13 11:34:12','Oke tao thoat roi','USER',2);
INSERT INTO cancel_histories VALUES (2,'2024-03-17 05:34:36','Oke tao thoat roi','USER',3);
INSERT INTO cancel_histories VALUES (3,'2024-03-17 09:14:46','Oke tao thoat roi','USER',16);

--
-- Dumping data for table `families`
--

INSERT INTO families VALUES (1,_binary '\0');
INSERT INTO families VALUES (2,_binary '\0');
INSERT INTO families VALUES (3,_binary '\0');
INSERT INTO families VALUES (4,_binary '\0');
INSERT INTO families VALUES (5,_binary '\0');
INSERT INTO families VALUES (6,_binary '\0');
INSERT INTO families VALUES (7,_binary '\0');
INSERT INTO families VALUES (8,_binary '\0');
INSERT INTO families VALUES (9,_binary '\0');
INSERT INTO families VALUES (10,_binary '\0');
INSERT INTO families VALUES (11,_binary '\0');
INSERT INTO families VALUES (12,_binary '\0');

--
-- Dumping data for table `group_role`
--

-- INSERT INTO group_role VALUES (1,2,1);
-- INSERT INTO group_role VALUES (2,0,1);
-- INSERT INTO group_role VALUES (3,2,2);
-- INSERT INTO group_role VALUES (4,1,2);
-- INSERT INTO group_role VALUES (5,2,3);
-- INSERT INTO group_role VALUES (6,2,4);
-- INSERT INTO group_role VALUES (7,1,4);
-- INSERT INTO group_role VALUES (8,1,5);
-- INSERT INTO group_role VALUES (9,2,5);
-- INSERT INTO group_role VALUES (10,1,6);
-- INSERT INTO group_role VALUES (11,2,6);
-- INSERT INTO group_role VALUES (12,1,7);
-- INSERT INTO group_role VALUES (13,2,7);
-- INSERT INTO group_role VALUES (14,1,8);
-- INSERT INTO group_role VALUES (15,2,8);
-- INSERT INTO group_role VALUES (16,2,9);

INSERT INTO `group_role` VALUES (1,1,1);
INSERT INTO `group_role` VALUES (2,3,1);
INSERT INTO `group_role` VALUES (3,3,2);
INSERT INTO `group_role` VALUES (5,3,3);
INSERT INTO `group_role` VALUES (7,3,4);
INSERT INTO `group_role` VALUES (8,3,5);
INSERT INTO `group_role` VALUES (11,3,6);
INSERT INTO `group_role` VALUES (13,3,7);
INSERT INTO `group_role` VALUES (15,3,8);
INSERT INTO `group_role` VALUES (16,3,9);
INSERT INTO `group_role` VALUES (17,2,10);
INSERT INTO `group_role` VALUES (18,1,10);
INSERT INTO `group_role` VALUES (19,2,11);
INSERT INTO `group_role` VALUES (20,3,11);
INSERT INTO `group_role` VALUES (21,2,2);
INSERT INTO `group_role` VALUES (22,2,4);
INSERT INTO `group_role` VALUES (23,2,5);
INSERT INTO `group_role` VALUES (24,2,6);
INSERT INTO `group_role` VALUES (25,2,7);
INSERT INTO `group_role` VALUES (26,2,8);
INSERT INTO `group_role` VALUES (27,2,9);
INSERT INTO `group_role` VALUES (28,2,10);
INSERT INTO `group_role` VALUES (29,1,11);
INSERT INTO `group_role` VALUES (30,1,1);

--
-- Dumping data for table `histories`
--

INSERT INTO histories VALUES (1,'2024-03-13 11:00:45',_binary '\0',16.234234234,108.2222,'Hello','COMPLETED','2024-03-13 11:00:45',1,1);
INSERT INTO histories VALUES (2,'2024-03-13 11:13:15',_binary '\0',16.234234234,108.123145123,'Hello','CANCELLED_USER','2024-03-13 11:13:15',1,1);
INSERT INTO histories VALUES (3,'2024-03-13 11:58:57',_binary '\0',16.234234234,108.2222,'Hello','CANCELLED_USER','2024-03-13 11:58:57',1,1);
INSERT INTO histories VALUES (4,'2024-03-13 12:26:30',_binary '\0',16.234234234,108.123145123,'Hello','COMPLETED','2024-03-13 12:26:30',1,1);
INSERT INTO histories VALUES (5,'2024-03-17 03:14:18',_binary '\0',16.234234234,108.123145123,'Hello','SYSTEM_RECEIVED','2024-03-17 03:14:18',1,1);
INSERT INTO histories VALUES (6,'2024-03-17 05:22:59',_binary '\0',16.234234234,108.123145123,NULL,'SYSTEM_RECEIVED','2024-03-17 05:22:59',1,1);
INSERT INTO histories VALUES (7,'2024-03-17 06:51:43',_binary '\0',16.051907295167997,108.21672101356653,NULL,'COMPLETED','2024-03-17 06:51:43',5,1);
INSERT INTO histories VALUES (8,'2024-03-17 06:51:52',_binary '\0',16.051907295167997,108.21672101356653,NULL,'COMPLETED','2024-03-17 06:51:52',5,1);
INSERT INTO histories VALUES (9,'2024-03-17 06:51:53',_binary '\0',16.051907295167997,108.21672101356653,NULL,'CONFIRMED','2024-03-17 06:51:53',5,1);
INSERT INTO histories VALUES (10,'2024-03-17 06:51:54',_binary '\0',16.051907295167997,108.21672101356653,NULL,'SYSTEM_RECEIVED','2024-03-17 06:51:54',5,1);
INSERT INTO histories VALUES (11,'2024-03-17 06:51:55',_binary '\0',16.051907295167997,108.21672101356653,NULL,'SYSTEM_RECEIVED','2024-03-17 06:51:55',5,1);
INSERT INTO histories VALUES (12,'2024-03-17 06:51:55',_binary '\0',16.051907295167997,108.21672101356653,NULL,'SYSTEM_RECEIVED','2024-03-17 06:51:55',5,1);
INSERT INTO histories VALUES (13,'2024-03-17 08:25:03',_binary '\0',16.051907295167997,108.21672101356653,NULL,'SYSTEM_RECEIVED','2024-03-17 08:25:03',5,1);
INSERT INTO histories VALUES (14,'2024-03-17 08:25:07',_binary '\0',16.051907295167997,108.21672101356653,NULL,'SYSTEM_RECEIVED','2024-03-17 08:25:07',5,1);
INSERT INTO histories VALUES (15,'2024-03-17 09:08:18',_binary '\0',16.051907295167997,108.21672101356653,NULL,'COMPLETED','2024-03-17 09:08:18',5,9);
INSERT INTO histories VALUES (16,'2024-03-17 09:13:44',_binary '\0',16.051907295167997,108.21672101356653,NULL,'CANCELLED_USER','2024-03-17 09:13:44',5,9);
INSERT INTO histories VALUES (17,'2024-03-17 09:15:01',_binary '\0',16.051907295167997,108.21672101356653,NULL,'SYSTEM_RECEIVED','2024-03-17 09:15:01',5,9);

--
-- Dumping data for table `history_logs`
--

INSERT INTO history_logs VALUES (1,'2024-03-13 11:00:45','CREATE','latitude','16.234234234',' ','USER',1);
INSERT INTO history_logs VALUES (2,'2024-03-13 11:00:45','CREATE','longitude','108.123145123',' ','USER',1);
INSERT INTO history_logs VALUES (3,'2024-03-13 11:00:45','CREATE','note','Hello',' ','USER',1);
INSERT INTO history_logs VALUES (4,'2024-03-13 11:00:45','CREATE','Status','SYSTEM_RECEIVED',' ','USER',1);
INSERT INTO history_logs VALUES (5,'2024-03-13 11:01:25','UPDATE','latitude','108.11111','108.123145123','USER',1);
INSERT INTO history_logs VALUES (6,'2024-03-13 11:12:51','UPDATE','latitude','108.2222','108.11111','USER',1);
INSERT INTO history_logs VALUES (7,'2024-03-13 11:13:15','CREATE','latitude','16.234234234',' ','USER',2);
INSERT INTO history_logs VALUES (8,'2024-03-13 11:13:15','CREATE','longitude','108.123145123',' ','USER',2);
INSERT INTO history_logs VALUES (9,'2024-03-13 11:13:15','CREATE','note','Hello',' ','USER',2);
INSERT INTO history_logs VALUES (10,'2024-03-13 11:13:15','CREATE','Status','SYSTEM_RECEIVED',' ','USER',2);
INSERT INTO history_logs VALUES (11,'2024-03-13 11:28:44','UPDATE','Status','CONFIRMED','SYSTEM_RECEIVED','RESCUE_STATION',1);
INSERT INTO history_logs VALUES (12,'2024-03-13 11:29:15','UPDATE','Status','ON_THE_WAY','CONFIRMED','RESCUE_STATION',1);
INSERT INTO history_logs VALUES (13,'2024-03-13 11:29:31','UPDATE','Status','ARRIVED','ON_THE_WAY','RESCUE_STATION',1);
INSERT INTO history_logs VALUES (14,'2024-03-13 11:31:55','UPDATE','Status','COMPLETED','ARRIVED','RESCUE_STATION',1);
INSERT INTO history_logs VALUES (15,'2024-03-13 11:58:57','CREATE','latitude','16.234234234',' ','USER',3);
INSERT INTO history_logs VALUES (16,'2024-03-13 11:58:57','CREATE','longitude','108.123145123',' ','USER',3);
INSERT INTO history_logs VALUES (17,'2024-03-13 11:58:57','CREATE','note','Hello',' ','USER',3);
INSERT INTO history_logs VALUES (18,'2024-03-13 11:58:57','CREATE','Status','SYSTEM_RECEIVED',' ','USER',3);
INSERT INTO history_logs VALUES (19,'2024-03-13 12:10:43','UPDATE','img1','2-c330e61e-0042-4afb-a991-934dbbd05545-image.jpg',NULL,'USER',2);
INSERT INTO history_logs VALUES (20,'2024-03-13 12:26:30','CREATE','latitude','16.234234234',' ','USER',4);
INSERT INTO history_logs VALUES (21,'2024-03-13 12:26:30','CREATE','longitude','108.123145123',' ','USER',4);
INSERT INTO history_logs VALUES (22,'2024-03-13 12:26:30','CREATE','note','Hello',' ','USER',4);
INSERT INTO history_logs VALUES (23,'2024-03-13 12:26:30','CREATE','Status','SYSTEM_RECEIVED',' ','USER',4);
INSERT INTO history_logs VALUES (24,'2024-03-16 11:48:52','UPDATE','img1','2-6b0d5d4b-c326-45bc-8bae-8a8cd557b03e-image.jpg','2-c330e61e-0042-4afb-a991-934dbbd05545-image.jpg','USER',2);
INSERT INTO history_logs VALUES (25,'2024-03-17 03:14:18','CREATE','latitude','16.234234234',' ','USER',5);
INSERT INTO history_logs VALUES (26,'2024-03-17 03:14:18','CREATE','longitude','108.123145123',' ','USER',5);
INSERT INTO history_logs VALUES (27,'2024-03-17 03:14:18','CREATE','note','Hello',' ','USER',5);
INSERT INTO history_logs VALUES (28,'2024-03-17 03:14:18','CREATE','Status','SYSTEM_RECEIVED',' ','USER',5);
INSERT INTO history_logs VALUES (29,'2024-03-17 05:22:59','CREATE','latitude','16.234234234',' ','USER',6);
INSERT INTO history_logs VALUES (30,'2024-03-17 05:22:59','CREATE','longitude','108.123145123',' ','USER',6);
INSERT INTO history_logs VALUES (31,'2024-03-17 05:22:59','CREATE','note',NULL,' ','USER',6);
INSERT INTO history_logs VALUES (32,'2024-03-17 05:22:59','CREATE','Status','SYSTEM_RECEIVED',' ','USER',6);
INSERT INTO history_logs VALUES (33,'2024-03-17 05:30:40','UPDATE','latitude','108.2222','108.123145123','USER',3);
INSERT INTO history_logs VALUES (34,'2024-03-17 05:41:07','UPDATE','Status','CONFIRMED','SYSTEM_RECEIVED','RESCUE_STATION',4);
INSERT INTO history_logs VALUES (35,'2024-03-17 05:45:34','UPDATE','Status','ON_THE_WAY','CONFIRMED','RESCUE_STATION',4);
INSERT INTO history_logs VALUES (36,'2024-03-17 05:45:46','UPDATE','Status','COMPLETED','ON_THE_WAY','RESCUE_STATION',4);
INSERT INTO history_logs VALUES (37,'2024-03-17 06:51:43','CREATE','latitude','16.051907295167997',' ','USER',7);
INSERT INTO history_logs VALUES (38,'2024-03-17 06:51:44','CREATE','longitude','108.21672101356653',' ','USER',7);
INSERT INTO history_logs VALUES (39,'2024-03-17 06:51:44','CREATE','note',NULL,' ','USER',7);
INSERT INTO history_logs VALUES (40,'2024-03-17 06:51:44','CREATE','Status','SYSTEM_RECEIVED',' ','USER',7);
INSERT INTO history_logs VALUES (41,'2024-03-17 06:51:53','CREATE','latitude','16.051907295167997',' ','USER',8);
INSERT INTO history_logs VALUES (42,'2024-03-17 06:51:53','CREATE','longitude','108.21672101356653',' ','USER',8);
INSERT INTO history_logs VALUES (43,'2024-03-17 06:51:53','CREATE','note',NULL,' ','USER',8);
INSERT INTO history_logs VALUES (44,'2024-03-17 06:51:53','CREATE','Status','SYSTEM_RECEIVED',' ','USER',8);
INSERT INTO history_logs VALUES (45,'2024-03-17 06:51:53','CREATE','latitude','16.051907295167997',' ','USER',9);
INSERT INTO history_logs VALUES (46,'2024-03-17 06:51:53','CREATE','longitude','108.21672101356653',' ','USER',9);
INSERT INTO history_logs VALUES (47,'2024-03-17 06:51:53','CREATE','note',NULL,' ','USER',9);
INSERT INTO history_logs VALUES (48,'2024-03-17 06:51:53','CREATE','Status','SYSTEM_RECEIVED',' ','USER',9);
INSERT INTO history_logs VALUES (49,'2024-03-17 06:51:54','CREATE','latitude','16.051907295167997',' ','USER',10);
INSERT INTO history_logs VALUES (50,'2024-03-17 06:51:54','CREATE','longitude','108.21672101356653',' ','USER',10);
INSERT INTO history_logs VALUES (51,'2024-03-17 06:51:54','CREATE','note',NULL,' ','USER',10);
INSERT INTO history_logs VALUES (52,'2024-03-17 06:51:54','CREATE','Status','SYSTEM_RECEIVED',' ','USER',10);
INSERT INTO history_logs VALUES (53,'2024-03-17 06:51:55','CREATE','latitude','16.051907295167997',' ','USER',11);
INSERT INTO history_logs VALUES (54,'2024-03-17 06:51:55','CREATE','longitude','108.21672101356653',' ','USER',11);
INSERT INTO history_logs VALUES (55,'2024-03-17 06:51:55','CREATE','note',NULL,' ','USER',11);
INSERT INTO history_logs VALUES (56,'2024-03-17 06:51:55','CREATE','Status','SYSTEM_RECEIVED',' ','USER',11);
INSERT INTO history_logs VALUES (57,'2024-03-17 06:51:55','CREATE','latitude','16.051907295167997',' ','USER',12);
INSERT INTO history_logs VALUES (58,'2024-03-17 06:51:55','CREATE','longitude','108.21672101356653',' ','USER',12);
INSERT INTO history_logs VALUES (59,'2024-03-17 06:51:55','CREATE','note',NULL,' ','USER',12);
INSERT INTO history_logs VALUES (60,'2024-03-17 06:51:55','CREATE','Status','SYSTEM_RECEIVED',' ','USER',12);
INSERT INTO history_logs VALUES (61,'2024-03-17 07:14:47','UPDATE','img1','7-e99884de-1d99-4287-8813-207d04d87ca3-image.jpg',NULL,'USER',7);
INSERT INTO history_logs VALUES (62,'2024-03-17 08:25:03','CREATE','latitude','16.051907295167997',' ','USER',13);
INSERT INTO history_logs VALUES (63,'2024-03-17 08:25:03','CREATE','longitude','108.21672101356653',' ','USER',13);
INSERT INTO history_logs VALUES (64,'2024-03-17 08:25:03','CREATE','note',NULL,' ','USER',13);
INSERT INTO history_logs VALUES (65,'2024-03-17 08:25:03','CREATE','Status','SYSTEM_RECEIVED',' ','USER',13);
INSERT INTO history_logs VALUES (66,'2024-03-17 08:25:07','CREATE','latitude','16.051907295167997',' ','USER',14);
INSERT INTO history_logs VALUES (67,'2024-03-17 08:25:07','CREATE','longitude','108.21672101356653',' ','USER',14);
INSERT INTO history_logs VALUES (68,'2024-03-17 08:25:07','CREATE','note',NULL,' ','USER',14);
INSERT INTO history_logs VALUES (69,'2024-03-17 08:25:07','CREATE','Status','SYSTEM_RECEIVED',' ','USER',14);
INSERT INTO history_logs VALUES (70,'2024-03-17 09:08:18','CREATE','latitude','16.051907295167997',' ','USER',15);
INSERT INTO history_logs VALUES (71,'2024-03-17 09:08:18','CREATE','longitude','108.21672101356653',' ','USER',15);
INSERT INTO history_logs VALUES (72,'2024-03-17 09:08:18','CREATE','note',NULL,' ','USER',15);
INSERT INTO history_logs VALUES (73,'2024-03-17 09:08:18','CREATE','Status','SYSTEM_RECEIVED',' ','USER',15);
INSERT INTO history_logs VALUES (74,'2024-03-17 09:13:44','CREATE','latitude','16.051907295167997',' ','USER',16);
INSERT INTO history_logs VALUES (75,'2024-03-17 09:13:44','CREATE','longitude','108.21672101356653',' ','USER',16);
INSERT INTO history_logs VALUES (76,'2024-03-17 09:13:44','CREATE','note',NULL,' ','USER',16);
INSERT INTO history_logs VALUES (77,'2024-03-17 09:13:44','CREATE','Status','SYSTEM_RECEIVED',' ','USER',16);
INSERT INTO history_logs VALUES (78,'2024-03-17 09:15:01','CREATE','latitude','16.051907295167997',' ','USER',17);
INSERT INTO history_logs VALUES (79,'2024-03-17 09:15:01','CREATE','longitude','108.21672101356653',' ','USER',17);
INSERT INTO history_logs VALUES (80,'2024-03-17 09:15:01','CREATE','note',NULL,' ','USER',17);
INSERT INTO history_logs VALUES (81,'2024-03-17 09:15:01','CREATE','Status','SYSTEM_RECEIVED',' ','USER',17);

--
-- Dumping data for table `history_media`
--

INSERT INTO history_media VALUES (1,NULL,NULL,NULL,NULL,1);
INSERT INTO history_media VALUES (2,'2-6b0d5d4b-c326-45bc-8bae-8a8cd557b03e-image.jpg',NULL,NULL,NULL,2);
INSERT INTO history_media VALUES (3,NULL,NULL,NULL,NULL,3);
INSERT INTO history_media VALUES (4,NULL,NULL,NULL,NULL,4);
INSERT INTO history_media VALUES (5,NULL,NULL,NULL,NULL,5);
INSERT INTO history_media VALUES (6,NULL,NULL,NULL,NULL,6);
INSERT INTO history_media VALUES (7,'7-e99884de-1d99-4287-8813-207d04d87ca3-image.jpg',NULL,NULL,NULL,7);
INSERT INTO history_media VALUES (8,NULL,NULL,NULL,NULL,8);
INSERT INTO history_media VALUES (9,NULL,NULL,NULL,NULL,9);
INSERT INTO history_media VALUES (10,NULL,NULL,NULL,NULL,10);
INSERT INTO history_media VALUES (11,NULL,NULL,NULL,NULL,11);
INSERT INTO history_media VALUES (12,NULL,NULL,NULL,NULL,12);
INSERT INTO history_media VALUES (13,NULL,NULL,NULL,NULL,13);
INSERT INTO history_media VALUES (14,NULL,NULL,NULL,NULL,14);
INSERT INTO history_media VALUES (15,NULL,NULL,NULL,NULL,15);
INSERT INTO history_media VALUES (16,NULL,NULL,NULL,NULL,16);
INSERT INTO history_media VALUES (17,NULL,NULL,NULL,NULL,17);

--
-- Dumping data for table `reports`
--

INSERT INTO reports VALUES (1,1,'RESCUE','2024-03-15 09:01:13','Thàng user nó tạo báo động giả');
INSERT INTO reports VALUES (2,1,'USER','2024-03-15 09:12:13','Thàng cứu hộ nó không tới cứu');

--
-- Dumping data for table `rescue_stations`

INSERT INTO `rescue_stations` VALUES (1,'Đà Nãng','2024-03-13 11:00:21','Cứu nhiều người',_binary '',16.059882,108.209734,'Tram cứu hộ DTU','2024-03-13 11:00:21','0866642463','','',2);
INSERT INTO `rescue_stations` VALUES (5,'Đà Nãng','2024-03-17 06:50:43','Cứu nhiều người',_binary '',16.0483184207492,108.21265376887816,'Tram cứu hộ ABC','2024-03-20 08:37:04','0866642460','','',4);
INSERT INTO `rescue_stations` VALUES (6,'Da Nang','2024-03-17 08:11:53','Cứu nhiều người',_binary '',16.0483184207492,108.21265376887816,'Tram cứu hộ ABC','2024-03-17 08:11:53','0866642464','','',5);
INSERT INTO `rescue_stations` VALUES (7,'Da Nang','2024-03-17 08:18:29','Cứu nhiều người',_binary '',16.0483184207492,108.21265376887816,'Tram cứu hộ ABC','2024-03-17 08:18:29','0866642465','','',6);
INSERT INTO `rescue_stations` VALUES (8,'Da Nang','2024-03-17 08:19:50','Cứu nhiều người',_binary '',16.0483184207492,108.21265376887816,'Tram cứu hộ Vẫn như cũ','2024-03-17 08:19:50','0866642466','','',7);
INSERT INTO `rescue_stations` VALUES (9,'Da Nang','2024-03-17 08:21:36','Cứu nhiều người',_binary '',16.0483184207492,108.21265376887816,'Tram cứu hộ Vẫn như cũ','2024-03-17 08:21:36','0866642454','','',8);
INSERT INTO `rescue_stations` VALUES (10,'Da Nang','2024-03-20 07:01:07','Cứu nhiều người',_binary '',16.05172056866692,108.15419956917019,'Tram cứu hộ Kinh Tế','2024-03-20 07:01:07','0866642111','','',10);
INSERT INTO `rescue_stations` VALUES (11,'Da Nang','2024-03-20 07:40:48','Cứu nhiều người',_binary '',16.111111,108.15419213691702,'Tram cứu hộ Kinh Tế','2024-03-20 07:40:48','0866642112','','',11);

--
-- Dumping data for table `tokens`
--

INSERT INTO tokens VALUES (3,'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDI0NjMiLCJzdWIiOiIwODY2NjQyNDYzIiwiZXhwIjoxNzEyOTM0ODUyfQ.m5FCZW7OXMexPFsNyo9qpyyBNSsCf7ekznMWRQEiZMU','Bearer','2024-04-12 11:14:13',0,0,0,2);
INSERT INTO tokens VALUES (34,'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDI0NjIiLCJzdWIiOiIwODY2NjQyNDYyIiwiZXhwIjoxNzEzMjU3MDYxfQ.YiNdBw_z_8Oqp-e_GAUZDnoQ3R2jqqsq7rbKH7DX0sI','Bearer','2024-04-16 04:44:22',0,0,0,1);
INSERT INTO tokens VALUES (35,'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDI0NjIiLCJzdWIiOiIwODY2NjQyNDYyIiwiZXhwIjoxNzEzMjU5MzA2fQ.t6L3UmnJ0B7DCiNm71xps4d8lyoTZ0pZxmXHfBnYFGI','Bearer','2024-04-16 05:21:46',0,0,0,1);
INSERT INTO tokens VALUES (36,'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDI0NjMiLCJzdWIiOiIwODY2NjQyNDYzIiwiZXhwIjoxNzEzMjYwNDA0fQ.fG30WWmey0BaLdkZ33YOuNtDVtDt9WHi_kljWMc3-NE','Bearer','2024-04-16 05:40:05',0,0,0,2);
INSERT INTO tokens VALUES (37,'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDI0NjAiLCJzdWIiOiIwODY2NjQyNDYwIiwiZXhwIjoxNzEzMjY0Mjg5fQ.X_jONfthT-VdlejsIT5xSX4j6kGeCQd38P1hbP_OGhY','Bearer','2024-04-16 06:44:50',0,0,0,4);
INSERT INTO tokens VALUES (38,'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDI0NjIiLCJzdWIiOiIwODY2NjQyNDYyIiwiZXhwIjoxNzEzMjY0MzUyfQ.VLoPcfl_zmJ5Cs5pN6V8SyS13TEQvppJ84q2DNiP-mU','Bearer','2024-04-16 06:45:52',0,0,0,1);
INSERT INTO tokens VALUES (39,'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDI0NjAiLCJzdWIiOiIwODY2NjQyNDYwIiwiZXhwIjoxNzEzMjY0Nzg1fQ.x3qIqUXsN2hbAnniHoU7h_YgGZBPlDhED26QIsGDKCs','Bearer','2024-04-16 06:53:06',0,0,0,4);
INSERT INTO tokens VALUES (40,'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDI0NTQiLCJzdWIiOiIwODY2NjQyNDU0IiwiZXhwIjoxNzEzMjcwMjgwfQ.NCiJAjLfWdOrVgPASq2uTy4YhHQ-Ve5_hBHA8gjDH9g','Bearer','2024-04-16 08:24:41',0,0,0,8);
INSERT INTO tokens VALUES (41,'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDI0NzkiLCJzdWIiOiIwODY2NjQyNDc5IiwiZXhwIjoxNzEzMjcwNzI2fQ.gDKqEHR5dAd6vWgcEzN7e6iTIHesw4rfSvGAJVkygUg','Bearer','2024-04-16 08:32:07',0,0,0,9);

--
-- Dumping data for table `users`
--

 INSERT  INTO `users` VALUES (1,'Da Nang','2001-12-11','Aa12345','2024-03-13 10:48:33','Long',_binary '','$2a$10$awp4gwYNYnKtNwApbhlFveL8xG5ezf5B0d6H27ADmxdls1KknNomi','0866642462','Bố',6,'Lý Thành');
 INSERT  INTO `users` VALUES (2,'Da Nang','2001-12-11','Aa12345','2024-03-13 10:59:05','Vinh',_binary '','$2a$10$VpCfT838BSHyRZtKmW6louwGPARbYdcGM49p0ft8cblHD3YroWSLS','0866642463','Bố',6,'Pham Xuan');
 INSERT  INTO `users` VALUES (3,'Da Nang','2001-12-11','Aa111201','2024-03-17 06:44:36','Admin',_binary '','$2a$10$sWXM3376SmtusODmNcHS6e5VIyk56Rbrx.d0ZsvEh5/Dp7c7Lz0Bm','0866642467','Ok',7,'12');
 INSERT  INTO `users` VALUES (4,'Da Nang','2001-12-11','Aa111201','2024-03-17 06:44:42','Admin',_binary '','$2a$10$WuYzFXXCyCfGVJkDOWuF/.YhtszV2usmLENtjspE5EjpC96Pw8SNy','0866642460','Ok',8,'12');
 INSERT  INTO `users` VALUES (5,'Da Nang','2001-12-11','Aa111201','2024-03-17 08:11:53','Admin',_binary '','Aa111201','0866642464','Ok',9,'12');
 INSERT  INTO `users` VALUES (6,'Da Nang','2001-12-11','Aa111201','2024-03-17 08:18:29','Admin',_binary '','$2a$10$kN532HzXF1pEOVFYF6LE7OKSGQdUGN9Me6G7JUiKs7NehHFAUNx/y','0866642465','Ok',10,'12');
 INSERT  INTO `users` VALUES (7,'Da Nang','2001-12-11','Aa111201','2024-03-17 08:19:50','Vũ như ',_binary '','$2a$10$..fAQ0OvypKoA8S.Q4ZZsul4R1h3UhPQmMQQgRrxv90hwbMd3VjV6','0866642466','Ok',6,'Cẩn');
 INSERT  INTO `users` VALUES (8,'Da Nang','2001-12-11','Aa111201','2024-03-17 08:21:36','Vũ như ',_binary '','$2a$10$OqF9e2.KizIE47gYWrq7luPYUsYQF2t4CsNjn9qBGeCwm.TfevzAO','0866642454','Ok',6,'Cẩn');
 INSERT  INTO `users` VALUES (9,'Da Nang','2001-12-11','Aa111201','2024-03-17 08:31:20','Hello',_binary '','$2a$10$7ZgUK0H00Yb6dSkbrlhzSuMT1AqnMh.gZu47w7J8OT4fToCzLaCWO','0866642479','Ok',12,'12');
 INSERT  INTO `users` VALUES (10,'Da Nang','2001-12-11','Aa111201','2024-03-20 07:01:07','111',_binary '','$2a$10$o2y/pqfqhhHDb3NX5v/no.EsmIJ.HMWrTpvT0y2lOQ1rl2VCQUopC','0866642111','Ny4',13,'Demo lock');
 INSERT  INTO `users` VALUES (11,'Da Nang','2001-12-11','Aa111201','2024-03-20 07:40:48','111',_binary '','$2a$10$3JU42nPYu8PUXEKt8r06oOvobVtFpmK9YEeTA6ZXQd0v1qoc/pfo.','0866642112','Ny4',14,'Demo lock');


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed