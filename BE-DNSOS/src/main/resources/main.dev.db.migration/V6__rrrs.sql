
ALTER  TABLE  history_rescues ADD  COLUMN  created_at datetime;
ALTER  TABLE  history_rescues ADD  COLUMN  updated_at datetime;
ALTER  TABLE  history_rescues ADD  COLUMN  cancel  bit DEFAULT FALSE;
ALTER TABLE history_rescues ADD COLUMN `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY;
