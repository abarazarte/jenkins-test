#!/bin/bash

id=$(cat db-id.txt)

echo "Borrando base de datos: $id"

dropdb $id -U postgres