ALTER TABLE schedules DROP CONSTRAINT IF EXISTS fk_schedule_service;
ALTER TABLE schedules DROP COLUMN IF EXISTS professional_services_id;