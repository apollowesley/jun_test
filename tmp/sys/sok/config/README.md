#配置文件说明
应用中获取全部配置信息 \sok\app::config();
获取数据库配置信息 \sok\app::config('database');
如果有特性的配置要求，可以在这个目录创建自定义文件名，然后在应用中可以通过\sok\app::config(自定义文件名)获取参数
app.php //应用配置
database.php //mysql配置请写到 
http.php //http服务配置
log.php //日志配置
redis.php //redis配置
route.php //路由配置
session.php //session配置
websocket.php //websocket服务配置 ，websocket服务同时提供的http服务也配置在这里

