package com.vitaheng.basic

import org.apache.spark.{SparkConf, SparkContext}

object SparkRDD_demo {
  def main(args: Array[String]): Unit = {
//    val conf = new SparkConf().setAppName("wc_cluster")   //spark-submit提交
    val conf = new SparkConf().setMaster("local[4]").setAppName("wc_local").set("spark.driver.bindAddress","127.0.0.1")     //本地模式
    val sc = new SparkContext(conf)
    sc.textFile(args(0)).flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_,1).sortBy(_._2,false).saveAsTextFile(args(1))
    sc.stop()
  }
}
