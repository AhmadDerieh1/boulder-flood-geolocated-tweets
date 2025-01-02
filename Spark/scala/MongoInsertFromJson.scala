import org.mongodb.scala._
import org.mongodb.scala.bson.collection.immutable.Document
import scala.io.Source
import play.api.libs.json._

object MongoInsertFromJson {

  def main(args: Array[String]): Unit = {
    val mongoClient: MongoClient = MongoClient("mongodb://localhost:27017")
    val database: MongoDatabase = mongoClient.getDatabase("tweets_db")
    val collection: MongoCollection[Document] = database.getCollection("tweets_collection")

    val filename = "src/main/boulder_flood_geolocated_tweets.json"
    val fileContent = Source.fromFile(filename).getLines().mkString
    val json = Json.parse(fileContent)

    json.as[JsArray].value.foreach { tweetJson =>
      val tweet = Document(
        "id" -> (tweetJson \ "id").as[String],
        "text" -> (tweetJson \ "text").as[String],
        "created_at" -> (tweetJson \ "created_at").as[String],
        "hashtags" -> (tweetJson \ "hashtags").as[Seq[String]],
        "user" -> Document(
          "user_id" -> (tweetJson \ "user" \ "id").as[String],
          "username" -> (tweetJson \ "user" \ "username").as[String]
        ),
        "place" -> Document(
          "lat" -> (tweetJson \ "place" \ "lat").as[Double],
          "lon" -> (tweetJson \ "place" \ "lon").as[Double]
        ),
        "sentiment" -> (tweetJson \ "sentiment").as[Double]
      )

      collection.insertOne(tweet).results()
    }

    println("Tweets inserted successfully!")
  }
}
