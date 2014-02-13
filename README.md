dynamodb
========

Example java web application based on the documentation from Amazon DynamoDB : 

http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/GettingStartedDynamoDB.html

##Technologies used:

* Spring 4.x
* Maven 3.0.x
* DynamoDB Local 
* jcabi-dynamodb-maven-plugin - for unit test and local development
* Deltaset - for automatic UI 
* jetty maven plugin
* tomcat maven plugin

jcabi-dynamodb-maven-plugin :
http://www.jcabi.com/jcabi-dynamodb-maven-plugin/

##Getting Started 

Starting DyanamoDB Local
```
mvn exec:exec 
```

Starting the application ( in separate terminal window)
```
mvn clean install jetty:run
```
or
```
mvn clean install tomcat7:run
```

open your browser
http://localhost:8080

##Examples covered

Load Data into Tables Using the AWS SDK for Java
http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/LoadData_Java.html

Querying Tables Using the AWS SDK for Java Low-Level API
http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/LowLevelJavaQuerying.html

Scanning Tables Using the AWS SDK for Java Low-Level API
http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/LowLevelJavaScanning.html

Higher-Level Programming Interfaces for DynamoDB
http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/ORM.html

Working with Tables Using the AWS SDK for Java Low-Level API
http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/LowLevelJavaCreateUpdateDeleteTable.html




