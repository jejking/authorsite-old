#!/bin/bash

# determine the directories to back up
DIRS="/home/jejking/Documents ${CVSROOT} /home/jejking/Mail"

# datestamp label to be used with tar files
DATESTAMP=$(date +%Y%m%d)

BACKUPDIR=/home/jejking/backups

# create new tar file of the files we want
find $DIRS -print | tar zcvf $BACKUPDIR/backup.$DATESTAMP.tgz -T -
echo 'created file $BACKUPDIR/backup.$DATESTAMP.tgz'

# determine checksum (in this the number of bytes) in LATEST
# if the file doesn't exist, it will be 0
ORIGINALCHECKSUM=$(cat $BACKUPDIR/backup.LATEST.tgz | wc -c)
echo "original checksum = ${ORIGINALCHECKSUM}"
NEWCHECKSUM=$(cat $BACKUPDIR/backup.$DATESTAMP.tgz | wc -c)
echo "new checksum = ${NEWCHECKSUM}"

if [ "$ORIGINALCHECKSUM" = "$NEWCHECKSUM" ]; then
    echo 'original and new checksums equal - no copy necessary. Exiting'
    exit 0
else
    echo 'original and new checksums not equal - copying to zelaza'
    
    # copy the new tar file to zelaza
    scp $BACKUPDIR/backup.$DATESTAMP.tgz jejking@zelaza.pair.com:~/backups/

    date | mail -s "backup ${DATESTAMP} copied to zelaza" jejking

    # make the copied file into LATEST
    cp $BACKUPDIR/backup.$DATESTAMP.tgz $BACKUPDIR/backup.LATEST.tgz
fi