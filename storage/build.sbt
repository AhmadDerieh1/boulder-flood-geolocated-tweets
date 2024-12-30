name := "boulder-flood-geolocated-tweets"

version := "0.1"

scalaVersion := "2.12.18" // Ensure this version is compatible with your project

// Project dependencies
libraryDependencies ++= Seq(
  libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "4.10.0"

  // MongoDB Scala Driver
  "org.mongodb" %% "mongo-scala-driver" % "4.6.0",

  // Akka - for parallel computation if needed
  "com.typesafe.akka" %% "akka-actor" % "2.6.14",

  // SBT plugin for project build support
  "org.scala-sbt" % "sbt" % "1.5.5",

  // Kafka dependency (if required)
  "org.apache.kafka" %% "kafka" % "2.8.0",

  // Elasticsearch (optional)
  "org.elasticsearch.client" % "elasticsearch-rest-high-level-client" % "7.10.0"
)
