#! /bin/bash
set -e

echo 'STEP 1 : start mysql ...'
service mysql start
sleep 3

if [ -e "is_first.txt" ];then
	sleep 3
else
	echo 'STEP 2 : import data ...'
	mysql < schema.sql
	sleep 3

	echo 'STEP 3 : modify password ...'
	mysql < privileges.sql
	sleep 3
	echo "This container is not the first time to start, if you see this file." > is_first.txt
fi

echo `service mysql status`
echo 'all finished'
tail -f /dev/null