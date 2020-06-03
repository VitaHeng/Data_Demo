/*
面试题常见hivesql:

+--------------+-------------+-----------+--+
| dept.deptno  | dept.dname  | dept.loc  |
+--------------+-------------+-----------+--+
| 10           | ACCOUNTING  | NEW YORK  |
| 20           | RESEARCH    | DALLAS    |
| 30           | SALES       | CHICAGO   |
| 40           | OPERATIONS  | BOSTON    |
+--------------+-------------+-----------+--+

+------------+------------+------------+----------+---------------+----------+-----------+-------------+--+
| emp.empno  | emp.ename  |  emp.job   | emp.mgr  | emp.hiredate  | emp.sal  | emp.comm  | emp.deptno  |
+------------+------------+------------+----------+---------------+----------+-----------+-------------+--+
| 7369       | SMITH      | CLERK      | 7902     | 1980-12-17    | 800.0    | NULL      | 20          |
| 7499       | ALLEN      | SALESMAN   | 7698     | 1981-02-20    | 1600.0   | 300.0     | 30          |
| 7521       | WARD       | SALESMAN   | 7698     | 1981-02-22    | 1250.0   | 500.0     | 30          |
| 7566       | JONES      | MANAGER    | 7839     | 1981-04-02    | 2975.0   | NULL      | 20          |
| 7654       | MARTIN     | SALESMAN   | 7698     | 1981-09-28    | 1250.0   | 1400.0    | 30          |
| 7698       | BLAKE      | MANAGER    | 7839     | 1981-05-01    | 2850.0   | NULL      | 30          |
| 7782       | CLARK      | MANAGER    | 7839     | 1981-06-09    | 2450.0   | NULL      | 10          |
| 7788       | SCOTT      | ANALYST    | 7566     | 1987-07-03    | 3000.0   | NULL      | 20          |
| 7839       | KING       | PRESIDENT  | NULL     | 1981-11-17    | 5000.0   | NULL      | 10          |
| 7844       | TURNER     | SALESMAN   | 7698     | 1981-09-08    | 1500.0   | 0.0       | 30          |
| 7876       | ADAMS      | CLERK      | 7788     | 1987-07-13    | 1100.0   | NULL      | 20          |
| 7900       | JAMES      | CLERK      | 7698     | 1981-12-03    | 950.0    | NULL      | 30          |
| 7902       | FORD       | ANALYST    | 7566     | 1981-12-03    | 3000.0   | NULL      | 20          |
| 7934       | MILLER     | CLERK      | 7782     | 1981-01-23    | 1300.0   | NULL      | 10          |
+------------+------------+------------+----------+---------------+----------+-----------+-------------+--+

+-----------------+-----------------+-----------------+--+
| salgrade.grade  | salgrade.losal  | salgrade.hisal  |
+-----------------+-----------------+-----------------+--+
| 1               | 700.0           | 1200.0          |
| 2               | 1201.0          | 1400.0          |
| 3               | 1401.0          | 2000.0          |
| 4               | 2001.0          | 3000.0          |
| 5               | 3001.0          | 9999.0          |
+-----------------+-----------------+-----------------+--+
*/


#1、返回拥有员工的部门名、部门号。
select t2.deptno,dname
  from emp t1
 inner join dept t2
    on t1.deptno=t2.deptno
 group by t2.deptno,dname

#2、工资水平多于smith的员工信息。
select t1.*
  from emp t1
 cross join emp t2
 where t2.ename='SMITH'
   and t1.sal>t2.sal

#3、返回员工和所属经理的姓名。
select t1.ename,t2.ename
  from emp t1
  left outer join emp t2
    on t1.mgr=t2.empno 

#4、返回雇员的雇佣日期早于其经理雇佣日期的员工及其经理姓名。
select t1.ename,t2.ename
  from emp t1
  left outer join emp t2
    on t1.mgr=t2.empno
 where t1.hiredate<t2.hiredate

#5、返回员工姓名及其所在的部门名称。
select ename,dname
  from emp t1
  left outer join dept t2
    on t1.deptno=t2.deptno

#6、返回从事clerk工作的员工姓名和所在部门名称。
select ename,dname
  from emp t1
  left outer join dept t2
    on t1.deptno=t2.deptno
 where job='CLERK'   

#7、返回部门号及其本部门的最低工资。
select deptno,min(sal)
  from emp
 group by deptno

#8、返回销售部(sales)所有员工的姓名。
select ename
  from emp t1
 inner join dept t2
    on t1.deptno=t2.deptno
 where dname='SALES'   

#9、返回工资水平多于平均工资的员工。
select  *
  from
     (
        select *
               ,avg(sal) over() as avg_sal
          from emp
     ) a
 where sal>avg_sal

#10、返回与SCOTT从事相同工作的员工。
select *
  from emp t1
 where job in(select job from emp where ename='SCOTT') 

#11、返回与30部门员工工资水平相同的员工姓名与工资。
select *
  from emp t1
 where sal in(select sal from emp where deptno=30 group by sal)

#12、返回工资高于30部门所有员工工资水平的员工信息。
select t1.*
  from emp t1
 cross join (select max(sal) as max_sal from emp where deptno=30) t2
 where sal>max_sal

#13、返回部门号、部门名、部门所在位置及其每个部门的员工总数。    
select t2.deptno,dname,loc,count(1)
  from emp t1
  left outer join dept t2
    on t1.deptno=t2.deptno
 group by t2.deptno,dname,loc

#14、返回员工的姓名、所在部门名及其工资。
select ename,dname,sal
  from emp t1
  left outer join dept t2
    on t1.deptno=t2.deptno

#15、返回员工的详细信息。(包括部门名)
select t1.*,dname
  from emp t1
  left outer join dept t2
    on t1.deptno=t2.deptno

#16、返回员工工作及其从事此工作的最低工资。
select ename,job,min(sal) over(partition by job) as min_sal
  from emp

#17、返回不同部门经理的部门名、姓名、工作及该部门最低工资。
select dname,ename,job,min(sal) over(partition by dname)
  from emp t1
  left outer join dept t2
    on t1.deptno=t2.deptno
 where job='MANAGER'

#18、计算出员工的年薪，并且以年薪排序。
select ename,sal*12 as ySalary
  from emp
 order by ySalary

#19、返回工资处于第四级别的员工的姓名。
select ename,sal
  from emp t1
 cross join salgrade t2
 where grade=4
   and sal>=losal
   and sal<=hisal

#20、返回工资为二等级的职员名字、部门所在地、工资和二等级的最低工资和最高工资
select ename,loc,sal,losal,hisal
  from emp t1
 cross join salgrade t2
  left outer join dept t3
    on t1.deptno=t3.deptno
 where grade=2
   and sal>=losal
   and sal<=hisal

#21、返回工资为二等级的职员员工工资的最低工资和最高工资
select min(sal),max(sal)
  from emp t1
 cross join salgrade t2
 where grade=2
   and sal>=losal
   and sal<=hisal

#22、 工资等级多于smith的员工信息。
select a.*
  from emp a
 cross join
     (
        select hisal
          from emp t1
         cross join salgrade t2
         where ename='SMITH' 
           and sal>=losal
           and sal<=hisal
     )  b
 where sal>hisal