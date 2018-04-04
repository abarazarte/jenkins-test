#!/bin/bash

mvn clean package && java -jar target/api-users-1.0.0.jar --server.port=3100
