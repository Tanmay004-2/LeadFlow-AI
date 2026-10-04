CREATE TABLE appointments (
         id UUID PRIMARY KEY,
         business_id UUID NOT NULL REFERENCES businesses(id) ON DELETE CASCADE,
         lead_id UUID NOT NULL REFERENCES leads(id) ON DELETE CASCADE,
         start_time TIMESTAMP NOT NULL,
         end_time TIMESTAMP NOT NULL,
         status VARCHAR(50) DEFAULT 'SCHEDULED',
         created_at TIMESTAMP NOT NULL,
         updated_at TIMESTAMP
     );
     CREATE INDEX idx_appointments_tenant ON appointments(business_id);

     CREATE TABLE assignment_settings (
         id UUID PRIMARY KEY,
         business_id UUID NOT NULL UNIQUE REFERENCES businesses(id) ON DELETE CASCADE,
         strategy VARCHAR(50) DEFAULT 'ROUND_ROBIN',
         last_assigned_agent_id UUID,





           created_at TIMESTAMP NOT NULL,
           updated_at TIMESTAMP
     );
     CREATE INDEX idx_assignment_settings_tenant ON assignment_settings(business_id);
