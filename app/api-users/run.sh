#!/bin/bash
echo 'Deploying spreingboot'
VERSION=`mvn help:evaluate -Dexpression=project.version | grep "^[^\[]"`
echo ${VERSION}
echo "Executing api-users-${VERSION}.jar"
BUILD_ID=do_not_kill_me
nohup java -jar target/api-users-${VERSION}.jar --server.port=3100 &
sleep 1
