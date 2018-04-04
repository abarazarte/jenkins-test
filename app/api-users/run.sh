#!/bin/bash

kill $(lsof -i :3100 | grep LISTEN | awk '{print $2}')
BUILD_ID=do_not_kill_me
java -jar target/api-users-1.0.2.jar --server.port=3100 &
