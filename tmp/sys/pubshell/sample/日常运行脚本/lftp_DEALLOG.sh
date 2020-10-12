#!/bin/bash
DEAL=/usr/local/backup/ecartoonftp/deal-log
LOG=/usr/local/backup/ecartoonftp/deal-log/deal-log.log
MYDATE=`date +%Y%m%d`
echo "###############################################" >> $LOG
echo "start:" $(date +"%Y%m%d %H:%M:%S")  >> $LOG
echo "###############################################" >> $LOG
echo "8000000300000002input MIRROR" >> $LOG
lftp -u hu,hu -e "mirror --no-recursion --only-newer --only-missing --verbose zhongbai/8000000300000002/8000000300000002input/ \
/usr/local/backup/ecartoonftp/deal-log/8000000300000002input;exit" ftp://59.175.174.194 >> $LOG
echo "------------------------------------------------------" >> $LOG

echo "8000000300000002output MIRROR" >> $LOG
lftp -u hu,hu -e "mirror --no-recursion --only-newer --only-missing --verbose zhongbai/8000000300000002/8000000300000002output/ \
/usr/local/backup/ecartoonftp/deal-log/8000000300000002output;exit" ftp://59.175.174.194 >> $LOG
echo "------------------------------------------------------" >> $LOG

echo "80000004Input MIRROR" >> $LOG
lftp -u hu,hu -e "mirror --no-recursion --only-newer --only-missing --verbose zhongbai/80000004Input/ \
/usr/local/backup/ecartoonftp/deal-log/80000004Input;exit" ftp://59.175.174.194 >> $LOG
echo "------------------------------------------------------" >> $LOG

echo "80000004Output MIRROR" >> $LOG
lftp -u hu,hu -e "mirror --no-recursion --only-newer --only-missing --verbose zhongbai/80000004Output/ \
/usr/local/backup/ecartoonftp/deal-log/80000004Output;exit" ftp://59.175.174.194 >> $LOG
echo "------------------------------------------------------" >> $LOG

echo "80000007Input MIRROR" >> $LOG
lftp -u hu,hu -e "mirror --no-recursion --only-newer --only-missing --verbose zhongbai/80000007Input/ \
/usr/local/backup/ecartoonftp/deal-log/80000007Input;exit" ftp://59.175.174.194 >> $LOG
echo "------------------------------------------------------" >> $LOG

echo "80000007Output MIRROR" >> $LOG
lftp -u hu,hu -e "mirror --no-recursion --only-newer --only-missing --verbose zhongbai/80000007Output/ \
/usr/local/backup/ecartoonftp/deal-log/80000007Output;exit" ftp://59.175.174.194 >> $LOG
echo "------------------------------------------------------" >> $LOG

echo "80000008Input MIRROR" >> $LOG
lftp -u hu,hu -e "mirror --no-recursion --only-newer --only-missing --verbose zhongbai/80000008Input/ \
/usr/local/backup/ecartoonftp/deal-log/80000008Input;exit" ftp://59.175.174.194 >> $LOG
echo "------------------------------------------------------" >> $LOG

echo "80000008Output MIRROR" >> $LOG
lftp -u hu,hu -e "mirror --no-recursion --only-newer --only-missing --verbose zhongbai/80000008Output/ \
/usr/local/backup/ecartoonftp/deal-log/80000008Output;exit" ftp://59.175.174.194 >> $LOG
echo "------------------------------------------------------" >> $LOG

echo "80000009Input MIRROR" >> $LOG
lftp -u hu,hu -e "mirror --no-recursion --only-newer --only-missing --verbose zhongbai/80000009Input/ \
/usr/local/backup/ecartoonftp/deal-log/80000009Input;exit" ftp://59.175.174.194 >> $LOG
echo "------------------------------------------------------" >> $LOG

echo "80000009Output MIRROR" >> $LOG
lftp -u hu,hu -e "mirror --no-recursion --only-newer --only-missing --verbose zhongbai/80000009Output/ \
/usr/local/backup/ecartoonftp/deal-log/80000009Output;exit" ftp://59.175.174.194 >> $LOG
echo "------------------------------------------------------" >> $LOG

echo "80000010Input MIRROR" >> $LOG
lftp -u hu,hu -e "mirror --no-recursion --only-newer --only-missing --verbose zhongbai/80000010Input/ \
/usr/local/backup/ecartoonftp/deal-log/80000010Input;exit" ftp://59.175.174.194 >> $LOG
echo "------------------------------------------------------" >> $LOG

echo "80000010Output MIRROR" >> $LOG
lftp -u hu,hu -e "mirror --no-recursion --only-newer --only-missing --verbose zhongbai/80000010Output/ \
/usr/local/backup/ecartoonftp/deal-log/80000010Output;exit" ftp://59.175.174.194 >> $LOG
echo "------------------------------------------------------" >> $LOG

echo "80000012Input MIRROR" >> $LOG
lftp -u hu,hu -e "mirror --no-recursion --only-newer --only-missing --verbose zhongbai/80000012Input/ \
/usr/local/backup/ecartoonftp/deal-log/80000012Input;exit" ftp://59.175.174.194 >> $LOG
echo "------------------------------------------------------" >> $LOG

echo "80000012Output MIRROR" >> $LOG
lftp -u hu,hu -e "mirror --no-recursion --only-newer --only-missing --verbose zhongbai/80000012Output/ \
/usr/local/backup/ecartoonftp/deal-log/80000012Output;exit" ftp://59.175.174.194 >> $LOG
echo "------------------------------------------------------" >> $LOG

echo "8000001400000001input MIRROR" >> $LOG
lftp -u hu,hu -e "mirror --no-recursion --only-newer --only-missing --verbose zhongbai/etk/8000001400000001input/ \
/usr/local/backup/ecartoonftp/deal-log/8000001400000001input;exit" ftp://59.175.174.194 >> $LOG
echo "------------------------------------------------------" >> $LOG

echo "8000001400000001output MIRROR" >> $LOG
lftp -u hu,hu -e "mirror --no-recursion --only-newer --only-missing --verbose zhongbai/etk/8000001400000001output/ \
/usr/local/backup/ecartoonftp/deal-log/8000001400000001output;exit" ftp://59.175.174.194 >> $LOG
echo "------------------------------------------------------" >> $LOG

echo "###############################################" >> $LOG
echo "end:" $(date +"%Y%m%d %H:%M:%S")  >> $LOG
echo "###############################################" >> $LOG
