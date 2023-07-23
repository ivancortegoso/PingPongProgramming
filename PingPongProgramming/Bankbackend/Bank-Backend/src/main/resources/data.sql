INSERT INTO "user" VALUES (20000, TRUE, 123456789, FALSE, 99, 'test address', '1998-04-04', '12345678A', 'testuser@solera.com', 'testname', 'testlastname', '$2a$10$pbtlIt.tMbkU5sNM2AAukejb3iMWlCOamb6dEHDF3rWlnCekq3VcO', 'testusername') , (20000, TRUE, 123456789, FALSE, 100, 'test address 2', '1998-04-04', '12345678B', 'testuser2@solera.com', 'testname2', 'testlastname2', '$2a$10$ExhhyUHDO2Y/xLmLSSvgD.B61eFyi7fPgp0c8VazvEZmKgHNm42yy', 'testusername2');
INSERT INTO "bank_account" VALUES (1000,TRUE,99,99,'accountivanucci1'),(2000,TRUE,100,100,'accountralus1');
INSERT INTO "transaction" VALUES (200,99,100,99),(200,100,99,100),(500,101,100,99),(420,102,99,100);
INSERT INTO "commentary" VALUES (99,99,99,'intelligent commentary'),(100,100,99,'not an intelligent commentary'), (101,101,100,'almost an intelligent commentary');
INSERT INTO "user_friends" VALUES (100,99),(99,100);
INSERT INTO "users_likes" VALUES (99,99),(100,100),(101,99),(102,100);