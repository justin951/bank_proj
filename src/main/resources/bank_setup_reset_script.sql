DROP TABLE IF EXISTS "user";

CREATE TABLE "user"(
	user_id integer PRIMARY KEY AUTOINCREMENT,
	username text,
	password text
);

INSERT INTO "user" values (0, 'adimn1', 1234);

DROP TABLE IF EXISTS "account";

CREATE TABLE "account"(
	account_id integer PRIMARY KEY AUTOINCREMENT,
	account_name text,
	balance DECIMAL(19, 4) NOT NULL DEFAULT 0,
	primary_user INTEGER NOT NULL,
	Foreign KEY (primary_user) REFERENCES "user" (user_id)
);

DROP TABLE IF EXISTS "transaction";

CREATE TABLE "transaction"(
    transaction_id integer PRIMARY KEY AUTOINCREMENT,
    account_id INTEGER NOT NULL,
    transaction_type text not null,
    transaction_amount DECIMAL(19, 4) not null,
    balance DECIMAL(19, 4) not null,
    transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES "account" (account_id) ON DELETE CASCADE
);