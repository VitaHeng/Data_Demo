package com.vitaheng.extend

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object WordCount_total {

  /*
  * newValues: 表示当前批次汇总成的(word,1)中相同单词的所有的1
  * runningCount: 历史的所有相同Key的value总和
  * */
  def updateFunction(newValues:Seq[Int],runningCount:Option[Int]): Option[Int] = {
    val newCount = runningCount.getOrElse(0)+newValues.sum
    Some(newCount)
  }

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[4]").setAppName("WordCount_total")
    val ssc = new StreamingContext(conf,Seconds(5))
    ssc.checkpoint("./resources")

    val lines = ssc.socketTextStream("hadoop102",8888)
    val wordCounts = lines.flatMap(_.split(" ")).map((_,1)).updateStateByKey(updateFunction)
    wordCounts.print()

    ssc.start()
    ssc.awaitTermination()

  }

}
