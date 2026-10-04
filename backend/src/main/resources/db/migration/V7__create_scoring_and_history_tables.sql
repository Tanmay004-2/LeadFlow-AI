CREATE TABLE scoring_rules (
         id UUID PRIMARY KEY,
         business_id UUID NOT NULL REFERENCES businesses(id) ON DELETE CASCADE,
         rule_type VARCHAR(50) NOT NULL,






           condition_value VARCHAR(255) NOT NULL,
           score_delta INT NOT NULL,
           created_at TIMESTAMP NOT NULL,
           updated_at TIMESTAMP
     );
     CREATE INDEX idx_scoring_rules_tenant ON scoring_rules(business_id);

     CREATE TABLE lead_score_history (
         id UUID PRIMARY KEY,
         business_id UUID NOT NULL REFERENCES businesses(id) ON DELETE CASCADE,
         lead_id UUID NOT NULL REFERENCES leads(id) ON DELETE CASCADE,
         old_score INT NOT NULL,
         new_score INT NOT NULL,
         reason VARCHAR(255),
         created_at TIMESTAMP NOT NULL,
         updated_at TIMESTAMP
     );
     CREATE INDEX idx_lead_score_history_tenant ON lead_score_history(business_id);
     CREATE INDEX idx_lead_score_history_lead ON lead_score_history(lead_id);
