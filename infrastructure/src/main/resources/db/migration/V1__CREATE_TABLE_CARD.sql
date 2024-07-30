CREATE TABLE IF NOT EXISTS card (
  id              CHAR(36)       NOT NULL PRIMARY KEY,
  number          VARCHAR(25)    NOT NULL UNIQUE,
  password        VARCHAR(50)    NOT NULL,
  owner_name      VARCHAR(50),
  cvv             VARCHAR(5),
  expiration_date VARCHAR(5),
  balance         DECIMAL(10, 2) NOT NULL DEFAULT 0,
  created_at      TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at      TIMESTAMP               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
