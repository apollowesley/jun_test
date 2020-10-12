项目开发环境:nodejs,express
1.安装nodejs,
2.安装express应用生成器 node install express-generator -g

安装项目:
1.clone
2.npm install
3.运行项目: node ./bin/www

访问项目:http://localhost:3000

代码说明:
1.创建一个html文件
2.加载外部文件
    link(rel='stylesheet', href='/stylesheets/style.css')
    link(rel='stylesheet', href='/stylesheets/jg.css')
    script(type='text/javascript' src='/javascripts/jquery-1.11.0.min.js')
    script(type='text/javascript' src='/javascripts/jgUtil.js')
    script(type='text/javascript' src='/javascripts/jg2.js')
3.在页面中创建一个带有id的div 
    执行JG.init('mainDiv');即可

说明:
    项目使用express
    
    数据库连接:修改mysqlUtil.js文件中关于mysql数据库的配置信息
    代码生成:访问localhost:3000/db,可以获取到前一步骤配置的数据库中数据表信息,可以进行代码生成(目前只实现了pojo,dao);生成代码时可通过
        config.js文件修改相关配置.template.js为代码生成的具体实现