package com.vitaheng

import java.net.InetAddress

import com.mongodb.casbah.{MongoClient, MongoClientURI}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.transport.client.PreBuiltTransportClient

object DataLoader {

  case class MongoConfig(uri:String,db:String)
  case class Movie(mid:Int,name:String,desc:String,timelong:String,issue:String,shoot:String,language:String,genres:String,actors:String,directors:String)
  case class Rating(uid:Int,mid:Int,score:Double,timestamp:Int)
  case class Tag(uid:Int,mid:Int,tag:String,timestamp:Int)
  case class ESConfig(httpHosts:String,transportHosts:String,index:String,clusterName:String)

  val MOVIE_COLLECTION_NAME = "movie"
  val RATING_COLLECTION_NAME = "rating"
  val TAG_COLLECTION_NAME="tag"
  val ES_MOVIE_TYPE_NAME="movie"

  def storeDataToMongo(moviesDF: DataFrame, ratingsDF: DataFrame, tagsDF: DataFrame): Unit = {
    val mongoConfig = new MongoConfig("mongodb://hadoop102:27017/recommend","recommend")
    val mongoClient = MongoClient(MongoClientURI(mongoConfig.uri))
    mongoClient(mongoConfig.db)(MOVIE_COLLECTION_NAME).dropCollection()
    mongoClient(mongoConfig.db)(RATING_COLLECTION_NAME).dropCollection()
    mongoClient(mongoConfig.db)(TAG_COLLECTION_NAME).dropCollection()

    moviesDF.write.option("uri",mongoConfig.uri).option("collection",MOVIE_COLLECTION_NAME).mode("overwrite").format("com.mongodb.spark.sql").save()
    ratingsDF.write.option("uri",mongoConfig.uri).option("collection",RATING_COLLECTION_NAME).mode("overwrite").format("com.mongodb.spark.sql").save()
    tagsDF.write.option("uri",mongoConfig.uri).option("collection",TAG_COLLECTION_NAME).mode("overwrite").format("com.mongodb.spark.sql").save()

    mongoClient.close()
  }

  def storeDataToES(esMovieDF: DataFrame): Unit = {
    val eSConfig = new ESConfig("hadoop102:9200","hadoop102:9300","recommend","bigdata")
    val settings = Settings.builder().put("cluster.name",eSConfig.clusterName).build()
    val esClient = new PreBuiltTransportClient(settings)
    val host = eSConfig.transportHosts.split(":")(0)
    val port = eSConfig.transportHosts.split(":")(1)
    val index = eSConfig.index
    esClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host),port.toInt))

    if (esClient.admin().indices().exists(new IndicesExistsRequest(index)).actionGet().isExists) {
      esClient.admin().indices().delete(new DeleteIndexRequest(index)).actionGet()
    }
    esClient.admin().indices().create(new CreateIndexRequest(index)).actionGet()

    val movieOptions = Map(
      "es.nodes" -> eSConfig.httpHosts,
      "es.http.timeout" -> "100m",
      "es.mapping.id" -> "mid")

    val movieTypeName = s"$index/$ES_MOVIE_TYPE_NAME"
    esMovieDF.write.options(movieOptions).mode("overwrite").format("org.elasticsearch.spark.sql").save(movieTypeName)

  }

  def main(args: Array[String]): Unit = {

    val DATAFILE_MOVIES = "E:\\Demo\\1\\Data_Demo\\SparkProject\\MovieRecommend\\src\\main\\resources\\data\\movies.csv"
    val DATAFILE_RATINGS = "E:\\Demo\\1\\Data_Demo\\SparkProject\\MovieRecommend\\src\\main\\resources\\data\\ratings.csv"
    val DATAFILE_TAGS = "E:\\Demo\\1\\Data_Demo\\SparkProject\\MovieRecommend\\src\\main\\resources\\data\\tags.csv"

    val spark = SparkSession.builder().master("local[4]").appName("dataloader").getOrCreate()
    import org.apache.spark.sql.functions._
    import spark.implicits._

    val moviesRDD = spark.sparkContext.textFile(DATAFILE_MOVIES)
    val ratingsRDD = spark.sparkContext.textFile(DATAFILE_RATINGS)
    val tagsRDD = spark.sparkContext.textFile(DATAFILE_TAGS)

    val moviesDF = moviesRDD.map(
      lines => {
        val word = lines.split("\\^")
        Movie(word(0).trim().toInt, word(1).trim(), word(2).trim(), word(3).trim(), word(4).trim(), word(5).trim(), word(6).trim(), word(7).trim(), word(8).trim(), word(9).trim())
      }
    ).toDF()

    val ratingsDF = ratingsRDD.map(
      lines => {
        val word = lines.split(",")
        Rating(word(0).trim().toInt, word(1).trim().toInt, word(2).trim().toDouble, word(3).trim().toInt)
      }
    ).toDF()

    val tagsDF = tagsRDD.map(
      lines => {
        val word = lines.split(",")
        Tag(word(0).trim().toInt, word(1).trim().toInt, word(2).trim(), word(3).trim().toInt)
      }
    ).toDF()

    moviesDF.cache()
    tagsDF.cache()

    storeDataToMongo(moviesDF, ratingsDF, tagsDF)

    val tagCollectDF = tagsDF.groupBy($"mid").agg(concat_ws("|",collect_set($"tag")).as("tags"))
    val esMovieDF = moviesDF.join(tagCollectDF,Seq("mid","mid"),"left").select("mid","name","desc","timelong","issue","shoot","language","genres","actors","directors","tags")

    System.setProperty("es.set.netty.runtime.available.processors", "false")
    storeDataToES(esMovieDF)
  }
}
