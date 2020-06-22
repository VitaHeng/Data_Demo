package com.vitaheng

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.sql.{Dataset, SparkSession}

object StaticRecommend extends App {

  case class MongoConfig(uri: String, db: String)
  case class Rating(uid:Int,mid:Int,score:Double,timestamp:Int)
  case class Movie(mid:Int,name:String,desc:String,timelong:String,issue:String,shoot:String,language:String,genres:String,actors:String,directors:String)
  case class Recommend(rid: Int, r: Double)
  case class GenresRecommend(genres: String, recommends: List[Recommend])

  private val spark: SparkSession = SparkSession.builder().appName("StaticRecommend").master("local[4]").getOrCreate()
  import spark.implicits._
  implicit val mongoConfig = new MongoConfig("mongodb://hadoop102:27017/recommend","recommend")

  private val ratings: Dataset[Rating] = spark.read.option("uri",mongoConfig.uri).option("collection","rating").format("com.mongodb.spark.sql").load().as[Rating].cache()
  private val movies: Dataset[Movie] = spark.read.option("uri",mongoConfig.uri).option("collection","movie").format("com.mongodb.spark.sql").load().as[Movie].cache()

  ratings.createOrReplaceTempView("ratings")

//  rateMore(spark)
//  rateMoreRecently(spark)
//  genresHot(spark)(movies)

  def rateMore(spark: SparkSession)(implicit mongoConfig: MongoConfig) = {
    val ratingMoreDF = spark.sql(
      """
        |select mid
        |       ,count(mid) as cnts
        |  from ratings
        | group by mid
        | order by cnts desc
      """.stripMargin)
    ratingMoreDF.write.option("uri",mongoConfig.uri).option("collection","ratingMoreMovies").mode("overwrite").format("com.mongodb.spark.sql").save()
  }

  def rateMoreRecently(spark: SparkSession)(implicit mongoConfig: MongoConfig) = {
    spark.udf.register("formatDate",(time:Long) => new SimpleDateFormat("yyyyMM").format(new Date(time*1000L)).toLong)
    val readMoreRecentlyDF = spark.sql(
      """
        select yearMonth
               ,mid
               ,count(mid) as cnts
          from
             (
               select mid
                      ,uid
                      ,score
                      ,formatDate(timestamp) as yearMonth
                 from ratings
             ) a
         group by yearMonth
                  ,mid
         order by yearMonth desc,cnts desc
      """.stripMargin)
    readMoreRecentlyDF.write.option("uri",mongoConfig.uri).option("collection","readMoreRecentlyMovies").mode("overwrite").format("com.mongodb.spark.sql").save()
  }

    def genresHot(spark: SparkSession)(movies: Dataset[Movie])(implicit mongoConfig: MongoConfig) = {
      val genres = List("Action","Adventure","Animation","Comedy","Ccrime","Documentary","Drama","Family","Fantasy","Foreign","History","Horror","Music","Mystery"
        ,"Romance","Science","Tv","Thriller","War","Western")
      val avgMovieScoreDF = spark.sql(
        """
          select mid
                 ,avg(score) as avg_score
            from ratings
           group by mid
        """.stripMargin).cache()
      val movieWithScoreDF = movies.join(avgMovieScoreDF,Seq("mid","mid")).select("mid","avg_score","genres").cache()
      val genresRDD = spark.sparkContext.makeRDD(genres)

      import spark.implicits._

      val genresTopMoviesDF = genresRDD.cartesian(movieWithScoreDF.rdd).filter {
        case (genres, row) => {
          row.getAs[String]("genres").toLowerCase().contains(genres.toLowerCase())
        }
      }.map {
        case (genres, row) => {
          (genres, (row.getAs[Int]("mid"), row.getAs[Double]("avg_score")))
        }
      }.groupByKey().map {
        case (genres, items) => {
          GenresRecommend(genres, items.toList.sortWith(_._2 > _._2).slice(0, 10).map(
            x => Recommend(x._1, x._2)
          ))
        }
      }.toDF()

      avgMovieScoreDF.write.option("uri",mongoConfig.uri).option("collection","avgMovieScore").mode("overwrite").format("com.mongodb.spark.sql").save()
      genresTopMoviesDF.write.option("uri",mongoConfig.uri).option("collection","genresTopMovies").mode("overwrite").format("com.mongodb.spark.sql").save()
    }
}

