#### Build 

for Local run:
mvn package && java -jar target/crealytics-assignments-1.0.0.jar

If you want to run with Docker (in case you dont have mongo instance handy :) ), execute:
docker-compose up

#### Details:

This application is built using Spring boot, java 8, and Mongo DB.
Rest API used for consuming csvs.

GET localhost:9000/ingestdata/

other apis

 GET localhost:9000/reports?month=1&site=iOS
 
 GET localhost:9000/reports?month=2
 
 GET localhost:9000/reports?site=android

Note: I have not stored costing percentages as required in assignment , but as we are returning this json data we can store to DB 
as per dimensions. 
 
####  Improvements:

we can use OAUTH and spring security, for security and can have 
token interceptor to validate token passed in header.

MongoDB provides built-in full-text search capabilities but does not 
provide advanced indexing and search features.  
Apache Solr is based on the high performance, full-featured text 
search engine Lucene. Using Mongo Connector; you can index MongoDB data in Apache Solr.


 



