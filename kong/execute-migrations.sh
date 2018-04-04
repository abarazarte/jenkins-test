#!/bin/bash

mvn compile
mvn exec:java -Dexec.mainClass="cl.multicaja.kong.app.ExecuteMigrations" -Dexec.args="$1 $2 $3 $4 $5 $6 $7 $8 $9"