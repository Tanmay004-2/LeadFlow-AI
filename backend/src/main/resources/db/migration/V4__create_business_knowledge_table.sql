CREATE TABLE business_knowledge (
         id UUID PRIMARY KEY,
         business_id UUID NOT NULL REFERENCES businesses(id) ON DELETE CASCADE,
         type VARCHAR(50) NOT NULL,
         content TEXT NOT NULL,
         is_active BOOLEAN DEFAULT TRUE,





           created_at TIMESTAMP NOT NULL,
           updated_at TIMESTAMP
     );

     CREATE INDEX idx_business_knowledge_tenant ON business_knowledge(business_id);
