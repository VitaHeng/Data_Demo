package com.vitaheng

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStreaming_demo {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("sparkStreaming_demo")
    val ssc = new StreamingContext(conf,Seconds(1))

    val lines = ssc.socketTextStream("hadoop102",8888)
    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(
      word => (word, 1)
    ).reduceByKey(_ + _)
    wordCounts.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
