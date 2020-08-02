DROP TABLE IF EXISTS bank_account;

DROP SEQUENCE IF EXISTS bank_account_id_sequence;

CREATE SEQUENCE bank_account_id_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE bank_account (
  account_number BIGINT DEFAULT bank_account_id_sequence.nextval PRIMARY KEY,
  version INT NOT NULL DEFAULT 0,
  start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP() NOT NULL,
  close_date TIMESTAMP DEFAULT NULL,
  balance DECIMAL NOT NULL
);
