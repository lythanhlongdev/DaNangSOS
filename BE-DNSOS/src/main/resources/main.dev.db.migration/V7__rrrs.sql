ALTER TABLE rescue DROP FOREIGN KEY FK_rescue_rescue_station;
ALTER TABLE rescue DROP COLUMN rescue_station_id;



CREATE TABLE rescue_station_rescue_worker(
                        `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        `rescue_id` BIGINT NOT NULL,
                        `rescue_station_id` BIGINT NULL,
                        `created_at` DATETIME NOT NULL,
                        `updated_at` DATETIME NOT NULL,
                        `is_activity` BIT NOT NULL, 
                        CONSTRAINT `PK_rescue_worker_id` FOREIGN KEY (`rescue_id`) REFERENCES `rescue` (`id`),
                        CONSTRAINT `PK_rescue_station_id` FOREIGN KEY (`rescue_station_id`) REFERENCES `rescue_stations` (`id`)
);