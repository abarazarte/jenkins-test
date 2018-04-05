#!/bin/bash
echo 'Deploying spreingboot'
VERSION=`mvn help:evaluate -Dexpression=project.version | grep "^[^\[]"`
echo ${VERSION}
BUILD_ID=do_not_kill_me
nohup java -jar target/api-users-${VERSION}.jar --server.port=3100 &
echo "Executing api-users-${VERSION}.jar"
