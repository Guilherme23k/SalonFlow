-- V2__improve_schema.sql
-- Corrige o schema para o padrão definido na arquitetura

-- Índices de performance por tenant
CREATE INDEX IF NOT EXISTS idx_professionals_tenant    ON professionals    (tenant_id);
CREATE INDEX IF NOT EXISTS idx_services_tenant         ON services         (tenant_id);
CREATE INDEX IF NOT EXISTS idx_service_duration_tenant ON service_duration (tenant_id);
CREATE INDEX IF NOT EXISTS idx_service_duration_prof   ON service_duration (professional_id);
CREATE INDEX IF NOT EXISTS idx_blocked_slots_tenant    ON blocked_slots    (tenant_id);
CREATE INDEX IF NOT EXISTS idx_blocked_slots_prof      ON blocked_slots    (professional_id);
CREATE INDEX IF NOT EXISTS idx_blocked_slots_period    ON blocked_slots    (professional_id, start_at, end_at);
CREATE INDEX IF NOT EXISTS idx_customers_tenant        ON customers        (tenant_id);
CREATE INDEX IF NOT EXISTS idx_customers_phone         ON customers        (tenant_id, phone);
CREATE INDEX IF NOT EXISTS idx_appointments_tenant     ON appointments     (tenant_id);
CREATE INDEX IF NOT EXISTS idx_appointments_prof       ON appointments     (professional_id);
CREATE INDEX IF NOT EXISTS idx_appointments_customer   ON appointments     (customer_id);
CREATE INDEX IF NOT EXISTS idx_appointments_scheduled  ON appointments     (professional_id, scheduled_at);

-- Constraints de unicidade
DO $$ BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint WHERE conname = 'uq_customer_tenant_phone'
    ) THEN
ALTER TABLE customers ADD CONSTRAINT uq_customer_tenant_phone UNIQUE (tenant_id, phone);
END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint WHERE conname = 'uq_service_duration'
    ) THEN
ALTER TABLE service_duration ADD CONSTRAINT uq_service_duration UNIQUE (professional_id, service_id);
END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint WHERE conname = 'chk_blocked_slots_period'
    ) THEN
ALTER TABLE blocked_slots ADD CONSTRAINT chk_blocked_slots_period CHECK (end_at > start_at);
END IF;
END $$;

-- FK de tenant em todas as tabelas
DO $$ BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint WHERE conname = 'fk_professionals_tenant'
    ) THEN
ALTER TABLE professionals ADD CONSTRAINT fk_professionals_tenant FOREIGN KEY (tenant_id) REFERENCES tenants(id);
END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint WHERE conname = 'fk_services_tenant'
    ) THEN
ALTER TABLE services ADD CONSTRAINT fk_services_tenant FOREIGN KEY (tenant_id) REFERENCES tenants(id);
END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint WHERE conname = 'fk_service_duration_tenant'
    ) THEN
ALTER TABLE service_duration ADD CONSTRAINT fk_service_duration_tenant FOREIGN KEY (tenant_id) REFERENCES tenants(id);
END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint WHERE conname = 'fk_blocked_slots_tenant'
    ) THEN
ALTER TABLE blocked_slots ADD CONSTRAINT fk_blocked_slots_tenant FOREIGN KEY (tenant_id) REFERENCES tenants(id);
END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint WHERE conname = 'fk_customers_tenant'
    ) THEN
ALTER TABLE customers ADD CONSTRAINT fk_customers_tenant FOREIGN KEY (tenant_id) REFERENCES tenants(id);
END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint WHERE conname = 'fk_appointments_tenant'
    ) THEN
ALTER TABLE appointments ADD CONSTRAINT fk_appointments_tenant FOREIGN KEY (tenant_id) REFERENCES tenants(id);
END IF;
END $$;