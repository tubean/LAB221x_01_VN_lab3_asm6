DROP TABLE IF EXISTS TBL_USER;
DROP TABLE IF EXISTS TBL_HINT_QUESTION;
DROP TABLE IF EXISTS TBL_HINT_QUESTION_CHOICE;

CREATE TABLE TBL_USER (
  ID INT AUTO_INCREMENT PRIMARY KEY ,
  USER_NAME VARCHAR(16) NOT NULL,
  PASSWORD VARCHAR(128) NULL,
  FIRST_LOGIN TINYINT NOT NULL,
  IS_LOCK TINYINT NOT NULL,
  FAILURE_LOGIN_TEMP INT NOT NULl
);

CREATE TABLE TBL_HINT_QUESTION (
                          ID INT AUTO_INCREMENT PRIMARY KEY ,
                          CONTENT VARCHAR(256) NOT NULL,
                          DISPLAY_ORDER INT NULL,
                          IS_DELETED TINYINT NOT NULL
);

CREATE TABLE TBL_HINT_QUESTION_CHOICE (
                                   ID INT AUTO_INCREMENT PRIMARY KEY ,
                                   USER_ID VARCHAR(256) NOT NULL,
                                   QUESTION_ID INT NULL,
                                   ANSWER TINYINT NOT NULL,
                                   foreign key (USER_ID) references TBL_USER(ID),
                                   foreign key (QUESTION_ID) references TBL_HINT_QUESTION(ID)
);

