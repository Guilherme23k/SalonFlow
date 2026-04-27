-- ==============================================================================
-- EXTENSIONS
-- ==============================================================================
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- ==============================================================================
-- TENANTS
-- ==============================================================================
CREATE TABLE tenants (
                         id              UUID        NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
                         name            VARCHAR(255) NOT NULL,
                         slug            VARCHAR(100) NOT NULL UNIQUE,
                         opening_time    TIME        NOT NULL DEFAULT '09:00',
                         closing_time    TIME        NOT NULL DEFAULT '19:00',
                         active          BOOLEAN     NOT NULL DEFAULT TRUE,
                         created_at      TIMESTAMP   NOT NULL DEFAULT NOW(),
                         updated_at      TIMESTAMP   NOT NULL DEFAULT NOW()
);

-- ==============================================================================
-- PROFESSIONALS
-- ==============================================================================
CREATE TABLE professionals (
                               id                    UUID          NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
                               tenant_id             UUID          NOT NULL REFERENCES tenants(id),
                               name                  VARCHAR(255)  NOT NULL,
                               phone                 VARCHAR(20),
                               commission_percentage DOUBLE PRECISION,
                               active                BOOLEAN       NOT NULL DEFAULT TRUE,
                               created_at            TIMESTAMP     NOT NULL DEFAULT NOW(),
                               updated_at            TIMESTAMP     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_professionals_tenant ON professionals (tenant_id);

-- ==============================================================================
-- SERVICES
-- ==============================================================================
CREATE TABLE services (
                          id          UUID          NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
                          tenant_id   UUID          NOT NULL REFERENCES tenants(id),
                          name        VARCHAR(255)  NOT NULL,
                          description TEXT,
                          active      BOOLEAN       NOT NULL DEFAULT TRUE,
                          created_at  TIMESTAMP     NOT NULL DEFAULT NOW(),
                          updated_at  TIMESTAMP     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_services_tenant ON services (tenant_id);

-- ==============================================================================
-- SERVICE DURATION
-- ==============================================================================
CREATE TABLE service_duration (
                                  id                UUID      NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
                                  tenant_id         UUID      NOT NULL REFERENCES tenants(id),
                                  professional_id   UUID      NOT NULL REFERENCES professionals(id),
                                  service_id        UUID      NOT NULL REFERENCES services(id),
                                  duration_minutes  INTEGER   NOT NULL,
                                  price             NUMERIC(10, 2) NOT NULL,
                                  created_at        TIMESTAMP NOT NULL DEFAULT NOW(),
                                  updated_at        TIMESTAMP NOT NULL DEFAULT NOW(),

                                  CONSTRAINT uq_service_duration UNIQUE (professional_id, service_id)
);

CREATE INDEX idx_service_duration_tenant      ON service_duration (tenant_id);
CREATE INDEX idx_service_duration_professional ON service_duration (professional_id);

-- ==============================================================================
-- BLOCKED SLOTS
-- ==============================================================================
CREATE TABLE blocked_slots (
                               id               UUID          NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
                               tenant_id        UUID          NOT NULL REFERENCES tenants(id),
                               professional_id  UUID          NOT NULL REFERENCES professionals(id),
                               start_at         TIMESTAMP     NOT NULL,
                               end_at           TIMESTAMP     NOT NULL,
                               reason           VARCHAR(255),
                               created_at       TIMESTAMP     NOT NULL DEFAULT NOW(),
                               updated_at       TIMESTAMP     NOT NULL DEFAULT NOW(),

                               CONSTRAINT chk_blocked_slots_period CHECK (end_at > start_at)
);

CREATE INDEX idx_blocked_slots_tenant       ON blocked_slots (tenant_id);
CREATE INDEX idx_blocked_slots_professional ON blocked_slots (professional_id);
CREATE INDEX idx_blocked_slots_period       ON blocked_slots (professional_id, start_at, end_at);

-- ==============================================================================
-- CUSTOMERS
-- ==============================================================================
CREATE TABLE customers (
                           id          UUID          NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
                           tenant_id   UUID          NOT NULL REFERENCES tenants(id),
                           name        VARCHAR(255)  NOT NULL,
                           phone       VARCHAR(20)   NOT NULL,
                           created_at  TIMESTAMP     NOT NULL DEFAULT NOW(),
                           updated_at  TIMESTAMP     NOT NULL DEFAULT NOW(),

                           CONSTRAINT uq_customer_tenant_phone UNIQUE (tenant_id, phone)
);

CREATE INDEX idx_customers_tenant ON customers (tenant_id);
CREATE INDEX idx_customers_phone  ON customers (tenant_id, phone);

-- ==============================================================================
-- APPOINTMENTS
-- ==============================================================================
CREATE TYPE appointment_status AS ENUM ('CONFIRMED', 'CANCELED');

CREATE TABLE appointments (
                              id               UUID                NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
                              tenant_id        UUID                NOT NULL REFERENCES tenants(id),
                              professional_id  UUID                NOT NULL REFERENCES professionals(id),
                              customer_id      UUID                NOT NULL REFERENCES customers(id),
                              service_id       UUID                NOT NULL REFERENCES services(id),
                              scheduled_at     TIMESTAMP           NOT NULL,
                              duration_minutes INTEGER             NOT NULL,
                              price            NUMERIC(10, 2)      NOT NULL,
                              status           appointment_status  NOT NULL DEFAULT 'CONFIRMED',
                              notes            TEXT,
                              created_at       TIMESTAMP           NOT NULL DEFAULT NOW(),
                              updated_at       TIMESTAMP           NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_appointments_tenant       ON appointments (tenant_id);
CREATE INDEX idx_appointments_professional ON appointments (professional_id);
CREATE INDEX idx_appointments_customer     ON appointments (customer_id);
CREATE INDEX idx_appointments_scheduled_at ON appointments (professional_id, scheduled_at);