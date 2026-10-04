CREATE TABLE follow_up_tasks (
         id UUID PRIMARY KEY,
         business_id UUID NOT NULL REFERENCES businesses(id) ON DELETE CASCADE,
         conversation_id UUID NOT NULL REFERENCES conversations(id) ON DELETE CASCADE,
         scheduled_for TIMESTAMP NOT NULL,
         status VARCHAR(50) DEFAULT 'PENDING',
         version BIGINT DEFAULT 0,
         created_at TIMESTAMP NOT NULL,
         updated_at TIMESTAMP
     );
     CREATE INDEX idx_follow_up_tasks_status_time ON follow_up_tasks(status, scheduled_for);
     CREATE INDEX idx_follow_up_tasks_tenant ON follow_up_tasks(business_id);
