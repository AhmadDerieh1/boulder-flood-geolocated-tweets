import org.mongodb.scala._

object TweetService {
  def main(args: Array[String]): Unit = {
    val collection = MongoConnection.getCollection("tweets")

    val tweetDocument: Document = Document("tweet" -> "This is a sample tweet.", "user" -> "user123", "date" -> "2024-12-31")
    collection.insertOne(tweetDocument).results()

    println("Data successfully inserted into MongoDB!")

    MongoConnection.close()
  }

  implicit class MyObservable[T](observable: Observable[T]) {
    def results(): Seq[T] = observable.toFuture().map(_.toSeq).await
  }
}
