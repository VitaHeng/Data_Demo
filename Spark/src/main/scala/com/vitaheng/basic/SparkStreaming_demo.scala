package com.vitaheng.basic

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStreaming_demo {
  def main(args: Array[String]): Unit = {
//    val conf = new SparkConf().setAppName("sparkStreaming_cluster")      //spark-submit提交
    val conf = new SparkConf().setMaster("local[4]").setAppName("sparkStreaming_local")      //本地模式
    val ssc = new StreamingContext(conf,Seconds(5))

    val lines = ssc.socketTextStream("hadoop102",8888)
    val wordCounts = lines.flatMap(_.split(" ")).map((_,1)).reduceByKey(_ + _)
    wordCounts.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
