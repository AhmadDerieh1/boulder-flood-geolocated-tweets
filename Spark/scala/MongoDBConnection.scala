import org.mongodb.scala._
import scala.io.Source
import org.json4s._
import org.json4s.native.JsonMethods._

object MongoDBConnection {
  def main(args: Array[String]): Unit = {
    val mongoClient: MongoClient = MongoClient("mongodb://localhost:27017")
    val database: MongoDatabase = mongoClient.getDatabase("twitter_stream")
    val collection: MongoCollection[Document] = database.getCollection("tweets")

    val filePath = "path_to_your_file.json"
    val fileContent = Source.fromFile(filePath).getLines().mkString

    val json = parse(fileContent)
    val tweet: Document = Document(
      "text" -> (json \ "text").extract[String],
      "user" -> (json \ "user").extract[String],
      "timestamp" -> (json \ "timestamp").extract[Long],
      "location" -> (json \ "location").extract[String],
      "hashtags" -> (json \ "hashtags").extract[Seq[String]],
      "likes" -> (json \ "likes").extract[Int],
      "retweets" -> (json \ "retweets").extract[Int]
    )

    collection.insertOne(tweet).subscribe((result: Completed) => println(s"Insert successful: $result"))
  }
}
