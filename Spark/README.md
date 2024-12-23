# Boulder Flood Geolocated Tweets

This repository streams geolocated tweets about the Boulder flood to a Kafka topic. The tweets are read from a JSON file and sent to a Kafka broker for further processing or analysis.

---
## **Add these to build.sbt file** ##
```cmd
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.5.0"

libraryDependencies += "org.slf4j" % "slf4j-simple" % "2.0.9"
```
## **How to Run Kafka on Windows**

### 1. **Start Zookeeper**
Kafka requires Zookeeper to manage its cluster. Start Zookeeper using:
```cmd
bin\windows\zookeeper-server-start.bat config\zookeeper.properties
```

### 2. **Start Kafka Broker**
In another terminal, start the Kafka broker:
```cmd
bin\windows\kafka-server-start.bat config\server.properties
```


### 3. **Show the Tweets in Kafka Terminal**
To verify the messages in the topic:
```cmd
bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic tweets-topic --from-beginning
```

---



