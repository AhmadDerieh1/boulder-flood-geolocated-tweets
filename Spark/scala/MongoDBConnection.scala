import org.mongodb.scala._

object MongoDBConnection {

  val mongoClient: MongoClient = MongoClient("mongodb://localhost:27017")
  val database: MongoDatabase = mongoClient.getDatabase("tweets_db")

  def getDatabase: MongoDatabase = database
}
