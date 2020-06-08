package com.vitaheng

import org.apache.spark.{SparkConf, SparkContext}

object WordCount_remote {

  /*远程调试报错,未找到原因*/
  def main(args: Array[String]){
    val conf = new SparkConf()
      .setMaster("spark://hadoop102:7077")
      .setAppName("wc_remote")
      .setJars(List("E:\\Demo\\Data_Demo\\Spark\\target\\Spark-1.0-SNAPSHOT-jar-with-dependencies.jar"))
      .setIfMissing("spark.driver.host","192.168.213.128")
      .set("spark.driver.bindAddress","127.0.0.1")
    val sc = new SparkContext(conf)

    sc.textFile("hdfs://hadoop102:9000/RELEASE").flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_,1).sortBy(_._2,false).saveAsTextFile("E:\\study\\out_remote")

    sc.stop()

  }

}
