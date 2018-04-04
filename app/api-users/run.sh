#!/bin/bash

kill $(lsof -i :3100 | grep LISTEN | awk '{print $2}')
java -jar target/api-users-1.0.0.jar --server.port=3100 &
