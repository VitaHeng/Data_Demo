package com.vitaheng

import org.apache.spark.{SparkConf, SparkContext}

object WordCount_local {

  def main(args: Array[String]){
    val conf = new SparkConf().setMaster("local[4]").setAppName("wc_local").set("spark.driver.bindAddress","127.0.0.1")
    val sc = new SparkContext(conf)

    sc.textFile("hdfs://hadoop102:9000/RELEASE").flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_,1).sortBy(_._2,false).saveAsTextFile("E:\\study\\out")

    sc.stop()

  }

}
