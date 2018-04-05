#!/bin/bash

VERSION=`mvn help:evaluate -Dexpression=project.version | grep "^[^\[]"`
echo ${VERSION}
