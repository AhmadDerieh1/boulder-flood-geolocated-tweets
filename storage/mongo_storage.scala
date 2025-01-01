import org.mongodb.scala._
import org.mongodb.scala.model.Indexes
import scala.concurrent.Await
import scala.concurrent.duration._

object MongoStorage {

  val mongoClient: MongoClient = MongoClient("mongodb://localhost:27017")
  val database: MongoDatabase = mongoClient.getDatabase("tweets_db")
  val collection: MongoCollection[Document] = database.getCollection("collection")

  collection.createIndex(Indexes.ascending("hashtags"))
  collection.createIndex(Indexes.ascending("sentiment"))
  collection.createIndex(Indexes.ascending("processed_at"))

  def insertTweet(tweetDoc: Document): Unit = {
    try {
      val insertObservable = collection.insertOne(tweetDoc)
      Await.result(insertObservable.toFuture(), 10.seconds)
      println("Tweet inserted successfully into MongoDB.")
    } catch {
      case e: Exception =>
        println(s"Error while inserting tweet into MongoDB: ${e.getMessage}")
    }
  }

  def storeTweet(
                  _id: String,
                  created_at: String,
                  id: String,
                  text: String,
                  hashtags: Array[String],
                  user: Document,
                  place: String,
                  sentiment: String
                ): Unit = {
    val tweetDoc = Document(
      "_id" -> _id,
      "created_at" -> created_at,
      "id" -> id,
      "text" -> text,
      "hashtags" -> hashtags,
      "user" -> user,
      "place" -> place,
      "sentiment" -> sentiment,
      "processed_at" -> java.time.Instant.now.toString
    )

    insertTweet(tweetDoc)
  }
}
}
