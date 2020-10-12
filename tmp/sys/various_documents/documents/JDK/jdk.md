JDK设置



tomcat jdk设置



setclasspath.bat


```
window
set JAVA_HOME=/jetshine/jdk1.8.0_241_oracleJdk
set JRE_HOME=$JAVA_HOME/jre
set CLASSPATH=.:$JAVA_HOME/lib:$JRE_HOME/lib:$JAVA_HOME/lib/tools.jar
set PATH=$JAVA_HOME/bin:$JRE_HOME/bin:$PATH
```



```
linux
export JAVA_HOME=/jetshine/jdk1.8.0_241_oracleJdk
export JRE_HOME=$JAVA_HOME/jre
export CLASSPATH=.:$JAVA_HOME/lib:$JRE_HOME/lib:$JAVA_HOME/lib/tools.jar
export PATH=$JAVA_HOME/bin:$JRE_HOME/bin:$PATH
```
