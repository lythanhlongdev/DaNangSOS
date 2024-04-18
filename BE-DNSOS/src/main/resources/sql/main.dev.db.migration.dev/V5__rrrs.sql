CREATE TABLE rescue (
                        Id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        latitude DOUBLE NOT NULL,
                        longitude DOUBLE NOT NULL,
                        user_id BIGINT NOT NULL,
                        rescue_station_id BIGINT NOT NULL,
                        CONSTRAINT uk_user UNIQUE (user_id),
                        created_at  datetime not null,
                        CONSTRAINT `FK_rescue_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                        CONSTRAINT `FK_rescue_rescue_station` FOREIGN KEY (`rescue_station_id`) REFERENCES `rescue_stations` (`id`)
);

INSERT INTO  roles values (4,true,'rescue');

ALTER  TABLE  rescue_stations ADD  COLUMN  status enum('ACTIVITY','PAUSE', 'OVERLOAD') DEFAULT 'ACTIVITY';

CREATE TABLE history_rescues(
                        history_id BIGINT NOT NULL,
                        rescue_id BIGINT NOT NULL,
                        CONSTRAINT `PK_history_id_rescues` FOREIGN KEY (`history_id`) REFERENCES `histories` (`id`),
                        CONSTRAINT `PK_history_rescues_id` FOREIGN KEY (`rescue_id`) REFERENCES `rescue` (`id`)
);