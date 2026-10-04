CREATE TABLE users (
         id UUID PRIMARY KEY,
         account_id UUID REFERENCES accounts(id) ON DELETE CASCADE,
         business_id UUID REFERENCES businesses(id) ON DELETE CASCADE,
         email VARCHAR(255) NOT NULL UNIQUE,
         password VARCHAR(255) NOT NULL,
         role VARCHAR(50) NOT NULL,
         created_at TIMESTAMP NOT NULL,
         updated_at TIMESTAMP
     );

     CREATE INDEX idx_users_account ON users(account_id);
     CREATE INDEX idx_users_business ON users(business_id);
