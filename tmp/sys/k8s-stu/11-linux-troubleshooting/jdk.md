# openjdk
https://jdk.java.net/11/
https://jdk.java.net/archive/


## step1: install openjdk
```
wget https://download.java.net/java/GA/jdk11/9/GPL/openjdk-11.0.2_linux-x64_bin.tar.gz
```

#add jdk env
```
tee >>~/.bash_profile <<-'EOF'
#add openjdk11
export JAVA_HOME=/root/jdk-11.0.2
export CLASSPATH=.:$JAVA_HOME/lib:$JAVA_HOME/jre/lib
export PATH=$JAVA_HOME/bin:$PATH
EOF
```

//tmp
```
export JAVA_HOME=/tmp/reindex/jdk-11.0.2
export CLASSPATH=.:$JAVA_HOME/lib:$JAVA_HOME/jre/lib
export PATH=$JAVA_HOME/bin:$PATH
```



