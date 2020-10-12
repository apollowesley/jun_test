#!/bin/bash
#http://www.ibm.com/developerworks/cn/linux/1309_huangwh_linuxshell/
echo "Current path is: `pwd`"

scriptpath=`dirname $0`

echo "The script is located at: $scriptpath"

cat "$scriptpath/$0"

sleep 5
