#!/bin/bash
kill -9 $(lsof -i:3100 -t) 2> /dev/null
VERSION=`mvn help:evaluate -Dexpression=project.version | grep "^[^\[]"`
echo ${VERSION}
BUILD_ID=do_not_kill_me
java -jar target/api-users-${VERSION}.jar --server.port=3100
echo "Executing api-users-${VERSION}.jar"
