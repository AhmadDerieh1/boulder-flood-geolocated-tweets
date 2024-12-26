import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import scala.io.Source
import java.util.Properties
import scala.util.matching.Regex
import scala.util.Try

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

    val hashtagPattern: Regex = """#\w+""".r
    var counter = 0
    try {
      for (tweet <- tweets) {
        if (counter >= 5) {
          println("Reached the limit of 5 tweets.")
          sys.exit(0)
        }
        val hashtags = hashtagPattern.findAllIn(tweet).mkString(", ")
        val processedTweet =
          s"""{
                       "original": "$tweet",
                       "hashtags": "$hashtags",

          }"""
        println(s"Processed Tweet: $processedTweet")
        val record = new ProducerRecord[String, String](kafkaTopic, processedTweet)
        producer.send(record)
        println(s"Sent: $tweet")
        counter += 1
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
