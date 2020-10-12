
 <h2>Redis Session Manager for Apache Tomcat7 (tomcat7会话共享)</h2>
<br/>
<br/>
<hr/>
**以下是tomcat/conf/content.xml 中的配置**
<br/>
```
<Valve className="com.orangefunction.tomcat.redissessions.RedisSessionHandlerValve" />        
	<Manager className="com.orangefunction.tomcat.redissessions.RedisSessionManager" 
		host="localhost" 
		port="6379" 
		password="1234"
		database="0" 
		maxInactiveInterval="60"/>
```
<br/>
<hr/>
**复制以下这些包(/WebContent/WEB-INF/lib)到/tomcat/lib目录中:**
<br/><br/>
commons-logging-1.1.3.jar<br/>
tomcat-redis-session-manage-tomcat7.jar<br/>
jedis-2.5.2.jar<br/>
commons-pool2-2.2.jar<br/>
tomcat-juli.jar<br/>
<br/>
启动redis并启动tomcat.<br/>