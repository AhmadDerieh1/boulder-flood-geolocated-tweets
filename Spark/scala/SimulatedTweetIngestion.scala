import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import scala.io.Source
import java.util.Properties

object SimulatedTweetIngestion {
  def main(args: Array[String]): Unit = {

    val kafkaTopic = "tweets-topic"
    val kafkaBrokers = "localhost:9092"


    val props = new Properties()
    props.put("bootstrap.servers", kafkaBrokers)
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)


    val tweetFilePath = "src/main/boulder_flood_geolocated_tweets.json"
    val tweets = Source.fromFile(tweetFilePath).getLines()

    println(s"Streaming tweets from $tweetFilePath to Kafka topic: $kafkaTopic")


    try {
      for (tweet <- tweets) {

        val record = new ProducerRecord[String, String](kafkaTopic, tweet)
        producer.send(record)
        println(s"Sent: $tweet")


        Thread.sleep(1000)
      }
    } catch {
      case e: Exception => println(s"Error while streaming tweets: ${e.getMessage}")
    } finally {
      producer.close()
      println("Tweet streaming completed.")
    }
  }
}
