INSERT INTO TBL_USER(IS_LOCK,PASSWORD,FAILURE_LOGIN_TEMP,FIRST_LOGIN,USER_NAME)
VALUES
(false ,'$2a$04$ELMHx7R3uRBIRn5D.AZ1JOHXDztkVVYAp8EW58SgQgGgkMmMXpJ6i',0,true,1234567891234567),
(false ,'$2a$04$ELMHx7R3uRBIRn5D.AZ1JOHXDztkVVYAp8EW58SgQgGgkMmMXpJ6i' ,0,false,1234567897654321),
(false ,'$2a$04$ELMHx7R3uRBIRn5D.AZ1JOHXDztkVVYAp8EW58SgQgGgkMmMXpJ6i' ,0,true,1234567891111111);


INSERT INTO TBL_HINT_QUESTION(CONTENT)
VALUES
('What is your nick name?'),
('What is your first school name?'),
('What is your first pet name?');