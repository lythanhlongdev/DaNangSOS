-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: rrrs_2
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
-- Dumping data for table `families`
--

INSERT INTO `families`
VALUES (1, _binary ''),
       (2, _binary ''),
       (3, _binary ''),
       (4, _binary ''),
       (5, _binary ''),
       (6, _binary ''),
       (7, _binary ''),
       (8, _binary ''),
       (9, _binary ''),
       (10, _binary ''),
       (11, _binary ''),
       (12, _binary ''),
       (13, _binary ''),
       (14, _binary ''),
       (15, _binary ''),
       (16, _binary ''),
       (17, _binary ''),
       (18, _binary ''),
       (19, _binary ''),
       (20, _binary '');

--
-- Dumping data for table `group_role`
--

INSERT INTO `group_role`
VALUES (1, 4, 1),
       (2, 4, 2),
       (3, 4, 3),
       (4, 4, 4),
       (5, 4, 5),
       (6, 4, 6),
       (7, 4, 7),
       (8, 4, 8),
       (9, 4, 9),
       (10, 4, 10),
       (11, 1, 10),
       (12, 2, 11),
       (13, 4, 11),
       (14, 4, 12),
       (15, 2, 12),
       (16, 4, 13),
       (17, 2, 13),
       (18, 4, 14),
       (19, 2, 14),
       (20, 4, 15),
       (21, 2, 15),
       (22, 4, 16),
       (23, 2, 16),
       (24, 4, 17),
       (25, 2, 17),
       (26, 3, 18),
       (27, 4, 18),
       (28, 3, 19),
       (29, 4, 19),
       (30, 3, 20),
       (31, 4, 20);

--
-- Dumping data for table `histories`
--

INSERT INTO `histories`
VALUES (1, '2024-04-16 05:13:38', _binary '\0', 16.22222, 108.3333333, NULL, 'CONFIRMED', '2024-04-16 05:13:38', 1, 1);

--
-- Dumping data for table `history_cancel`
--


--
-- Dumping data for table `history_logs`
--

INSERT INTO `history_logs`
VALUES (1, '2024-04-16 05:13:38', 'CREATE', 'latitude', '16.066046914031997', ' ', 'USER', 1),
       (2, '2024-04-16 05:13:38', 'CREATE', 'longitude', '108.20191072520986', ' ', 'USER', 1),
       (3, '2024-04-16 05:13:38', 'CREATE', 'note', NULL, ' ', 'USER', 1),
       (4, '2024-04-16 05:13:38', 'CREATE', 'Status', 'SYSTEM_RECEIVED', ' ', 'USER', 1),
       (5, '2024-04-16 05:15:35', 'UPDATE', 'img1', '1-577f4d84-93fd-492c-aa92-25f7990c9c7c-image.jpg', NULL, 'USER',
        1),
       (6, '2024-04-16 05:15:35', 'UPDATE', 'img2', '1-79d38bab-d028-45b0-9e6b-755843c9d899-image.jpg', NULL, 'USER',
        1),
       (7, '2024-04-16 05:15:35', 'UPDATE', 'img3', '1-c940bf62-4b96-420a-b956-a994c13fd601-image.png', NULL, 'USER',
        1),
       (8, '2024-04-16 05:15:35', 'UPDATE', 'voice', '1-71c06e43-51b4-48f6-a47b-e862c616720a-voice.mp3', NULL, 'USER',
        1);

--
-- Dumping data for table `history_media`
--

INSERT INTO `history_media`
VALUES (1, '1-577f4d84-93fd-492c-aa92-25f7990c9c7c-image.jpg', '1-79d38bab-d028-45b0-9e6b-755843c9d899-image.jpg',
        '1-c940bf62-4b96-420a-b956-a994c13fd601-image.png', '1-71c06e43-51b4-48f6-a47b-e862c616720a-voice.mp3', 1);

--
-- Dumping data for table `history_rescues`
--

INSERT INTO `history_rescues`
VALUES (1, 1),
       (1, 3);

--
-- Dumping data for table `reports`
--

INSERT INTO `reports`
VALUES (1, 1, 'RESCUE', '2024-04-16 05:18:48', 'Rescue nó mất dạy nó đấm tao');

--
-- Dumping data for table `rescue`
--

INSERT INTO `rescue`
VALUES (1, 16.22223, 108.333333, 18, 1, '2024-04-16 05:08:45'),
       (2, 0, 0, 19, 2, '2024-04-16 05:26:29'),
       (3, 16.22223, 108.333333, 20, 1, '2024-04-16 07:53:41');

--
-- Dumping data for table `rescue_stations`
--

INSERT INTO `rescue_stations`
VALUES (1, '3 Quang Trung, Hải Châu 1, Hải Châu, Đà Nẵng 550000, Việt Nam', '2024-04-16 04:31:02', 'Cứu nhiều người',
        _binary '', 16.07466086007006, 108.22286741327824, 'Tram cứu hộ Đại Học Duy Tan 1', '2024-04-16 05:13:23',
        '0866642010', '', '', 11, 'ACTIVITY'),
       (2, '254 Đ Nguyễn Văn Linh, Thạc Gián, Thanh Khê, Đà Nẵng 550000, Việt Nam', '2024-04-16 04:43:56',
        'Cứu nhiều người', _binary '', 16.05994506464399, 108.20974056263304, 'Tram cứu hộ Đại Học Duy Tan 2',
        '2024-04-16 04:43:56', '0866642020', '', '', 12, 'PAUSE'),
       (3, '3 Quang Trung, Hải Châu 1, Hải Châu, Đà Nẵng 550000, Việt Nam', '2024-04-16 04:45:00', 'Cứu nhiều người',
        _binary '', 16.07466086007006, 108.22286741327824, 'Tram cứu hộ Đại Học Duy Tan 3', '2024-04-16 04:45:00',
        '0866642030', '', '', 13, 'PAUSE'),
       (4, '71 Ngũ Hành Sơn, Bắc Mỹ An, Ngũ Hành Sơn, Đà Nẵng 550000, Việt Nam', '2024-04-16 04:46:03',
        'Cứu nhiều người', _binary '', 16.047403342912972, 108.23944406013102, 'Tram cứu hộ Đại Học Kinh Tế',
        '2024-04-16 04:46:03', '0866642040', '', '', 14, 'PAUSE'),
       (5, '566 Núi Thành, Hoà Cường Nam, Hải Châu, Đà Nẵng, Việt Nam', '2024-04-16 04:46:58', 'Cứu nhiều người',
        _binary '', 16.03226882632708, 108.22214114538947, 'Tram cứu hộ Đại Học Kiến Trúc', '2024-04-16 04:46:58',
        '0866642050', '', '', 15, 'PAUSE'),
       (6, '54 Nguyễn Lương Bằng, Hoà Khánh Bắc, Liên Chiểu, Đà Nẵng 550000, Việt Nam', '2024-04-16 04:47:44',
        'Cứu nhiều người', _binary '', 16.073657517973263, 108.14987014259869, 'Tram cứu hộ Đại Học Kiến Trúc',
        '2024-04-16 04:47:44', '0866642060', '', '', 16, 'PAUSE'),
       (7, '120 Hoàng Minh Thảo, Hoà Khánh Nam, Liên Chiểu, Đà Nẵng 550000, Việt Nam', '2024-04-16 04:48:22',
        'Cứu nhiều người', _binary '', 16.049358695712574, 108.16046709480686, 'Tram cứu hộ Đại Học Kiến Trúc',
        '2024-04-16 04:48:22', '0866642070', '', '', 17, 'PAUSE');


INSERT INTO `tokens`
VALUES (1,
        'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDIwMDEiLCJzdWIiOiIwODY2NjQyMDAxIiwiZXhwIjoxNzE1ODQ4MDI5fQ.3UtrxHojlgL5PPEX0aeBICJRuO4_iW36EvcnUcLCYgw',
        'Bearer', '2024-05-16 04:27:10', 0, 0, 0, 1),
       (2,
        'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDI0NjIiLCJzdWIiOiIwODY2NjQyNDYyIiwiZXhwIjoxNzE1ODQ4MTU3fQ.OqXIUc3miVn_veVtdhihPwOAvWm3L3y3LCNvUD0_uU8',
        'Bearer', '2024-05-16 04:29:18', 0, 0, 0, 10),
       (4,
        'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDIwMjAiLCJzdWIiOiIwODY2NjQyMDIwIiwiZXhwIjoxNzE1ODQ4MzgxfQ.7XhPZntVxyipmNDs07uhw3bFPBStbcd6lKn6pZ_wT50',
        'Bearer', '2024-05-16 04:33:01', 0, 0, 0, 11),
       (5,
        'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDIwMjAiLCJzdWIiOiIwODY2NjQyMDIwIiwiZXhwIjoxNzE1ODQ4NDcwfQ.IdellMaXvO5hGlmHfcjjnP1PceLAwa9gA2Rcqv9MgUQ',
        'Bearer', '2024-05-16 04:34:31', 0, 0, 0, 11),
       (6,
        'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDIwMDIiLCJzdWIiOiIwODY2NjQyMDAyIiwiZXhwIjoxNzE1ODQ4NjQ3fQ.H5bv-JIC6ARTqzYNyIbMN22a09BLnPQHoZjUWxXoJDs',
        'Bearer', '2024-05-16 04:37:27', 0, 0, 0, 2),
       (7,
        'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDIwMDMiLCJzdWIiOiIwODY2NjQyMDAzIiwiZXhwIjoxNzE1ODQ4NjcxfQ.xWDLHqQPkEMtFAiXwz-2cYQ-AJa0-5rLk-SP4fqjMXI',
        'Bearer', '2024-05-16 04:37:51', 0, 0, 0, 3),
       (9,
        'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDIwMDQiLCJzdWIiOiIwODY2NjQyMDA0IiwiZXhwIjoxNzE1ODQ4NzY4fQ.zbrFYpl8d-n1jX75vY-rB2wjdJVTesReSnjUI9I3aVA',
        'Bearer', '2024-05-16 04:39:28', 0, 0, 0, 4),
       (10,
        'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDIwMDQiLCJzdWIiOiIwODY2NjQyMDA0IiwiZXhwIjoxNzE1ODQ4NzY5fQ.G9mhyjyRa7HBD1zE01PIVKBHHt2ZjPuXHBm9IqCSKxQ',
        'Bearer', '2024-05-16 04:39:29', 0, 0, 0, 4),
       (11,
        'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDIwMDQiLCJzdWIiOiIwODY2NjQyMDA0IiwiZXhwIjoxNzE1ODQ4Nzg5fQ.3i_1dEHRzkw1sSM5ylsqQOdQtw-1YiPG74Kc9FAGdwo',
        'Bearer', '2024-05-16 04:39:49', 0, 0, 0, 4),
       (12,
        'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDIwMDUiLCJzdWIiOiIwODY2NjQyMDA1IiwiZXhwIjoxNzE1ODQ4ODI3fQ.GPRTtgCTZRcFMnqhQj7CqXBWhXiekcXrQs5zizbTAd0',
        'Bearer', '2024-05-16 04:40:28', 0, 0, 0, 5),
       (13,
        'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDIwMDYiLCJzdWIiOiIwODY2NjQyMDA2IiwiZXhwIjoxNzE1ODQ4ODUzfQ.xRBLJRszkIzASSFTvakAmfjzLA2dxGNlJPXPrRpnhBM',
        'Bearer', '2024-05-16 04:40:54', 0, 0, 0, 6),
       (14,
        'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDIwMDciLCJzdWIiOiIwODY2NjQyMDA3IiwiZXhwIjoxNzE1ODQ4ODcxfQ.uBk7u8atC7-cVErHrdcFvvwQOngE3luIWSXvF3yTTg0',
        'Bearer', '2024-05-16 04:41:11', 0, 0, 0, 7),
       (15,
        'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDIwMDgiLCJzdWIiOiIwODY2NjQyMDA4IiwiZXhwIjoxNzE1ODQ4ODkxfQ.6EblXJbApydql6l0MuDwSgrycSimz2w4qX9wK63DblE',
        'Bearer', '2024-05-16 04:41:32', 0, 0, 0, 8),
       (16,
        'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDIwMDkiLCJzdWIiOiIwODY2NjQyMDA5IiwiZXhwIjoxNzE1ODQ4OTMyfQ._6gdYiE0juAnB3eGd7wSXnnhQCB3EATGe5q4L3Yasko',
        'Bearer', '2024-05-16 04:42:13', 0, 0, 0, 9),
       (17,
        'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDIwMTAiLCJzdWIiOiIwODY2NjQyMDEwIiwiZXhwIjoxNzE1ODQ5NzEyfQ.EvbTSPpe4jIHd34MXwQhy4k7A-_LeRMPnFl5tJZx4Tk',
        'Bearer', '2024-05-16 04:55:12', 0, 0, 0, 11),
       (18,
        'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDIwMjAiLCJzdWIiOiIwODY2NjQyMDIwIiwiZXhwIjoxNzE1ODQ5NzQ2fQ.jLgC5bA4W-Q1X7o1JCNakpDnwgDqRYDw8rwAh0Smphg',
        'Bearer', '2024-05-16 04:55:46', 0, 0, 0, 12),
       (19,
        'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDIwMzAiLCJzdWIiOiIwODY2NjQyMDMwIiwiZXhwIjoxNzE1ODQ5ODQzfQ.QfBNRXLvBv4UIPNbt0iYfLX83n3sana8Y_VJClEYu1g',
        'Bearer', '2024-05-16 04:57:23', 0, 0, 0, 13),
       (20,
        'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDIwNDAiLCJzdWIiOiIwODY2NjQyMDQwIiwiZXhwIjoxNzE1ODQ5ODcwfQ.JB8IdTKjNxJlukLUOxrkwS9HJbLoCmF0JqvfY754uA8',
        'Bearer', '2024-05-16 04:57:51', 0, 0, 0, 14),
       (21,
        'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDIwNTAiLCJzdWIiOiIwODY2NjQyMDUwIiwiZXhwIjoxNzE1ODQ5ODkzfQ.LXdgvMr9ERtSb9AwMFFWLKYYL7Ya4b2ekrQS5khFCzY',
        'Bearer', '2024-05-16 04:58:14', 0, 0, 0, 15),
       (22,
        'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDIwNjAiLCJzdWIiOiIwODY2NjQyMDYwIiwiZXhwIjoxNzE1ODQ5OTE2fQ.JscmIusDBS1YOlA_AVfOomE9peKBWYBD5k0zlTVv0-Y',
        'Bearer', '2024-05-16 04:58:37', 0, 0, 0, 16),
       (23,
        'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDIwNzAiLCJzdWIiOiIwODY2NjQyMDcwIiwiZXhwIjoxNzE1ODQ5OTM3fQ.P-C1FsJwjZYmGWEYudFw7N6964y04OWD8CVNhsPRbNY',
        'Bearer', '2024-05-16 04:58:58', 0, 0, 0, 17),
       (24,
        'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDIxMDAiLCJzdWIiOiIwODY2NjQyMTAwIiwiZXhwIjoxNzE1ODUwNTYxfQ.Hwz_fcD8zVHjUlMbt77GpiMckGiSF_6yavvKcRp75-o',
        'Bearer', '2024-05-16 05:09:22', 0, 0, 0, 18),
       (25,
        'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDIwMDEiLCJzdWIiOiIwODY2NjQyMDAxIiwiZXhwIjoxNzE1ODUwNzQ3fQ.1SG37BzvengDJSXeMKtDPj88I6sP_aUGYDNPgJcIXMs',
        'Bearer', '2024-05-16 05:12:27', 0, 0, 0, 1),
       (26,
        'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDIyMDAiLCJzdWIiOiIwODY2NjQyMjAwIiwiZXhwIjoxNzE1ODUxNjAyfQ.LO2Bn6rLIufa19p7vTqBeZZIbKY_BB0MoDxCCbwpNgg',
        'Bearer', '2024-05-16 05:26:43', 0, 0, 0, 19),
       (27,
        'eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU51bWJlciI6IjA4NjY2NDIzMDAiLCJzdWIiOiIwODY2NjQyMzAwIiwiZXhwIjoxNzE1ODYwNDM1fQ.CRK-fed3elYSgIbVUDYqGtSeguaxRJxghvVK_fG-k2A',
        'Bearer', '2024-05-16 07:53:55', 0, 0, 0, 20);

--
-- Dumping data for table `users`
--

INSERT INTO `users`
VALUES (1, 'Da Nang', '2001-12-11', 'Aa111201', '2024-04-16 04:26:33', 'User', _binary '',
        '$2a$10$NnOaKxL2uuHQ7EyRuSSk8OXIuDmqxRGwveJbGYqHdJY5zqkC6f1YK', '0866642001', 'Oke', 1, '1'),
       (2, 'Da Nang', '2001-12-11', 'Aa111201', '2024-04-16 04:26:38', 'User', _binary '',
        '$2a$10$0vBddVabY.E.DoIBB9onmunQygJBHsVoGdjIjv2xcNZlsxc3O7XwG', '0866642002', 'Oke', 2, '2'),
       (3, 'Da Nang', '2001-12-11', 'Aa111201', '2024-04-16 04:26:41', 'User', _binary '',
        '$2a$10$Eo2..nXFvyutmct//d.2fOZl5guneO4yiowTeMMQUPMkWhcL31bWW', '0866642003', 'Oke', 3, '3'),
       (4, 'Da Nang', '2001-12-11', 'Aa111201', '2024-04-16 04:26:46', 'User', _binary '',
        '$2a$10$3wNOHwRvSSsYOzyyioK78ek1b1owjUR9r7L.KA9h4ReJP3DyNpT0O', '0866642004', 'Oke', 4, '4'),
       (5, 'Da Nang', '2001-12-11', 'Aa111201', '2024-04-16 04:26:49', 'User', _binary '',
        '$2a$10$DyU5DY/NDFLzOf1yDwPfiewU4u6bbXE0zsFRWlo2cHy/0d7yDkYKm', '0866642005', 'Oke', 5, '5'),
       (6, 'Da Nang', '2001-12-11', 'Aa111201', '2024-04-16 04:26:52', 'User', _binary '',
        '$2a$10$6CESTbQXMxpBIE6qgy0XfuGNXRS6WXrgnIMp0Lh6vIcDKXgWqP/K2', '0866642006', 'Oke', 6, '6'),
       (7, 'Da Nang', '2001-12-11', 'Aa111201', '2024-04-16 04:26:56', 'User', _binary '',
        '$2a$10$8RJLPX.KHf3wkeRHNwkM4ueOqCFfDiV.0pzC4Z2zstui5J5ke9LeW', '0866642007', 'Oke', 7, '7'),
       (8, 'Da Nang', '2001-12-11', 'Aa111201', '2024-04-16 04:26:59', 'User', _binary '',
        '$2a$10$RJCrQRMYJTyaQlNrwg9Z1eK4hxJKOYC/qdiKym16lpOJP09xMrgmO', '0866642008', 'Oke', 8, '8'),
       (9, 'Da Nang', '2001-12-11', 'Aa111201', '2024-04-16 04:27:02', 'User', _binary '',
        '$2a$10$pMXz2/DJyPZm1AHcoqn8xOe9aFlYXPfvXRbOoE.KeLKB01mbyKbgq', '0866642009', 'Oke', 9, '9'),
       (10, 'Da Nang', '2001-12-11', 'Aa111201', '2024-04-16 04:28:13', 'Long', _binary '',
        '$2a$10$CcP0TB1CgdbUR4D8c2w2COUcMwJI5e3Xp91l88cXkvSp3wLt4fwTa', '0866642462', 'Father', 10, 'Lý Thành'),
       (11, 'Da Nang', '2001-12-11', 'Aa111201', '2024-04-16 04:31:02', '1', _binary '',
        '$2a$10$5qG7IZGUKE1YRoUAc3DYoO2aD21/ctzzBRZpHpJyNg1vJhXH9vXMu', '0866642010', 'ok', 11, 'Rescue Station'),
       (12, 'Da Nang', '2001-12-11', 'Aa111201', '2024-04-16 04:43:56', '2', _binary '',
        '$2a$10$Y7xiHSiqzQ7sh7CmaH1fmOWfHDXe7uXExxZWXqiz/aDsoZr4LYiay', '0866642020', 'ok', 12, 'Rescue Station'),
       (13, 'Da Nang', '2001-12-11', 'Aa111201', '2024-04-16 04:45:00', '3', _binary '',
        '$2a$10$7UeVmVshHA6/F.Gn9BHtvuHMGhiWRHUAkKc7tX4PAKON1AqOZ9iD.', '0866642030', 'ok', 13, 'Rescue Station'),
       (14, 'Da Nang', '2001-12-11', 'Aa111201', '2024-04-16 04:46:03', '4', _binary '',
        '$2a$10$qWX6Spm0ruOXfbLqrA4a/uvIDN2Ngl3Lk2pacOTKQzAgk0U/qjWPW', '0866642040', 'ok', 14, 'Rescue Station'),
       (15, 'Da Nang', '2001-12-11', 'Aa111201', '2024-04-16 04:46:58', '5', _binary '',
        '$2a$10$V5nke6uwjk06Wdl9TIZz.u7yEaTzC6yUoEhrBOjICbMhZjfCxRxe6', '0866642050', 'ok', 15, 'Rescue Station'),
       (16, 'Da Nang', '2001-12-11', 'Aa111201', '2024-04-16 04:47:44', '6', _binary '',
        '$2a$10$c/Go5lXPXCod4gnM3MA9S.ap.ByuNPpvUGMnuqF2yvCQQbNWOv4iK', '0866642060', 'ok', 16, 'Rescue Station'),
       (17, 'Da Nang', '2001-12-11', 'Aa111201', '2024-04-16 04:48:22', '7', _binary '',
        '$2a$10$YjAxWA3aCuR8eCnIzL4uvOrwKR.WGAfLqvNsMSaIIMfKmk8c42a36', '0866642070', 'ok', 17, 'Rescue Station'),
       (18, 'Da Nang', '2001-12-11', 'Aa111201', '2024-04-16 05:08:45', 'Rescue worker', _binary '',
        '$2a$10$eJe59n5xlBpM4krvl05lOerkzUjK8/FbWYsBs6qC1rBauqWO7lNaC', '0866642100', 'oke', 18, '1'),
       (19, 'Da Nang', '2001-12-11', 'Aa111201', '2024-04-16 05:26:29', 'Rescue worker', _binary '',
        '$2a$10$Cygi/NCUKcbYnM8qs9Rp/.TDU4dKevO8lSc.KQzcimucndgidthcK', '0866642200', 'oke', 19, '2_1'),
       (20, 'Da Nang', '2001-12-11', 'Aa111201', '2024-04-16 07:53:41', 'Rescue worker', _binary '',
        '$2a$10$3blFayzx03vGehpRjYgeEO5JjBwfKWQctoDrXpYkcUIy7BQwKoQrC', '0866642300', 'oke', 20, '1_2');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed
