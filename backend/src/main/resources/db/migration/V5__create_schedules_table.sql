CREATE TABLE schedules (
                           id UUID NOT NULL PRIMARY KEY,
                           schedule_time TIMESTAMP NOT NULL,
                           customers_id UUID,
                           professional_id UUID,
                           professional_services_id BIGINT,
                           status VARCHAR(50),
                           CONSTRAINT fk_schedule_customer FOREIGN KEY (customers_id) REFERENCES customers(id),
                           CONSTRAINT fk_schedule_professional FOREIGN KEY (professional_id) REFERENCES professionals(id),
                           CONSTRAINT fk_schedule_service FOREIGN KEY (professional_services_id) REFERENCES professional_services(id)
);