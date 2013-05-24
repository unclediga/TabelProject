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

INSERT INTO DDT_EMP
    VALUES (1, '������', '����', '��������', DATE '2000-01-20', null);

INSERT INTO DDT_EMP
  VALUES (2, '������', '����', '��������', DATE '2002-02-22', null);
INSERT INTO DDT_EMP
  VALUES (3, '�������', '�����', '���������', DATE '2004-04-24', null);
INSERT INTO DDT_EMP
  VALUES (4, '��������', '�������', '����������', DATE '2006-06-26', null);

/*
          � � � � � � �

 */

CREATE TABLE DDT_LEAVE(
  ID INTEGER PRIMARY KEY ,
  EMP_ID INTEGER,
  D_FROM DATE,
  D_TO DATE
);

ALTER TABLE DDT_LEAVE ADD CONSTRAINT DDT_LEAVE_EMP_FK FOREIGN KEY(EMP_ID) REFERENCES DDT_EMP(ID);

INSERT INTO DDT_LEAVE
  VALUES (1, 1, DATE '2013-02-22', DATE '2013-03-08');
INSERT INTO DDT_LEAVE
  VALUES (2,2, DATE '2013-05-06', DATE '2013-05-08');

/*
   � � � � � � � � � � I L L
  */
CREATE TABLE DDT_ILL(
  ID INTEGER PRIMARY KEY ,
  EMP_ID INTEGER,
  D_FROM DATE,
  D_TO DATE
);

ALTER TABLE DDT_ILL ADD CONSTRAINT DDT_ILL_EMP_FK FOREIGN KEY(EMP_ID) REFERENCES DDT_EMP(ID);

INSERT INTO DDT_ILL
  VALUES (1, 1, DATE '2013-05-10', DATE '2013-05-12');
INSERT INTO DDT_ILL
  VALUES (1,3, DATE '2013-04-06', DATE '2013-04-10');
INSERT INTO DDT_ILL
  VALUES (1,4, DATE '2013-03-20', DATE '2013-03-25');
