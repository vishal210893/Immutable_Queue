Requirements
Handle large write volume: Billions of write events per day.
Handle large read/query volume: Millions of merchants wish to gain insight into their business. Read/Query patterns are time-series related metrics.
Provide metrics to customers with at most one hour delay.
Run with minimum downtime.
Have the ability to reprocess historical data in case of bugs in the processing logic.
Design Solution
The detailed design can be accessed at Design Document.

The system provides following APIs

Events API Service: To record the events to underlying database
Insights API Service: To retrieve the insights.
These services sit behind an API gateway that can be used for autheticating the incoming traffic. This also hides the details of backend services from end-user. These services will be hosted in the Kubernetes cluster so they can easily be scaled to support the high throughput.

Saving of Events
Since the events are generated at very high rate so we want to build software stack that has high-throughput. Once a new event is generated, the API receives the request and instantly returns the response. This way it becomes readily available for the next request. The API service does not do any processing on the event except to push it the Kafka. The messages from Kafka are prcessed by Spark Streaming. Spark Streaming is a distributed and scalable fault-tolerant system and hence a perfect compliment to Kafka. Spark Streaming processes the message and save the events to database. In this case Casandara is being used. Casandera is a perfect choice because it is distributed database management and can handle high volume of data.

Generating Insights
Since we are not required to provide real-time insights. So in order to support the queries, a datawarehouse is build. We can have an ETL process that runs every hour and loads data to the datawarehouse from casendara db. This way we can have multi-dimensional cubes to suuport the OLAP queries. This will provide a fast access to traffic insights.
