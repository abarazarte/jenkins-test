#!/bin/bash

mvn compile
mvn exec:java -Dexec.mainClass="cl.multicaja.kong.app.CreateMigration"