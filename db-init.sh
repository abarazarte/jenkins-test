#!/bin/bash

id=$(date +%s | sha256sum | base64 | head -c 32 ; echo)

echo "ID: $id"

cat <<EOF >./db-id.txt
$id
EOF

cat <<EOF >./pg/proyecto1/environments/jenkins.properties
time_zone=GMT-4:00
driver=org.postgresql.Driver
url=jdbc:postgresql://localhost:5432/$id
username=postgres
password=postgres
send_full_script=true
delimiter=;
full_line_delimiter=false
auto_commit=false
ignore_warnings=true
changelog=CHANGELOG
EOF

createdb $id -U postgres -W postgres -O postgres -w

psql -U postgres -W postgres -w -d $id -a -f dump.sql
