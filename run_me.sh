#!/usr/bin/env bash

rm ./h2db.mv.db

echo "please make sure you have java 1.8 or higher"

echo "going to read html and generate sql"

./gradlew run

echo "Please open http://localhost:8082 to view h2 database. Put in these 2 parameters. Driver Class: org.h2.Driver,  JDBC URL: jdbc:h2:./h2DB"

java -jar ./h2-1.4.199.jar

