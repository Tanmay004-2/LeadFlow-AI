CREATE TABLE campaigns (
         id UUID PRIMARY KEY,
         business_id UUID NOT NULL REFERENCES businesses(id) ON DELETE CASCADE,
         name VARCHAR(255) NOT NULL,
         created_at TIMESTAMP NOT NULL,
         updated_at TIMESTAMP
     );

     CREATE INDEX idx_campaigns_tenant ON campaigns(business_id);
