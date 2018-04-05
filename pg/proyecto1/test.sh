#!/bin/bash

mvn test -DskipTests=false -Denv=$1
