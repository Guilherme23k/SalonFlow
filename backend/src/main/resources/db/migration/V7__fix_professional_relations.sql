DROP TABLE IF EXISTS professionals_services;

ALTER TABLE professional_services
    ALTER COLUMN professional_id SET NOT NULL;

ALTER TABLE professionals ADD COLUMN IF NOT EXISTS phone VARCHAR(20);
ALTER TABLE professionals ADD COLUMN IF NOT EXISTS commission_percentage DOUBLE PRECISION;
ALTER TABLE professionals ADD COLUMN IF NOT EXISTS active BOOLEAN DEFAULT TRUE;