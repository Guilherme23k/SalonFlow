-- V1__initial_schema.sql
-- Reflete o schema criado pelo Hibernate (ddl-auto: update)
-- Esta migration é apenas documentativa — o schema já existe

CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS tenants (
                                       id           UUID                        NOT NULL PRIMARY KEY,
                                       name         VARCHAR(255)                NOT NULL,
    slug         VARCHAR(255)                NOT NULL,
    active       BOOLEAN                     NOT NULL,
    opening_time TIME                        NOT NULL,
    closing_time TIME                        NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT ukkn82rs0p55luybrg4n7x7di8 UNIQUE (slug)
    );

CREATE TABLE IF NOT EXISTS professionals (
                                             id                    UUID                        NOT NULL PRIMARY KEY,
                                             tenant_id             UUID                        NOT NULL,
                                             name                  VARCHAR(255)                NOT NULL,
    phone                 VARCHAR(255),
    commission_percentage DOUBLE PRECISION,
    active                BOOLEAN                     NOT NULL,
    created_at            TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at            TIMESTAMP WITHOUT TIME ZONE NOT NULL
    );

CREATE TABLE IF NOT EXISTS services (
                                        id          UUID                        NOT NULL PRIMARY KEY,
                                        tenant_id   UUID                        NOT NULL,
                                        name        VARCHAR(255)                NOT NULL,
    description VARCHAR(255),
    active      BOOLEAN                     NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL
    );

CREATE TABLE IF NOT EXISTS service_duration (
                                                id               UUID                        NOT NULL PRIMARY KEY,
                                                tenant_id        UUID                        NOT NULL,
                                                professional_id  UUID                        NOT NULL,
                                                service_id       UUID                        NOT NULL,
                                                duration_minutes INTEGER                     NOT NULL,
                                                price            NUMERIC(10,2)               NOT NULL,
    created_at       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT fk8kmsxe8s8usnhwdlpu1559cub FOREIGN KEY (professional_id) REFERENCES professionals(id),
    CONSTRAINT fkenpogj8jeyj0lude873sebmrp FOREIGN KEY (service_id)      REFERENCES services(id)
    );

CREATE TABLE IF NOT EXISTS blocked_slots (
                                             id              UUID                        NOT NULL PRIMARY KEY,
                                             tenant_id       UUID                        NOT NULL,
                                             professional_id UUID                        NOT NULL,
                                             start_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                             end_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                             reason          VARCHAR(255),
    created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT fkew6n5v417mv60s3wew9qc4p3e FOREIGN KEY (professional_id) REFERENCES professionals(id)
    );

CREATE TABLE IF NOT EXISTS customers (
                                         id         UUID                        NOT NULL PRIMARY KEY,
                                         tenant_id  UUID                        NOT NULL,
                                         name       VARCHAR(255)                NOT NULL,
    phone      VARCHAR(255)                NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
    );

CREATE TABLE IF NOT EXISTS appointments (
                                            id               UUID                        NOT NULL PRIMARY KEY,
                                            tenant_id        UUID                        NOT NULL,
                                            professional_id  UUID                        NOT NULL,
                                            customer_id      UUID                        NOT NULL,
                                            service_id       UUID                        NOT NULL,
                                            scheduled_at     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                            duration_minutes INTEGER                     NOT NULL,
                                            price            NUMERIC(10,2)               NOT NULL,
    status           VARCHAR(50)                 NOT NULL,
    notes            VARCHAR(255),
    created_at       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT fk70r651dhvcob4dm4icn54of0y FOREIGN KEY (professional_id) REFERENCES professionals(id),
    CONSTRAINT fkrlbb09f329sfsmftrh7y0yxtk FOREIGN KEY (customer_id)     REFERENCES customers(id),
    CONSTRAINT fk5iltr7k9pows18hk8nc101vc1 FOREIGN KEY (service_id)      REFERENCES services(id)
    );