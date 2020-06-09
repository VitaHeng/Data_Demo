package com.vitaheng.project

import org.apache.spark.sql.SparkSession

object GoodsTrading {
  case class tbStock(order_num:String,loc_id:String,dayno:String) extends Serializable
  case class tbStockDetail(order_num:String,row_num:Int,item_id:String,number:Int,price:Double,amount:Double) extends Serializable
  case class tbDate(dayno:String,years:Int,theyear:Int,month:Int,day:Int,weekday:Int,week:Int,quarter:Int,period:Int,halfmonth:Int) extends Serializable

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().appName("GoodsTrading").getOrCreate()
    import spark.implicits._

    val tbStockRDD = spark.sparkContext.textFile("/examples/tbStock.txt")
    val tbStockDS = tbStockRDD.map(_.split(",")).map(
      attr => tbStock(attr(0), attr(1), attr(2))
    ).toDS()
    tbStockDS.show()

    val tbStockDetailRDD = spark.sparkContext.textFile("/examples/tbStockDetail.txt")
    val tbStockDetailDS = tbStockDetailRDD.map(_.split(",")).map(
      attr => tbStockDetail(attr(0), attr(1).trim().toInt, attr(2), attr(3).trim().toInt, attr(4).trim().toDouble, attr(5).trim().toDouble)
    ).toDS()
    tbStockDetailDS.show()

    val tbDateRDD = spark.sparkContext.textFile("/examples/tbDate.txt")
    val tbDateDS = tbDateRDD.map(_.split(",")).map(
      attr => tbDate(attr(0), attr(1).trim().toInt, attr(2).trim().toInt, attr(3).trim().toInt, attr(4).trim().toInt, attr(5).trim().toInt, attr(6).trim().toInt, attr(7).trim().toInt, attr(8).trim().toInt, attr(9).trim().toInt)
    ).toDS()
    tbDateDS.show()

    tbStockDS.createOrReplaceTempView("tbStock")
    tbStockDetailDS.createOrReplaceTempView("tbStockDetail")
    tbDateDS.createOrReplaceTempView("tbDate")

    /*需求*/
    /*统计所有订单中每年的销售单数、销售总额*/
    spark.sql(
      """
        |select theyear
        |       ,count(distinct a.order_num) as order_nums
        |       ,sum(amount) as amounts
        |  from tbStock t1
        |  join tbStockDetail t2
        |    on t1.order_num=t2.order_num
        |  join tbDate t3
        |    on t1.dayno=t3.dayno
        | group by theyear
        | order by theyear
      """.stripMargin).show()

    /*统计所有订单中每年最大金额订单的销售额*/
    spark.sql(
      """
        |select theyear
        |       ,max(sum_amount) as sum_amount
        |  from
        |     (
        |       select dayno
        |               ,a.order_num as order_num
        |               ,sum(amount) as sum_amount
        |         from tbStock t1
        |         join tbStockDetail t2
        |           on t1.order_num=t2.order_num
        |        group by dayno
        |                 ,a.order_num
        |      ) a
        |   join tbDate b
        |     on a.dayno=b.dayno
        |  group by theyear
        |  order by theyear desc
      """.stripMargin).show()

    /*统计所有订单中每年最畅销货品(以amount的大小为判断依据)*/
    spark.sql(
      """
        |select b.theyear as theyear
        |       ,item_id
        |       ,max_amount
        |  from
        |     (
                |select theyear
                |       ,item_id
                |       ,sum(amount) as sum_amount
                |  from tbStock t1
                |  join tbStockDetail t2
                |    on t1.order_num=t2.order_num
                |  join tbDate t3
                |    on t1.dayno=t3.dayno
                | group by theyear
                |          ,item_id
        |     ) a
        |  join
        |     (
        |       select theyear
        |              ,max(sum_amount) as max_amount
        |         from
        |            (
                        |select theyear
                        |       ,item_id
                        |       ,sum(amount) as sum_amount
                        |  from tbStock t1
                        |  join tbStockDetail t2
                        |    on t1.order_num=t2.order_num
                        |  join tbDate t3
                        |    on t1.dayno=t3.dayno
                        | group by theyear
                        |          ,item_id
        |            ) t
        |        group by theyear
        |     ) b
        |    on a.theyear=b.theyear
        |   and a.sum_amount=b.max_amount
        | order by b.theyear
      """.stripMargin).show()

  }
}
