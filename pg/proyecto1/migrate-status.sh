#!/bin/bash

export MIGRATIONS="$(dirname "$(pwd)")/mybatis-migrations-3.3.3"
export PATH=$PATH:$MIGRATIONS/bin

echo "MyIbatis migrations: $MIGRATIONS"

migrate status $1 $2 $3
