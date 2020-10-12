prompt PL/SQL Developer import file
prompt Created on 2017年12月5日 by liuburu
set feedback off
set define off
prompt Creating BONUS...
create table BONUS
(
  ENAME VARCHAR2(10),
  JOB   VARCHAR2(9),
  SAL   NUMBER,
  COMM  NUMBER
)
;

prompt Creating DEPT...
create table DEPT
(
  DEPTNO NUMBER(2) not null,
  DNAME  VARCHAR2(14),
  LOC    VARCHAR2(13)
)
;
alter table DEPT
  add constraint PK_DEPT primary key (DEPTNO);

prompt Creating EMP...
create table EMP
(
  EMPNO    NUMBER(4) not null,
  ENAME    VARCHAR2(10),
  JOB      VARCHAR2(9),
  MGR      NUMBER(4),
  HIREDATE DATE,
  SAL      NUMBER(7,2),
  COMM     NUMBER(7,2),
  DEPTNO   NUMBER(2)
)
;
alter table EMP
  add constraint PK_EMP primary key (EMPNO);
alter table EMP
  add constraint FK_DEPTNO foreign key (DEPTNO)
  references DEPT (DEPTNO);

prompt Creating SALGRADE...
create table SALGRADE
(
  GRADE NUMBER,
  LOSAL NUMBER,
  HISAL NUMBER
)
;

prompt Loading BONUS...
prompt Table is empty
prompt Loading DEPT...
insert into DEPT (DEPTNO, DNAME, LOC)
values (10, 'ACCOUNTING', 'NEW YORK');
insert into DEPT (DEPTNO, DNAME, LOC)
values (20, 'RESEARCH', 'DALLAS');
insert into DEPT (DEPTNO, DNAME, LOC)
values (30, 'SALES', 'CHICAGO');
insert into DEPT (DEPTNO, DNAME, LOC)
values (40, 'OPERATIONS', 'BOSTON');
commit;
prompt 4 records loaded
prompt Loading EMP...
insert into EMP (EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO)
values (7777, '卡卡罗特', '程序员', 7902, to_date('17-12-1980', 'dd-mm-yyyy'), null, null, 20);
insert into EMP (EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO)
values (7369, 'KiWiPeach', 'CLERK', 7902, to_date('17-12-1980', 'dd-mm-yyyy'), null, null, 20);
insert into EMP (EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO)
values (7499, 'ALLEN', 'SALESMAN', 7698, to_date('20-02-1981', 'dd-mm-yyyy'), 1600, 300, 30);
insert into EMP (EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO)
values (7521, 'WARD', 'SALESMAN', 7698, to_date('22-02-1981', 'dd-mm-yyyy'), 1250, 500, 30);
insert into EMP (EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO)
values (7566, 'JONES', 'MANAGER', 7839, to_date('02-04-1981', 'dd-mm-yyyy'), 2975, null, 20);
insert into EMP (EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO)
values (7654, 'MARTIN', 'SALESMAN', 7698, to_date('28-09-1981', 'dd-mm-yyyy'), 1250, 1400, 30);
insert into EMP (EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO)
values (7698, 'BLAKE', 'MANAGER', 7839, to_date('01-05-1981', 'dd-mm-yyyy'), 2850, null, 30);
insert into EMP (EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO)
values (7782, 'CLARK', 'MANAGER', 7839, to_date('09-06-1981', 'dd-mm-yyyy'), 2450, null, 10);
insert into EMP (EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO)
values (7839, 'KING', 'PRESIDENT', null, to_date('17-11-1981', 'dd-mm-yyyy'), 5000, null, 10);
insert into EMP (EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO)
values (7844, 'TURNER', 'SALESMAN', 7698, to_date('08-09-1981', 'dd-mm-yyyy'), 1500, 0, 30);
insert into EMP (EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO)
values (7900, 'JAMES', 'CLERK', 7698, to_date('03-12-1981', 'dd-mm-yyyy'), 950, null, 30);
insert into EMP (EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO)
values (7902, 'FORD', 'ANALYST', 7566, to_date('03-12-1981', 'dd-mm-yyyy'), 3000, null, 20);
insert into EMP (EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO)
values (7934, 'MILLER', 'CLERK', 7782, to_date('23-01-1982', 'dd-mm-yyyy'), 1300, null, 10);
commit;
prompt 13 records loaded
prompt Loading SALGRADE...
insert into SALGRADE (GRADE, LOSAL, HISAL)
values (1, 700, 1200);
insert into SALGRADE (GRADE, LOSAL, HISAL)
values (2, 1201, 1400);
insert into SALGRADE (GRADE, LOSAL, HISAL)
values (3, 1401, 2000);
insert into SALGRADE (GRADE, LOSAL, HISAL)
values (4, 2001, 3000);
insert into SALGRADE (GRADE, LOSAL, HISAL)
values (5, 3001, 9999);
commit;
prompt 5 records loaded
set feedback on
set define on
prompt Done.
