ALTER TABLE customers RENAME COLUMN nome TO name;
ALTER TABLE customers RENAME COLUMN telephone TO phone;
ALTER TABLE customers RENAME COLUMN create_em TO created_at;
ALTER TABLE customers ADD COLUMN updated_at TIMESTAMP;