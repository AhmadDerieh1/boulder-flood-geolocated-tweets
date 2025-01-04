# Boulder Flood Geolocated Tweets

This project focuses on collecting, processing, and analyzing geolocated tweets during the Boulder flood event. By leveraging the power of Apache Spark and Scala, the project aims to gain insights into public sentiment, disaster tracking, and the role of social media in natural disasters. Geotagged tweets are processed to identify patterns, track the event in real-time, and perform sentiment analysis for improved situational awareness.

## Project Overview

During the Boulder flood, social media platforms like Twitter played a crucial role in sharing real-time information. This repository collects geolocated tweets posted during the event, processes the data using Apache Spark, and analyzes it to provide insights such as tweet volume, geographic distribution, and sentiment analysis of the posts.

The project serves as a case study for using social media data in disaster management and emergency response efforts.

## Features

- **Geolocated Tweet Collection**: Extracts geolocated tweets from Twitter, focusing on the Boulder flood event.
- **Data Processing**: Utilizes Apache Spark for scalable data processing, handling large datasets efficiently.
- **Sentiment Analysis**: Analyzes tweet sentiment to track public mood and reactions in real-time.
- **Location-based Insights**: Identifies tweet density and distribution across Boulder, providing geographical insights into how the event impacted different areas.
- **Visualization**: Creates basic visualizations to represent tweet distribution and sentiment over time.

## Prerequisites

Before running the project, ensure that you have the following installed:

- **Scala**: Version 2.x (For compatibility with the project code).
- **Apache Spark**: Version 3.x (For distributed data processing).
- **SBT**: A build tool for Scala projects, used to manage dependencies.

## Installation

Follow these steps to get the project running on your local machine:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/AhmadDerieh1/boulder-flood-geolocated-tweets.git
   cd boulder-flood-geolocated-tweets
   ```

2. **Run Kafka**:

   Add these dependencies to your `build.sbt` file:
   ```scala
   libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.5.0"
   libraryDependencies += "org.slf4j" % "slf4j-simple" % "2.0.9"
   ```

   **How to Run Kafka on Windows**:

   1. **Start Zookeeper**:
      Kafka requires Zookeeper to manage its cluster. Start Zookeeper using:
      ```cmd
      bin\windows\zookeeper-server-start.bat config\zookeeper.properties
      ```

   2. **Start Kafka Broker**:
      In another terminal, start the Kafka broker:
      ```cmd
      bin\windows\kafka-server-start.bat config\server.properties
      ```

3. **Install MongoDB**:

   Download and install MongoDB from the [official MongoDB website](https://www.mongodb.com/try/download/community).

   **Start MongoDB**:
   Once MongoDB is installed, you can start it by running the following command:
   ```cmd
   mongod
   ```
   By default, MongoDB runs on `localhost:27017`.

4. **MongoDB Integration**:
   To integrate MongoDB with the project, ensure that the MongoDB Scala Driver is included in your `build.sbt`:
   ```scala
   libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "4.5.0"
   ```

5. **MongoDB Setup**:
   The processed tweet data will be stored in a MongoDB database named `tweets_db` and a collection named `tweets_collection`. You can create the MongoDB database by simply running the project; MongoDB will automatically create the database and collection.

6. **Run the Project**:
   To run the project, execute the following command:
   ```bash
   sbt run
   ```
   This will start processing tweets and storing them in MongoDB, as well as performing sentiment analysis and sending data to Kafka.
