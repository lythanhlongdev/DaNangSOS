
DROP TABLE IF EXISTS `flyway_schema_history`;
CREATE TABLE `flyway_schema_history` (
                                         `installed_rank` int NOT NULL,
                                         `version` varchar(50) DEFAULT NULL,
                                         `description` varchar(200) NOT NULL,
                                         `type` varchar(20) NOT NULL,
                                         `script` varchar(1000) NOT NULL,
                                         `checksum` int DEFAULT NULL,
                                         `installed_by` varchar(100) NOT NULL,
                                         `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                         `execution_time` int NOT NULL,
                                         `success` tinyint NOT NULL,
                                         PRIMARY KEY (`installed_rank`)
);

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
                         `role_id` bigint NOT NULL,
                         `is_deleted` bit DEFAULT NULL,
                         `role_name` varchar(10) DEFAULT NULL,
                         PRIMARY KEY (`role_id`)
);

DROP TABLE IF EXISTS `families`;
CREATE TABLE `families` (
                            `family_id` bigint NOT NULL AUTO_INCREMENT,
                            `is_deleted` bit DEFAULT NULL,
                            PRIMARY KEY (`family_id`)
);

DROP TABLE IF EXISTS `rescue_stations`;
CREATE TABLE `rescue_stations` (
                                   `rescue_stations_id` bigint NOT NULL AUTO_INCREMENT,
                                   `address` varchar(255) NOT NULL,
                                   `captain` varchar(60) NOT NULL,
                                   `created_at` datetime DEFAULT NULL,
                                   `description` varchar(255) DEFAULT NULL,
                                   `is_deleted` bit DEFAULT NULL,
                                   `latitude` double DEFAULT NULL,
                                   `longitude` double DEFAULT NULL,
                                   `password` varchar(255) NOT NULL,
                                   `phone_number` varchar(20) NOT NULL,
                                   `rescue_stations_name` varchar(30) NOT NULL,
                                   `role_id` bigint DEFAULT NULL,
                                   PRIMARY KEY (`rescue_stations_id`),
                                   KEY `idx_role_id` (`role_id`),
                                   CONSTRAINT `FK_rescue_stations_roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
);

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
                         `user_id` bigint NOT NULL AUTO_INCREMENT,
                         `address` varchar(255) NOT NULL,
                         `birthday` date DEFAULT NULL,
                         `cccd_or_passport` varchar(30) NOT NULL,
                         `created_at` datetime DEFAULT NULL,
                         `first_name` varchar(20) DEFAULT NULL,
                         `is_deleted` bit DEFAULT NULL,
                         `password` varchar(12) NOT NULL,
                         `phone_number` varchar(20) NOT NULL,
                         `role_family` varchar(255) NOT NULL,
                         `security_code` bigint DEFAULT NULL,
                         `family_id` bigint DEFAULT NULL,
                         `role_id` bigint NOT NULL DEFAULT '2',
                         `last_name` varchar(50) DEFAULT NULL,
                         PRIMARY KEY (`user_id`),
                         KEY `idx_family_id` (`family_id`),
                         KEY `idx_role_id` (`role_id`),
                         CONSTRAINT `FK_users_families` FOREIGN KEY (`family_id`) REFERENCES `families` (`family_id`),
                         CONSTRAINT `FK_users_roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
);

DROP TABLE IF EXISTS `histories`;
CREATE TABLE `histories` (
                             `history_id` bigint NOT NULL AUTO_INCREMENT,
                             `created_at` datetime DEFAULT NULL,
                             `is_deleted` bit DEFAULT NULL,
                             `latitude` double DEFAULT NULL,
                             `longitude` double DEFAULT NULL,
                             `note` varchar(500) DEFAULT NULL,
                             `status` enum('ARRIVED','CANCELLED','CANCELLED_USER','COMPLETED','CONFIRMED','ON_THE_WAY','SYSTEM_RECEIVED') DEFAULT NULL,
                             `updated_at` datetime DEFAULT NULL,
                             `rescue_stations_id` bigint DEFAULT NULL,
                             `user_id` bigint DEFAULT NULL,
                             PRIMARY KEY (`history_id`),
                             KEY `idx_rescue_stations_id` (`rescue_stations_id`),
                             KEY `idx_user_id` (`user_id`),
                             CONSTRAINT `FK_histories_rescue_stations` FOREIGN KEY (`rescue_stations_id`) REFERENCES `rescue_stations` (`rescue_stations_id`),
                             CONSTRAINT `FK_histories_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);

DROP TABLE IF EXISTS `history_logs`;
CREATE TABLE `history_logs` (
                                `log_id` bigint NOT NULL AUTO_INCREMENT,
                                `event_time` datetime DEFAULT NULL,
                                `event_type` varchar(255) DEFAULT NULL,
                                `field_name` varchar(255) DEFAULT NULL,
                                `new_value` varchar(255) DEFAULT NULL,
                                `old_value` varchar(255) DEFAULT NULL,
                                `role` varchar(255) DEFAULT NULL,
                                `history_id` bigint DEFAULT NULL,
                                PRIMARY KEY (`log_id`),
                                KEY `idx_history_id` (`history_id`),
                                CONSTRAINT `FK_history_logs_histories` FOREIGN KEY (`history_id`) REFERENCES `histories` (`history_id`)
);

DROP TABLE IF EXISTS `history_media`;
CREATE TABLE `history_media` (
                                 `media_id` bigint NOT NULL AUTO_INCREMENT,
                                 `image1` varchar(255) DEFAULT NULL,
                                 `image2` varchar(255) DEFAULT NULL,
                                 `image3` varchar(255) DEFAULT NULL,
                                 `voice` varchar(255) DEFAULT NULL,
                                 `history_id` bigint DEFAULT NULL,
                                 PRIMARY KEY (`media_id`),
                                 KEY `idx_history_id` (`history_id`),
                                 CONSTRAINT `FK_history_media_histories` FOREIGN KEY (`history_id`) REFERENCES `histories` (`history_id`)
);

DROP TABLE IF EXISTS `cancel_histories`;
CREATE TABLE `cancel_histories` (
                                    `cancel_id` bigint NOT NULL AUTO_INCREMENT,
                                    `create_at` datetime DEFAULT NULL,
                                    `note` varchar(255) DEFAULT NULL,
                                    `role` varchar(255) DEFAULT NULL,
                                    `history_history_id` bigint DEFAULT NULL,
                                    PRIMARY KEY (`cancel_id`),
                                    UNIQUE KEY `UK_cancel_history_id` (`cancel_id`),
                                    CONSTRAINT `FK_cancel_histories_histories` FOREIGN KEY (`history_history_id`) REFERENCES `histories` (`history_id`)
);


INSERT INTO  roles values (0,false,'admin');
INSERT INTO  roles values (1,false,'rescue');
INSERT INTO  roles values (2,false,'user');