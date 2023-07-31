INSERT INTO "user" ("balance", "enabled", "phone_number", "token_expired", "address", "birth_date", "document_id", "email", "first_name", "last_name", "password", "username") VALUES (0,TRUE,0,FALSE,'','','','admin@solera.com','admin','','$2a$10$eqSt5wA0tnkxLRinXWQfX.qrhkpo4Fo4VX9ugytyODRyAYVZebT1i','admin'), (20000, TRUE, 123456789, FALSE, 'test address', '1998-04-04', '12345678A', 'testuser1@solera.com', 'testname', 'testlastname', '$2a$10$pbtlIt.tMbkU5sNM2AAukejb3iMWlCOamb6dEHDF3rWlnCekq3VcO', 'testusername1') , (20000, TRUE, 123456789, FALSE, 'test address 2', '1998-04-04', '12345678B', 'testpremiumuser2@solera.com', 'testname2', 'testlastname2', '$2a$10$ExhhyUHDO2Y/xLmLSSvgD.B61eFyi7fPgp0c8VazvEZmKgHNm42yy', 'testpremiumusername2');
INSERT INTO "bank_account" ("balance", "enabled", "user_id", "name") VALUES (1000,TRUE,1,'bank account test 1'),(2000,TRUE,2,'bank account test 2');
INSERT INTO "transaction" ("balance", "receiver_id", "sender_id") VALUES (200,2,1),(333,1,2),(500,2,1),(420,1,2);
INSERT INTO "commentary" ("transaction_id", "writer_id", "commentary") VALUES (1,2,'intelligent commentary'),(2,1,'not an intelligent commentary'), (3,2,'almost an intelligent commentary');
INSERT INTO "user_friends" ("friends_id", "user_id") VALUES (2,1),(1,2);
INSERT INTO "users_likes" ("transaction_id", "user_id") VALUES (1,1),(2,2),(3,1),(4,2);
INSERT INTO "privilege" ("name") VALUES ('BANK_ACCOUNT_PRIVILEGE'),('TRANSACTION_PRIVILEGE'),('PREMIUM_USER_PRIVILEGE'),('ADMIN_PRIVILEGE');
INSERT INTO "role" ("name") VALUES ('ROLE_ADMIN'),('ROLE_USER'),('ROLE_PREMIUM_USER');
INSERT INTO "roles_privileges" ("privilege_id", "role_id") VALUES (4,1),(1,2),(2,2),(3,3);
INSERT INTO "users_roles" ("role_id", "user_id") VALUES (1,1),(2,2),(3,3);