CREATE TABLE accounts (
         id UUID PRIMARY KEY,
         name VARCHAR(255) NOT NULL,
         created_at TIMESTAMP NOT NULL,
         updated_at TIMESTAMP
     );

     CREATE TABLE businesses (
         id UUID PRIMARY KEY,
         account_id UUID NOT NULL REFERENCES accounts(id) ON DELETE CASCADE,
         name VARCHAR(255) NOT NULL,
         created_at TIMESTAMP NOT NULL,
         updated_at TIMESTAMP
     );

     CREATE INDEX idx_businesses_account ON businesses(account_id);
