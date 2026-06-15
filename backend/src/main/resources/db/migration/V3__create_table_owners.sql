CREATE TABLE owners (
                        id         UUID         NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
                        tenant_id  UUID         NOT NULL REFERENCES tenants(id),
                        name       VARCHAR(255) NOT NULL,
                        email      VARCHAR(255) NOT NULL,
                        password   VARCHAR(255) NOT NULL,
                        created_at TIMESTAMP    NOT NULL DEFAULT NOW(),
                        updated_at TIMESTAMP    NOT NULL DEFAULT NOW(),
                        CONSTRAINT uq_owner_email UNIQUE (email)
);