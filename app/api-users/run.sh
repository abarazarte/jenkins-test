#!/bin/bash

mvn -DskipTests=true clean package

VERSION=`mvn help:evaluate -Dexpression=project.version | grep "^[^\[]"`
serverPort=3100

#CONSTANTS
logFile=initServer.log
dstLogFile=target/$logFile
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

function deleteFiles(){
    echo "Deleting $dstLogFile"
    rm -rf $dstLogFile

    echo " "
}

function run(){

   #echo "java -jar target/api-users-${VERSION}.jar --server.port=$serverPort " | at now + 1 minutes

   nohup nice java -jar target/api-users-${VERSION}.jar --server.port=$serverPort $> $dstLogFile 2>&1 &

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
# JENKINS_NODE_COOKIE=dontKillMe /path/to/this/file/api-deploy.sh dev 8082 spring-boot application-localhost.yml

# 1 - stop server on port ...
stopServer

# 2 - remove files
deleteFiles

# 3 - start server
run

# 4 - watch loading messages until  ($whatToFind) message is found
watch
