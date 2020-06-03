/*
工作中常用的hivesql:
*/

/*
1. 按照某一字段去重:  (可以将rand()函数换成具体的列,按照某一列排序取rn=1同理,可用于取相同id其他列的最大最小值记录)
+--------------------------+-------------------------+------------------------------+--------------------------+------------------------+--+
| data_deduplication.name  | data_deduplication.adx  | data_deduplication.train_id  | data_deduplication.cost  | data_deduplication.ts  |
+--------------------------+-------------------------+------------------------------+--------------------------+------------------------+--+
| ck                       | 5                       | 125.168.10.0                 | 33.0                     | 1407234660             |
| ck                       | 5                       | 187.18.99.00                 | 33.32                    | 1407234661             |
| ck                       | 5                       | 125.168.10.0                 | 33.24                    | 1407234661             |
+--------------------------+-------------------------+------------------------------+--------------------------+------------------------+--+
*/
select * 
  from 
     (
        select *
               ,row_number() over(partition by train_id order by rand(12345) asc) as rn 
          from data_deduplication
     ) a 
 where rn=1;

/*
2. 时间处理函数:
    输入日期20190812,返回20190918
*/
select from_unixtime(unix_timestamp(date_add(from_unixtime(unix_timestamp('20190812','yyyyMMdd'),'yyyy-MM-dd'),37),'yyyy-MM-dd'),'yyyyMMdd');
select from_unixtime(unix_timestamp(date_add('2019-08-12',37),'yyyy-MM-dd'),'yyyyMMdd');

/*
3. 开窗函数:
+-----------------------------+-------------------------------+-------------------------------+-----------------------------+--+
| products_window.product_id  | products_window.product_name  | products_window.product_type  | products_window.sale_price  |
+-----------------------------+-------------------------------+-------------------------------+-----------------------------+--+
| 0001                        | T恤衫                           | 衣服                            | 1000                        |
| 0002                        | 打孔器                           | 办公用品                          | 500                         |
| 0003                        | 运动T恤                          | 衣服                            | 4000                        |
| 0004                        | 菜刀                            | 厨房用品                          | 3000                        |
| 0005                        | 高压锅                           | 厨房用品                          | 6800                        |
| 0006                        | 叉子                            | 厨房用品                          | 500                         |
| 0007                        | 擦菜板                           | 厨房用品                          | 880                         |
| 0008                        | 圆珠笔                           | 办公用品                          | 100                         |
+-----------------------------+-------------------------------+-------------------------------+-----------------------------+--+
*/

# a.  统计不同产品类型售价最高的产品
select product_type
       ,product_name
       ,sale_price
  from
     (
        select product_name
               ,product_type
               ,sale_price
               ,max(sale_price) over(partition by product_type) as max_sale_price 
          from products_window
     ) a
where sale_price=max_sale_price;

# b. 对商品价格累积求和 (标准聚合函数(sum/avg/max/min)作为窗口函数配合order by使用可以实现累积计算)
select product_id
       ,product_name
       ,product_type
       ,sale_price
       ,sum(sale_price) over(order by product_id) as current_sum 
  from products_window;

# c. 利用不同排序函数统计所有函数的售价排行
select product_name
       ,product_type
       ,sale_price
       ,rank() over(order by sale_price) as ranking             --1,2,2,4,5,6,7,8
       ,dense_rank() over(order by sale_price) as dense_rank    --1,2,2,3,4,5,6,7
       ,row_number() over(order by sale_price) as row_number    --1,2,3,4,5,6,7,8
from products_window

/*
3. 查找某一字段重复的记录:
+-------------------+--------------------+--+
| join_demo_5.imei  | join_demo_5.model  |
+-------------------+--------------------+--+
| 1                 | a                  |
| 2                 | b                  |
| 2                 | c                  |
| 4                 | d                  |
| 5                 | e                  |
| 6                 | f                  |
+-------------------+--------------------+--+
*/

select * 
  from join_demo_5 
 where imei in(select imei from join_demo_5 group by imei having count(1)>1);

/*
4. 留存转化类问题
+--------------------------+-------------------------+--+
| retention_demo_01.dayno  | retention_demo_01.imei  |
+--------------------------+-------------------------+--+
| 20200210                 | A                       |
| 20200210                 | B                       |
| 20200210                 | C                       |
| 20200210                 | D                       |
| 20200210                 | E                       |
| 20200210                 | F                       |
| 20200211                 | A                       |
| 20200211                 | B                       |
| 20200211                 | C                       |
| 20200211                 | D                       |
| 20200211                 | E                       |
| 20200211                 | F                       |
| 20200212                 | A                       |
| 20200212                 | B                       |
| 20200212                 | C                       |
| 20200212                 | D                       |
| 20200212                 | E                       |
| 20200212                 | F                       |
| 20200213                 | A                       |
| 20200213                 | B                       |
| 20200213                 | C                       |
| 20200213                 | D                       |
| 20200213                 | E                       |
| 20200213                 | F                       |
| 20200214                 | A                       |
| 20200214                 | B                       |
| 20200214                 | C                       |
| 20200214                 | D                       |
| 20200214                 | E                       |
| 20200214                 | F                       |
| 20200215                 | A                       |
| 20200215                 | B                       |
| 20200215                 | C                       |
| 20200215                 | D                       |
| 20200215                 | E                       |
| 20200215                 | F                       |
| 20200216                 | A                       |
| 20200216                 | B                       |
| 20200216                 | C                       |
| 20200216                 | D                       |
| 20200216                 | E                       |
| 20200216                 | F                       |
+--------------------------+-------------------------+--+

+--------------------------+-------------------------+--+
| retention_demo_02.dayno  | retention_demo_02.imei  |
+--------------------------+-------------------------+--+
| 20200210                 | A                       |
| 20200211                 | A                       |
| 20200212                 | A                       |
| 20200213                 | A                       |
| 20200214                 | A                       |
| 20200215                 | A                       |
| 20200216                 | A                       |
| 20200210                 | B                       |
| 20200211                 | B                       |
| 20200212                 | B                       |
| 20200213                 | B                       |
| 20200214                 | B                       |
| 20200215                 | B                       |
| 20200210                 | C                       |
| 20200211                 | C                       |
| 20200212                 | C                       |
| 20200214                 | C                       |
| 20200215                 | C                       |
| 20200216                 | C                       |
| 20200210                 | D                       |
| 20200211                 | D                       |
| 20200212                 | D                       |
| 20200215                 | D                       |
| 20200216                 | D                       |
| 20200210                 | E                       |
| 20200212                 | E                       |
| 20200214                 | E                       |
| 20200210                 | F                       |
| 20200212                 | F                       |
| 20200214                 | F                       |
+--------------------------+-------------------------+--+
*/

# a. 计算时间差:
select datediff(concat(substr(t2.dayno,1,4),'-',substr(t2.dayno,5,2),'-',substr(t2.dayno,7,2)),concat(substr(t1.dayno,1,4),'-',substr(t1.dayno,5,2),'-',substr(t1.dayno,7,2)));

# b. 计算02表中10号、11号的用户近六天的留存率:
select  t1.dayno
        ,count(distinct t1.imei) as new_users
        ,count(distinct case when datediff(concat(substr(t2.dayno,1,4),'-',substr(t2.dayno,5,2),'-',substr(t2.dayno,7,2)),concat(substr(t1.dayno,1,4),'-',substr(t1.dayno,5,2),'-',substr(t1.dayno,7,2)))=1 then t2.imei else null end) as retention_1
        ,count(distinct case when datediff(concat(substr(t2.dayno,1,4),'-',substr(t2.dayno,5,2),'-',substr(t2.dayno,7,2)),concat(substr(t1.dayno,1,4),'-',substr(t1.dayno,5,2),'-',substr(t1.dayno,7,2)))=2 then t2.imei else null end) as retention_2
        ,count(distinct case when datediff(concat(substr(t2.dayno,1,4),'-',substr(t2.dayno,5,2),'-',substr(t2.dayno,7,2)),concat(substr(t1.dayno,1,4),'-',substr(t1.dayno,5,2),'-',substr(t1.dayno,7,2)))=3 then t2.imei else null end) as retention_3
        ,count(distinct case when datediff(concat(substr(t2.dayno,1,4),'-',substr(t2.dayno,5,2),'-',substr(t2.dayno,7,2)),concat(substr(t1.dayno,1,4),'-',substr(t1.dayno,5,2),'-',substr(t1.dayno,7,2)))=4 then t2.imei else null end) as retention_4
        ,count(distinct case when datediff(concat(substr(t2.dayno,1,4),'-',substr(t2.dayno,5,2),'-',substr(t2.dayno,7,2)),concat(substr(t1.dayno,1,4),'-',substr(t1.dayno,5,2),'-',substr(t1.dayno,7,2)))=5 then t2.imei else null end) as retention_5
        ,count(distinct case when datediff(concat(substr(t2.dayno,1,4),'-',substr(t2.dayno,5,2),'-',substr(t2.dayno,7,2)),concat(substr(t1.dayno,1,4),'-',substr(t1.dayno,5,2),'-',substr(t1.dayno,7,2)))=6 then t2.imei else null end) as retention_6
  from
     (
        select dayno
               ,imei
          from retention_demo_02
         where dayno>='20200210' 
           and dayno<='20200211'
     ) t1
  left join
     (
        select dayno,imei
          from retention_demo_02
         where dayno>='20200211' 
           and dayno<='20200216'
         group by dayno,imei        
     ) t2
    on t1.imei=t2.imei 
 group by t1.dayno

# 如果涉及到调度参数,{v_day}代表调度日期,计算当天用户近6天的留存率:
-- Step1: 依据时间差保留每一天的留存用户数
insert overwrite table retention_inner_01 partition(dayno=${v_day})
select  t1.dayno
        ,datediff(concat(substr(${v_day},1,4),'-',substr(${v_day},5,2),'-',substr(${v_day},7,2)),concat(substr(t1.dayno,1,4),'-',substr(t1.dayno,5,2),'-',substr(t1.dayno,7,2))) as day_diff
        ,count(distinct t2.imei) as retention
  from
     (
        select dayno,imei
          from retention_demo_02
         where dayno>=${v_last_6_day}
           and dayno<=${v_day}
         group by dayno,imei  
     ) t1
 inner join
     (
        select dayno,imei
          from retention_demo_02
         where dayno=${v_day}
         group by dayno,imei        
     ) t2
    on t1.imei=t2.imei 
 group by t1.dayno
-- Step2: 从上表中选取自己需要的数据
select  create_dayno
        ,sum(case when day_diff=0 then retention else 0 end) as new_users
        ,sum(case when day_diff=1 then retention else 0 end) as retention_1
        ,sum(case when day_diff=2 then retention else 0 end) as retention_2
        ,sum(case when day_diff=3 then retention else 0 end) as retention_3
        ,sum(case when day_diff=4 then retention else 0 end) as retention_4
        ,sum(case when day_diff=5 then retention else 0 end) as retention_5
        ,sum(case when day_diff=6 then retention else 0 end) as retention_6
  from retention_inner_01
 where create_dayno='20200210'
 group by create_dayno

# c. 计算01表新增用户在02表当天/7天/14天/30天的转化用户数
select  create_dayno
        ,count(distinct case when datediff(concat(substr('${v_day}',1,4),'-',substr('${v_day}',5,2), '-',substr('${v_day}',7,2)),concat(substr(create_dayno,1,4),'-',substr(create_dayno,5,2),'-',substr(create_dayno,7,2)))=0  then a.imei else null end) as new_users
        ,count(distinct case when dayno>=create_dayno and datediff(concat(substr('${v_day}',1,4),'-',substr('${v_day}',5,2), '-',substr('${v_day}',7,2)),concat(substr(create_dayno,1,4),'-',substr(create_dayno,5,2),'-',substr(create_dayno,7,2)))=0  then b.imei else null end) as 1_conversion_users
        ,count(distinct case when dayno>=create_dayno and datediff(concat(substr('${v_day}',1,4),'-',substr('${v_day}',5,2), '-',substr('${v_day}',7,2)),concat(substr(create_dayno,1,4),'-',substr(create_dayno,5,2),'-',substr(create_dayno,7,2)))=6  then b.imei else null end) as 7_conversion_users
        ,count(distinct case when dayno>=create_dayno and datediff(concat(substr('${v_day}',1,4),'-',substr('${v_day}',5,2), '-',substr('${v_day}',7,2)),concat(substr(create_dayno,1,4),'-',substr(create_dayno,5,2),'-',substr(create_dayno,7,2)))=13 then b.imei else null end) as 14_conversion_users
        ,count(distinct case when dayno>=create_dayno and datediff(concat(substr('${v_day}',1,4),'-',substr('${v_day}',5,2), '-',substr('${v_day}',7,2)),concat(substr(create_dayno,1,4),'-',substr(create_dayno,5,2),'-',substr(create_dayno,7,2)))=29 then b.imei else null end) as 30_conversion_users
  from
     (
        select dayno as create_dayno
               ,imei
          from retention_demo_01
         where dayno in('${v_day}','${v_last_6_day}','${v_last_13_day}','${v_last_29_day}')
     ) a
  left outer join
     (
        select dayno,imei
          from retention_demo_02
         where dayno>=${v_last_29_day}
           and dayno<=${v_day}
     ) b
    on a.imei=b.imei 
 group by create_dayno