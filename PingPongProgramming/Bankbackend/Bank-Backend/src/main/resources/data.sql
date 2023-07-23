INSERT INTO "user" VALUES (20000, TRUE, 612345678, FALSE, 1, 'test address', '1998-04-04', '12345678A', 'testuser@solera.com', 'testname', 'testlastname', 'password', 'testusername') , (20000, TRUE, 612345678, FALSE, 2, 'test address 2', '1998-04-04', '12345678B', 'testuser2@solera.com', 'testname2', 'testlastname2', 'password', 'testusername2');
INSERT INTO "bank_account" VALUES (1000,TRUE,1,1,'accountivanucci1'),(2000,TRUE,2,2,'accountralus1');
INSERT INTO "transaction" VALUES (200,1,2,1),(200,2,1,2),(500,3,2,1),(420,4,1,2);
INSERT INTO "commentary" VALUES (1,1,1,'intelligent commentary'),(2,2,2,'not an intelligent commentary'), (3,3,1,'almost an intelligent commentary');
INSERT INTO "user_friends" VALUES (2,1),(1,2);
INSERT INTO "users_likes" VALUES (1,1),(2,2),(3,1),(4,2);