
ALTER TABLE users DROP FOREIGN KEY FK_users_roles;
ALTER TABLE rescue_stations DROP FOREIGN KEY FK_rescue_stations_roles;
DROP INDEX idx_role_id ON rescue_stations;
DROP INDEX idx_role_id ON users;

ALTER  TABLE  users DROP COLUMN  role_id;
ALTER  TABLE  rescue_stations DROP COLUMN  role_id;
ALTER  TABLE  rescue_stations DROP COLUMN  phone_number;
ALTER  TABLE  rescue_stations DROP COLUMN  captain;
ALTER  TABLE  rescue_stations DROP COLUMN  password;


ALTER  TABLE  rescue_stations ADD  COLUMN  updated_at datetime;
ALTER  TABLE  rescue_stations ADD  COLUMN  phone_number1 VARCHAR(20) NOT NULL ;
ALTER  TABLE  rescue_stations ADD  COLUMN  phone_number2 VARCHAR(20);
ALTER  TABLE  rescue_stations ADD  COLUMN  phone_number3 VARCHAR(20);
ALTER  TABLE  rescue_stations ADD  COLUMN  user_id BIGINT;

ALTER TABLE rescue_stations ADD CONSTRAINT uk_user_id UNIQUE (user_id);
ALTER TABLE rescue_stations ADD CONSTRAINT uk_phone1 UNIQUE (phone_number1);
ALTER TABLE rescue_stations ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id);

CREATE TABLE group_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

