#!/bin/bash

USER=bds-appp
PASSWORD=lomer1234
HOST=localhost
DATABASE=bds-zdenva
BACKUP_DIR=./backups
TIMESTAMP=$(date +"%Y%m%d%H%M%S")

PGPASSWORD=$PASSWORD pg_dump -h $HOST -U $USER -F c -b -v -f $BACKUP_DIR/backup_$TIMESTAMP.dump $DATABASE

# Add to crontab to backup each midnight.
# 0 0 * * * /path/to/backup_script.sh
