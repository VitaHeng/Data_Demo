package com.vitaheng.basic

import org.apache.spark.sql.SparkSession

object SparkSql_demo {
  def main(args: Array[String]): Unit = {
//    val spark = SparkSession.builder().appName("people_cluster").getOrCreate()       //spark-submit提交
    val spark = SparkSession.builder().master("local[4]").appName("people_local").getOrCreate()      //本地模式
    import spark.implicits._
    val df = spark.read.json("/examples/people.json")    //默认读取HDFS文件
    df.show()
    df.filter($"age">15).show()
    df.createOrReplaceTempView("persons")
    spark.sql("select * from persons where age>15").show()
    spark.stop()
  }
}