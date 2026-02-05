ALTER TABLE customers
RENAME COLUMN nome TO name
RENAME COLUMN telefone TO phone
RENAME COLUMN criado_em TO created_at
ADD COLUMN updated_at TIMESTAMP