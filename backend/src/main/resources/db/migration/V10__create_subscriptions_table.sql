CREATE TABLE subscriptions (
         id UUID PRIMARY KEY,
         account_id UUID NOT NULL UNIQUE REFERENCES accounts(id) ON DELETE CASCADE,
         status VARCHAR(50) NOT NULL,
         trial_ends_at TIMESTAMP,
         current_period_end TIMESTAMP,
         created_at TIMESTAMP NOT NULL,
         updated_at TIMESTAMP
     );

     CREATE INDEX idx_subscriptions_account ON subscriptions(account_id);
