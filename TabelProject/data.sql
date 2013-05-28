INSERT INTO DDT_EMP
    VALUES (1, 'Иванов', 'Иван', 'Иванович', DATE '2000-01-20', null);

INSERT INTO DDT_EMP
  VALUES (2, 'Петров', 'Петр', 'Петрович', DATE '2002-02-22', null);
INSERT INTO DDT_EMP
  VALUES (3, 'Сидоров', 'Сидор', 'Сидорович', DATE '2004-04-24', null);
INSERT INTO DDT_EMP
  VALUES (4, 'Васильев', 'Василий', 'Васильевич', DATE '2006-06-26', null);

/*
          О Т П У С К А

 */

INSERT INTO DDT_LEAVE
  VALUES (1, 1, 'ОТП',DATE '2013-02-22', DATE '2013-03-08');
INSERT INTO DDT_LEAVE
  VALUES (2,2, 'ОТП',DATE '2013-05-06', DATE '2013-05-08');

/*
   Б О Л Ь Н И Ч Н Ы Е I L L
  */
INSERT INTO DDT_ILL
  VALUES (1, 1,'БЛН' ,DATE '2013-05-10', DATE '2013-05-12');
INSERT INTO DDT_ILL
  VALUES (1,3, 'БЛН' ,DATE '2013-04-06', DATE '2013-04-10');
INSERT INTO DDT_ILL
  VALUES (1,4, 'БЛН' ,DATE '2013-03-20', DATE '2013-03-25');

/*
  должности
  */
INSERT INTO DDT_APPOINT
  VALUES (1, 'педагог-организатор','пед.-орг.');
INSERT INTO DDT_APPOINT
  VALUES (2, 'педагог доп.образования','пдо');
INSERT INTO DDT_APPOINT
  VALUES (3, 'руководитель','рук.стр.');
