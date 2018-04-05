#!/bin/bash

VERSION=`mvn help:evaluate -Dexpression=project.version | grep "^[^\[]"`
serverPort=3100

#CONSTANTS
logFile=initServer.log
dstLogFile=./target/$logFile
whatToFind="Started Application in"
msgBuffer="Buffering: "
msgAppStarted="Application Started... exiting buffer!"

### FUNCTIONS
##############
function stopServer(){
    echo " "
    echo "Stoping process on port: $serverPort"
    fuser -n tcp -k $serverPort > redirection &
    echo " "
}

function run(){

   #echo "java -jar target/api-users-${VERSION}.jar --server.port=$serverPort " | at now + 1 minutes

   BUILD_ID=dontKillMe1 nohup nice java -jar target/api-users-${VERSION}.jar --server.port=$serverPort $> $dstLogFile 2>&1 &

   echo "COMMAND: nohup nice java -jar target/api-users-${VERSION}.jar --server.port=$serverPort $> $dstLogFile 2>&1 &"

   echo " "
}

function watch(){
    tail -f $dstLogFile |

        while IFS= read line
            do
                echo "$msgBuffer" "$line"

                if [[ "$line" == *"$whatToFind"* ]]; then
                    echo $msgAppStarted
                    pkill  tail
                fi
        done
}

### FUNCTIONS CALLS
#####################
# Use Example of this file. Args: enviroment | port | project name | external resourcce
# BUILD_ID=dontKillMe /path/to/this/file/api-deploy.sh dev 8082 spring-boot application-localhost.yml

# 1 - stop server on port ...
stopServer

# 2 - start server
run

# 3 - watch loading messages until  ($whatToFind) message is found
watch
