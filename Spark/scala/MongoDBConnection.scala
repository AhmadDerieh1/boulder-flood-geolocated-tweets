import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import scala.io.Source
import java.util.Properties
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}
import scala.collection.JavaConverters._

object SimulatedTweetIngestion {
  def main(args: Array[String]): Unit = {

    val kafkaTopic = "tweets-topic"
    val kafkaBrokers = "localhost:9092"
    val processedTopic = "processed-tweets-topic"

    val producerProps = new Properties()
    producerProps.put("bootstrap.servers", kafkaBrokers)
    producerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    producerProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](producerProps)

    val tweetFilePath = "src/main/boulder_flood_geolocated_tweets.json"
    val tweets = Source.fromFile(tweetFilePath).getLines()

    println(s"Streaming tweets from $tweetFilePath to Kafka topic: $kafkaTopic")

    tweets.foreach { tweet =>
      val record = new ProducerRecord[String, String](kafkaTopic, tweet)
      producer.send(record)
    }

    producer.close()

    val consumerProps = new Properties()
    consumerProps.put("bootstrap.servers", kafkaBrokers)
    consumerProps.put("key.deserializer", classOf[StringDeserializer].getName)
    consumerProps.put("value.deserializer", classOf[StringDeserializer].getName)
    consumerProps.put("group.id", "tweet-consumer-group")
    consumerProps.put("auto.offset.reset", "earliest")
    val consumer = new KafkaConsumer[String, String](consumerProps)
    consumer.subscribe(java.util.Arrays.asList(kafkaTopic))

    def analyzeSentimentBasic(text: String): String = {
      val lowerText = text.toLowerCase
      if (lowerText.contains("happy") || lowerText.contains("great") || lowerText.contains("good")) "Positive"
      else if (lowerText.contains("sad") || lowerText.contains("bad") || lowerText.contains("terrible")) "Negative"
      else "Neutral"
    }

    def analyzeTweet(tweet: String): (String, Array[String], String) = {
      val sentiment = analyzeSentimentBasic(tweet)
      val hashtags = extractHashtags(tweet)
      val processedTweet = s"""{"original": "$tweet", "hashtags": [${hashtags.mkString(",")}], "sentiment": "$sentiment"}"""
      (processedTweet, hashtags, sentiment)
    }

    def extractHashtags(text: String): Array[String] = {
      val hashtagPattern = """#\w+""".r
      hashtagPattern.findAllIn(text).toArray
    }

    try {
      var counter = 0
      while (true) {
        val records = consumer.poll(java.time.Duration.ofMillis(100))
        for (record <- records.asScala) {
          val tweet = record.value()
          val (processedTweet, hashtags, sentiment) = analyzeTweet(tweet)
          println(s"Processed Tweet: $processedTweet")

          val producerRecord = new ProducerRecord[String, String](processedTopic, processedTweet)
          producer.send(producerRecord)

          counter += 1
        }
      }
    } catch {
      case e: Exception =>
        println(s"Error while streaming tweets: ${e.getMessage}")
        e.printStackTrace()
    } finally {
      consumer.close()
      println("Tweet streaming completed.")
    }
  }
}
