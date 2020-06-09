package com.vitaheng

import org.apache.spark.sql.SparkSession

object SparkSql_demo {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName("people_demo").getOrCreate()
    import sparkSession.implicits._
//    val df = sparkSession.read.json("/examples/people.json")    //默认读取HDFS文件
    val df = sparkSession.read.json("file:///opt/study/people.json")    //读取本地文件
    df.show()
    df.filter($"age">15).show()
    df.createOrReplaceTempView("persons")
    sparkSession.sql("select * from persons where age>15").show()
    sparkSession.stop()
  }
}