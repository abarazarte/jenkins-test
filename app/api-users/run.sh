#!/bin/bash

VERSION=`mvn help:evaluate -Dexpression=project.version | grep "^[^\[]"`
java -jar target/api-users-${VERSION}.jar --server.port=3100 &
