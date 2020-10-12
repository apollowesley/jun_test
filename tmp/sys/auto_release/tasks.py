# -*- coding:utf8 -*-
from celery import Celery
import os
import xmlrpclib
import tornado.httpclient
from celery.task.http import URL

app = Celery('task', backend='redis://192.168.5.13:6379/0', broker='redis://192.168.5.13:6379/0')

#------------------------------------------------------------------------------
#版本发布任务
@app.task
def release_version(server_ip, v_filename, local_path, dest_path, process_name):
	#解压版本zip包到/tmp目录中
	unzip_cmd = "unzip -o %s -d /tmp/" % v_filename
	os.system(unzip_cmd)

	#发布版本到指定的服务器
	rsync_cmd = 'rsync -avz -e ssh --exclude "init.properties" --exclude "jdbc.properties" --exclude "redis.properties" %s root@%s:%s --delete' % (local_path, server_ip, dest_path)
	print rsync_cmd
	os.system(rsync_cmd)

#------------------------------------------------------------------------------
#服务进程管理任务
@app.task
def services_manager(server_ip, active, process_name):
	rpcaddr = 'http://%s:9001/RPC2' % server_ip
	server = xmlrpclib.Server(rpcaddr)

	if active == "start":
		server.supervisor.startProcess(process_name)

	if active == "stop":
		server.supervisor.stopProcess(process_name)

	if active == "restart":
		server.supervisor.stopProcess(process_name)
		server.supervisor.startProcess(process_name)
		




	

    
