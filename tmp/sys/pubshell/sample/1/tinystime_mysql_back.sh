filename="/opt/scripts/db_time.$(date "+%Y-%m-%d").sql"

mysqldump --database db_time -u root -proot > $filename
