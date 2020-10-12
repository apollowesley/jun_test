#!/bin/bash

pid=$$

name=`basename $0`

ps -ef | awk -v p=$pid -v n=$name '$2!=p && $NF~n{system("kill "p)}'

sleep 30
