package com.vitaheng

import org.apache.spark.{SparkConf, SparkContext}
import org.slf4j.{Logger, LoggerFactory}

object WordCount {
  private val logger: Logger = LoggerFactory.getLogger(WordCount.getClass)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("WC")
    val sc = new SparkContext(conf)

    sc.textFile(args(0)).flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_,1).sortBy(_._2,false).saveAsTextFile(args(1))

    logger.info("complete")
    sc.stop()

  }




}
