CREATE SEQUENCE DDT_ID_SEQ START WITH 1 INCREMENT BY 1;

CREATE TABLE DDT_EMP(
  ID INTEGER PRIMARY KEY ,
  LNAME VARCHAR2(100),
  FNAME VARCHAR2(100),
  MNAME VARCHAR2(100),
  D_HIRE DATE,
  D_FIRE DATE
);

--ALTER TABLE DDT_EMP ADD CONSTRAINT DDT_EMP_PK PRIMARY KEY (ID);
ALTER TABLE DDT_EMP ADD APPOINT VARCHAR2(100);


/*
          О Т П У С К А

 */

CREATE TABLE DDT_LEAVE(
  ID INTEGER PRIMARY KEY ,
  EMP_ID INTEGER,
  TLEAVE_ID VARCHAR2(3),
  D_FROM DATE,
  D_TO DATE
);

ALTER TABLE DDT_LEAVE ADD CONSTRAINT DDT_LEAVE_EMP_FK FOREIGN KEY(EMP_ID) REFERENCES DDT_EMP(ID);

/*
   Б О Л Ь Н И Ч Н Ы Е I L L
  */
CREATE TABLE DDT_ILL(
  ID INTEGER PRIMARY KEY ,
  EMP_ID INTEGER,
  TILL_ID VARCHAR2(3),
  D_FROM DATE,
  D_TO DATE
);

ALTER TABLE DDT_ILL ADD CONSTRAINT DDT_ILL_EMP_FK FOREIGN KEY(EMP_ID) REFERENCES DDT_EMP(ID);


/*
   РАСПИСАНИЕ
*/

CREATE TABLE DDT_SCHEDULE(
  ID INTEGER PRIMARY KEY ,
  EMP_ID INTEGER,
  D_FROM DATE,
  WEEK_DAY INTEGER,
  HOURS1 INTEGER,
  HOURS2 INTEGER,
  HOURS3 INTEGER,
  HOURS4 INTEGER,
  HOURS5 INTEGER,
  HOURS6 INTEGER,
  HOURS7 INTEGER
);

