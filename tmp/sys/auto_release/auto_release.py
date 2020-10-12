# -*- coding:utf8 -*-
import pymongo
import tornado.httpserver
import tornado.ioloop
import tornado.options
import tornado.httpclient
import tornado.web
import string
import os
import ldap
import xmlrpclib
import time
import bson

from tasks import release_version, services_manager
from tornado.options import define, options

define("port", default=100, help="run on the given port", type=int)

#------------------------------------------------------------------------------
#域账户验证函数
def checkuser(ldaphost, domain, username, password):
    ldap.set_option(ldap.OPT_REFERRALS,0)
    ldap.set_option(ldap.OPT_X_TLS_REQUIRE_CERT,ldap.OPT_X_TLS_NEVER)
    ldaphost = "ldap://%s" % ldaphost
    conn = ldap.initialize(ldaphost)
    domainuser = r"%s\%s" % (domain, username)
    conn.simple_bind_s(domainuser, password)
    mail = "%s@%s.com" % (domain, username)

    result_id = conn.search("DC=%s,DC=com" % domain,
                                ldap.SCOPE_SUBTREE,
                                "(&(objectClass=user)(mailNickName=%s))" % username,
                                None)

    result_type, result_data = conn.result(result_id, 1000)
    return mail, result_data

#------------------------------------------------------------------------------
#主页面
class MainHandler(tornado.web.RequestHandler):
    def get(self):
        username = self.get_cookie("username", "")
        print username
        if not username:
            self.redirect("/login")
        else:

            page = int(self.get_argument('page', 1))
            conn = pymongo.Connection('192.168.100.100', 27017)
            version_db = conn.version
            release_record = version_db.release_record

            pagesize = 30
            total_size = release_record.find({}).count()

            if total_size % pagesize == 0:
                pages = total_size / pagesize / 2
            else:
                pages = total_size / pagesize / 2 + 1

            if page > pages:
                page = pages

            if page < 1 :
                page = 1

            i = (page - 1) * pagesize
            if i < 0:
                i = 0

            versions = release_record.find({}).sort([("create_time", -1)]).limit(pagesize).skip(i)
            self.render("home.html", versions=versions, page=page, pages=pages)

#------------------------------------------------------------------------------
#版本发布
class ReleaseHandler(tornado.web.RequestHandler):
    def get(self):
        conn = pymongo.Connection('192.168.100.100', 27017)
        version_db = conn.version
        areas = version_db.areas
        username = self.get_cookie("username", "")
        print username
        if not username:
            self.redirect("/login")
        else:
            filename = self.get_argument("v", "")
            v_type = self.get_argument("v_type", "")

            areas_info = areas.find({"app_type" : v_type})
            print filename
            if filename:
               self.render("release.html", filename=filename, v_type=v_type, areas_info=areas_info)
            else:
                self.redirect("/")

    def post(self):
        username = self.get_cookie("username", "")
        print username
        if not username:
            self.redirect("/login")
        else:
            filename = self.get_argument("v", "")
            version_type = self.get_argument("v_type", "")
            ids = self.get_arguments("_id")
            print ids

            conn = pymongo.Connection('192.168.100.100', 27017)
            version_db = conn.version
            areas = version_db.areas
            projects = version_db.projects
            print version_type
            projects_info = projects.find({'project_code' : version_type})
            for project_info in projects_info:
                local = project_info['project_path']
                print project_info, local
            
            for _id in ids:
                area = areas.find({'_id' : bson.ObjectId(_id)})
                for item in area:
                    print item,type(item)
                    server_ip = item['server_ip']
                    process_path = item['process_path']
                    process_name = item['process_name']
                    print filename, server_ip, process_path

                if filename:
                    #发布版本
                    result = release_version.delay(server_ip, filename, local, process_path, process_name)
                    print result.get(timeout=300)
                    #重启服务
                    result = services_manager.delay(server_ip, "restart", process_name)
                    print result.get(timeout=120)

            self.redirect("/serverinfo/")

#------------------------------------------------------------------------------
#服务器信息管理
class ServerInfoHandler(tornado.web.RequestHandler):
    def get(self):
        process_infos = {}  #每个大区的服务器信息
        app_ips = {}        #大区与IP的对应关系
        conn = pymongo.Connection('192.168.100.100', 27017)
        version_db = conn.version
        areas = version_db.areas
        areas_info = areas.find({}).sort('server_ip', 1)

        for area in areas_info:
            ip = area.get('server_ip')
            app_name = area.get('app_name')
            process_name = area.get('process_name')
            server = xmlrpclib.Server('http://' + ip + ':9001/RPC2')
            process_info = server.supervisor.getProcessInfo(process_name)
            process_infos[app_name] = process_info
            app_ips[app_name] = ip

        self.render("serverinfo.html", process_infos=process_infos, app_ips=app_ips)

#-----------------------------------------------------------------------------
#服务进程操作
class ServicesHandler(tornado.web.RequestHandler):
    def get(self):
        ip = self.get_argument("ip", "")
        active = self.get_argument("active", "")
        name = self.get_argument("name", "")

        rpcaddr = 'http://%s:9001/RPC2' % ip
        server = xmlrpclib.Server(rpcaddr)

        if active in ["start", "stop", "restart"]:
            result = services_manager.delay(ip, active, name)
            self.redirect("/serverinfo/")

        if active == "tailProcessStdoutLog":
            ProcessStdoutLog = server.supervisor.tailProcessStdoutLog(name, 0, 20000)
            self.render("viewlogs.html", logs=ProcessStdoutLog)

        if active == "tailProcessStderrLog":
            ProcessStderrLog = server.supervisor.tailProcessStderrLog(name, 0, 20000)
            self.render("viewlogs.html", logs=ProcessStderrLog)

        if active == "readlog" :
            logs = []
            readLogs = server.supervisor.readLog(0, 2000)
            logs.append(readLogs)
            self.render("viewlogs.html", logs=logs)

#------------------------------------------------------------------------------
#项目管理
class ProjectsHandler(tornado.web.RequestHandler):
    def get(self):
        username = self.get_cookie("username", "")
        print username
        if not username:
            self.redirect("/login")
        else:
            conn = pymongo.Connection('192.168.100.100', 27017)
            db = conn.version
            projects = db.projects
            projects_info = projects.find({})
            print projects_info
            self.render("projects.html", projects_info=projects_info)

#------------------------------------------------------------------------------
#添加项目
class AddProjectHandler(tornado.web.RequestHandler):
    def get(self):
        username = self.get_cookie("username", "")
        print username
        if not username:
            self.redirect("/login")
        else:
            conn = pymongo.Connection('192.168.100.100', 27017)
            db = conn.version
            projects = db.projects
            projects_info = projects.find({})
            self.render("add_projects.html", projects_info=projects_info)

    def post(self):
        username = self.get_cookie("username", "")
        print username
        if not username:
            self.redirect("/login")
        else:
            project_code = self.get_argument("project_code", "")
            project_name = self.get_argument("project_name", "")
            project_path = self.get_argument("project_path", "")

            values = dict(
                project_code = project_code,
                project_name = project_name,
                project_path = project_path
                )

            conn = pymongo.Connection('192.168.100.100', 27017)
            db = conn.version
            projects = db.projects
            projects.insert(values)
            
            self.redirect("/projects/")

#------------------------------------------------------------------------------
#项目删除
class DelProjectHandler(tornado.web.RequestHandler):
    def get(self, argv):
        _id = argv
        username = self.get_cookie("username", "")
        print username
        if not username:
            self.redirect("/login")
        else:
            conn = pymongo.Connection('192.168.100.100', 27017)
            db = conn.version
            projects = db.projects
            projects.remove({'_id' : bson.ObjectId(_id)})
            self.redirect("/projects/")

#------------------------------------------------------------------------------
#游戏大区管理
class AreasHandler(tornado.web.RequestHandler):
    def get(self):
        username = self.get_cookie("username", "")
        print username
        if not username:
            self.redirect("/login")
        else:
            conn = pymongo.Connection('192.168.100.100', 27017)
            db = conn.version
            areas = db.areas
            areas_info = areas.find({})
            print areas_info
            self.render("areas.html", areas_info=areas_info)


#------------------------------------------------------------------------------
#添加游戏大区
class AddAreaHandler(tornado.web.RequestHandler):
    def get(self):
        username = self.get_cookie("username", "")
        print username
        if not username:
            self.redirect("/login")
        else:
            conn = pymongo.Connection('192.168.100.100', 27017)
            db = conn.version
            projects = db.projects
            projects_info = projects.find({})
            self.render("add_areas.html", projects_info=projects_info)

    def post(self):
        username = self.get_cookie("username", "")
        print username
        if not username:
            self.redirect("/login")
        else:
            app_type = self.get_argument("app_type", "")
            app_name = self.get_argument("app_name", "")
            server_ip = self.get_argument("server_ip", "")
            server_name = self.get_argument("server_name", "")
            process_name = self.get_argument("process_name", "")
            process_path = self.get_argument("process_path", "")

            values = dict(
                app_type = app_type,
                app_name = app_name,
                server_ip = server_ip,
                server_name = server_name,
                process_name = process_name,
                process_path = process_path
                )

            conn = pymongo.Connection('192.168.100.100', 27017)
            db = conn.version
            areas = db.areas
            areas.insert(values)
            
            self.redirect("/areas/")


#------------------------------------------------------------------------------
#游戏大区删除
class DelAreaHandler(tornado.web.RequestHandler):
    def get(self, argv):
        _id = argv
        username = self.get_cookie("username", "")
        print username
        if not username:
            self.redirect("/login")
        else:
            conn = pymongo.Connection('192.168.100.100', 27017)
            db = conn.version
            areas = db.areas
            areas.remove({'_id' : bson.ObjectId(_id)})
            self.redirect("/areas/")


#------------------------------------------------------------------------------
#用户登陆模块
class LoginHandler(tornado.web.RequestHandler):
    def get(self):
        error = ""
        username = self.get_cookie("username", "")
        print username
        if not username:
            self.render("login.html", error=error)
        else:
            self.redirect("/")
            
    def post(self):
        ldaphost = "192.168.5.5"
        domain = "kuen"

        username = self.get_argument("username", "")
        password = self.get_argument("password", "")
        remember = self.get_argument("remember", "")
        name = ""
        mail = ""

        print username
        print password
        print remember

        try:
            mail, result = checkuser(ldaphost, domain, username, password)

        except Exception, e:
            #raise e
            error = "Account or Password Is Wrong, Please try again"
            error_info = e
            mail = ""
            result = False
        else:
            error = ""
            pass

        if result:
            for i in result:
                if isinstance(i[1], dict):
                    mail =  i[1]['mail'][0]
                    name =  str(i[1]['name'][0]).decode('utf8')

                    self.set_cookie("username", "%s_%s_%s" % (username, name, mail), path = "/", expires_days =1)
            
            self.redirect("/")
        else:
            print error
            print error_info
            self.render("login.html", error=error)

#----------------------------------------------------------------------------
#主函数
def main():
    settings = dict(
            template_path=os.path.join(os.path.dirname(__file__), "templates"),
            static_path=os.path.join(os.path.dirname(__file__), "static"),
        )

    tornado.options.parse_command_line()
    application = tornado.web.Application([
        (r"/", MainHandler),
        (r"/release/", ReleaseHandler),
        (r"/serverinfo/", ServerInfoHandler),
        (r"/services/", ServicesHandler),
        (r"/projects/", ProjectsHandler),
        (r"/project_del/(.*)", DelProjectHandler),
        (r"/add_project/", AddProjectHandler),
        (r"/login", LoginHandler),
        (r"/areas/", AreasHandler),
        (r"/add_area/", AddAreaHandler),
        (r"/area_del/(.*)", DelAreaHandler),

    ], **settings)
    
    http_server = tornado.httpserver.HTTPServer(application)
    http_server.listen(options.port)
    tornado.ioloop.IOLoop.instance().start()
    
if __name__ == "__main__":
    main()
    
