#!/bin/bash
HOMEDIR=/usr/local/backup/ecartoonftp/

cd $HOMEDIR
find . -name "*.txt" -mtime +30 -exec rm -rf {} \;
find . -name "*.result" -mtime +30 -exec rm -rf {} \;
find . -name "check_result*.*" -mtime +30 -exec rm -rf {} \;
find . -name "card_info*.*" -mtime +30 -exec rm -rf {} \;

