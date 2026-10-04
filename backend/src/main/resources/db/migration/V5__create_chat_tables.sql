CREATE TABLE leads (
         id UUID PRIMARY KEY,
         business_id UUID NOT NULL REFERENCES businesses(id) ON DELETE CASCADE,
         campaign_id UUID NOT NULL REFERENCES campaigns(id) ON DELETE CASCADE,
         session_id UUID NOT NULL UNIQUE,
         score INT DEFAULT 0,
         status VARCHAR(50) DEFAULT 'COLD',
         created_at TIMESTAMP NOT NULL,
         updated_at TIMESTAMP
     );
     CREATE INDEX idx_leads_tenant ON leads(business_id);
     CREATE INDEX idx_leads_session ON leads(session_id);

     CREATE TABLE conversations (
         id UUID PRIMARY KEY,
         business_id UUID NOT NULL REFERENCES businesses(id) ON DELETE CASCADE,
         lead_id UUID NOT NULL REFERENCES leads(id) ON DELETE CASCADE,
         agent_id UUID,
         status VARCHAR(50) DEFAULT 'ACTIVE',
         created_at TIMESTAMP NOT NULL,
         updated_at TIMESTAMP
     );
     CREATE INDEX idx_conversations_tenant ON conversations(business_id);

     CREATE TABLE messages (
         id UUID PRIMARY KEY,
         business_id UUID NOT NULL REFERENCES businesses(id) ON DELETE CASCADE,
         conversation_id UUID NOT NULL REFERENCES conversations(id) ON DELETE CASCADE,
         sender_type VARCHAR(50) NOT NULL,
         content TEXT NOT NULL,
         created_at TIMESTAMP NOT NULL,
         updated_at TIMESTAMP
     );
     CREATE INDEX idx_messages_tenant ON messages(business_id);
     CREATE INDEX idx_messages_conversation ON messages(conversation_id);
