import org.apache.kafka.clients.consumer.{KafkaConsumer}
import org.mongodb.scala._
import java.util.Properties

object KafkaMongoIntegration {
  val kafkaTopic = "tweets_topic"
  val kafkaGroupId = "tweets_group"
  val kafkaBootstrapServers = "localhost:9092"

  def createKafkaConsumer(): KafkaConsumer[String, String] = {
    val properties = new Properties()
    properties.put("bootstrap.servers", kafkaBootstrapServers)
    properties.put("group.id", kafkaGroupId)
    properties.put("key.deserializer", classOf[StringDeserializer].getName)
    properties.put("value.deserializer", classOf[StringDeserializer].getName)

    new KafkaConsumer[String, String](properties)
  }

  def consumeTweets(): Unit = {
    val consumer = createKafkaConsumer()
    consumer.subscribe(java.util.Collections.singletonList(kafkaTopic))

    while (true) {
      val records = consumer.poll(1000)
      records.forEach(record => {
        storeTweetInMongo(record.value())
      })
    }
  }

  def storeTweetInMongo(tweet: String): Unit = {
    val mongoClient = MongoClient("mongodb://localhost:27017")
    val database = mongoClient.getDatabase("tweets_db")
    val collection = database.getCollection("Collection")

    val tweetDoc = Document("tweet_text" -> tweet)

    collection.insertOne(tweetDoc)
  }
}
