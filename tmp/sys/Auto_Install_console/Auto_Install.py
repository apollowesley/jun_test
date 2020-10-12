# -*- coding:utf8 -*-
#导入各模块
from core import *
from config import *
from account import *
from init import *
from iptables import *
from java import *
from mysql import *
from redis import *


#----------------------------------------------------------------------------------------
#主函数
if __name__ == "__main__":
	server = "192.168.100.100"
	port = 22
	username = "root"
	password = "kuentech"
	account = "hartnett"
	passwd = "hartnett999"
	newpasswd = "hartnett123456"

	iptables_file = "kuen_firewall.sh"
	iptables_template = "basic/iptables/iptables.tpl"

	ntpserver = "210.72.145.44"  #国家授时中心服务器IP地址
	start_area 	= 1
	end_area	= 2


	add_account(server, port, username, password, account, passwd)
	modify_pass(server, port, username, password, account, newpasswd)
	del_account(server, port, username, password, account)
	#set_iptables(server, port, username, password, iptables_file, iptables_template)
	set_hostname(server, port, username, password, 'kuen_server06')
	set_ntpserver(server, port, username, password, ntpserver)
	set_history(server, port, username, password)	#profile也在里面
	#set_nameserver(server, port, username, password)
	set_limlits(server, port, username, password)
	#Install_Java(server, port, username, password, start_area, end_area)
	#Install_mysql(server, port, username, password, start_area, end_area)