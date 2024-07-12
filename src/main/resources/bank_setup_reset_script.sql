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
	balance double NOT NULL DEFAULT 0,
	primary_user INTEGER NOT null,
	joint_user INTEGER DEFAULT null
);

