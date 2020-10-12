#!/bin/bash
#http://blog.csdn.net/chlaws/article/details/8799784

#������� �ļ���
#����ֵ   0,�Ϸ�;����ֵ�Ƿ������
function check_syntax()
{
	if [ ! -f $1 ];then 
		return 1
	fi

	ret=$(awk -F= 'BEGIN{valid=1}
	{
		#�Ѿ��ҵ��Ƿ���,��һֱ�Թ�����
		if(valid == 0) next
		#���Կ���	
		if(length($0) == 0) next
		#�������еĿո�
		gsub(" |\t","",$0)	
		#����Ƿ���ע����	
		head_char=substr($0,1,1)
		if (head_char != "#"){
			#�����ֶ�=ֵ ��ʽ�ļ���Ƿ��ǿ���
			if( NF == 1){
				b=substr($0,1,1)
				len=length($0)
				e=substr($0,len,1)
				if (b != "[" || e != "]"){
					valid=0
				}
			}else if( NF == 2){
			#����ֶ�=ֵ ���ֶο�ͷ�Ƿ���[
				b=substr($0,1,1)
				if (b == "["){
					valid=0
				}
			}else{
			#���ڶ��=�ŷָ�Ķ��Ƿ�
				valid=0
			}	
		}
	}
	END{print valid}' $1)
	
	if [ $ret -eq 1 ];then
		return 0
	else
		return 2
	fi
}

#����1 �ļ���
#����2 ����
#����3 �ֶ���
#����0,��ʾ��ȷ,��������ַ�����ʾ�ҵ���Ӧ�ֶε�ֵ
#���������������ʾδ�ҵ���Ӧ���ֶλ����ǳ���
function get_field_value()
{
	if [ ! -f $1 ] || [ $# -ne 3 ];then
		return 1
	fi
blockname=$2
fieldname=$3

begin_block=0
end_block=0

	cat $1 | while read line
	do
	
		if [ "X$line" = "X[$blockname]" ];then
			begin_block=1
			continue
		fi
		
		if [ $begin_block -eq 1 ];then
			end_block=$(echo $line | awk 'BEGIN{ret=0} /^\[.*\]$/{ret=1} END{print ret}')
			if [ $end_block -eq 1 ];then
				#echo "end block"
				break
			fi
	
			need_ignore=$(echo $line | awk 'BEGIN{ret=0} /^#/{ret=1} /^$/{ret=1} END{print ret}')
			if [ $need_ignore -eq 1 ];then
				#echo "ignored line:" $line
				continue
			fi
			field=$(echo $line | awk -F= '{gsub(" |\t","",$1); print $1}')
			value=$(echo $line | awk -F= '{gsub(" |\t","",$2); print $2}')
			#echo "'$field':'$value'"
			if [ "X$fieldname" = "X$field" ];then	
				#echo "result value:'$result'"
				echo $value
				break
			fi
			
		fi
	done
	return 0
}

check_syntax odbcinst.ini
echo "check syntax status:$?"
GLOBAL_FIELD_VALUE=$(get_field_value odbcinst.ini PostgreSQL Setup)
echo "status:$?,value:$GLOBAL_FIELD_VALUE"


