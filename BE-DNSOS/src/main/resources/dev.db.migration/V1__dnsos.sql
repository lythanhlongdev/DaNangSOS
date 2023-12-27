-- Đổi tên cột full_name thành first_name và giới hạn độ dài là VARCHAR(20)
ALTER TABLE users CHANGE COLUMN full_name first_name VARCHAR(20);

-- Thêm cột last_name với giới hạn độ dài là VARCHAR(50)
ALTER TABLE users ADD COLUMN last_name VARCHAR(50);

-- Đặt giá trị mặc định cho cột role_id là 2
ALTER TABLE users ALTER COLUMN role_id SET DEFAULT 2;
