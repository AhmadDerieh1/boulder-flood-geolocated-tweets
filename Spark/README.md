# Boulder Flood Geolocated Tweets

This repository streams geolocated tweets about the Boulder flood to a Kafka topic. The tweets are read from a JSON file and sent to a Kafka broker for further processing or analysis.

---

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



