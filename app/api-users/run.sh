#!/bin/bash

NAME=`mvn help:evaluate -Dexpression=project.name | grep "^[^\[]"`
VERSION=`mvn help:evaluate -Dexpression=project.version | grep "^[^\[]"`
java -jar target/${NAME}-${VERSION}.jar --server.port=3100 &
