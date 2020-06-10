package com.vitaheng.extend

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}

object WordCount_top {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[4]").setAppName("WordCount_top")
    val ssc = new StreamingContext(conf,Seconds(5))

    val lines = ssc.socketTextStream("hadoop102",8888)
    val wordCounts = lines.flatMap(_.split(" ")).map((_,1)).reduceByKeyAndWindow((a:Int,b:Int)=>a+b,Seconds(10),Seconds(5))
    val sortData = wordCounts.transform(rdd => {
      val dataRDD = rdd.sortBy(_._2, false)
      val sortResult = dataRDD.take(2).foreach(println)
      dataRDD
    })
    sortData.print()

    ssc.start()
    ssc.awaitTermination()
  }

}
