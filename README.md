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
