# 笔记
### TortoiseGit安装与配置

http://blog.csdn.net/renfufei/article/details/41647937


### maven项目中添加自己的jar包

mvn install:install-file   -Dfile=E:\maven\ecard-client-0.0.2.jar   -DgroupId=com.n3.ecs.jf  -DartifactId=java-bloomfilter -Dversion=0.0.2  -Dpackaging=jar

```
-Dfile：指明你当前jar包的位置（就是第1步存放jar的路径+jar包名）；
 -DgroupId， groupId
 -Dversion：版本号
```
### 
 **maven打包时跳过测试** 
```
mvn install -Dmaven.test.skip=true
mvn clean -Dmaven.test.skip=true package -P prod

//不跳过测试
mvn clean package
```



### windows装（glup打包）
https://www.cnblogs.com/liubin0509/p/5710822.html

 **安装nodejs** 
[https://nodejs.org/en/](https://nodejs.org/en/)
```
`安装：打开nodejs官网，点击硕大的绿色Download按钮，它会根据系统信息选择对应版本（.msi文件）。然后像安装QQ一样安装它就可以了（安装路径随意）`

node -v查看安装的nodejs版本，出现版本号，说明刚刚已正确安装nodejs
npm -v查看npm的版本号，npm是在安装nodejs时一同安装的nodejs包管理器
npm（node package manager）nodejs的包管理器，用于node插件管理（包括安装、卸载、管理依赖等）


使用npm安装插件：命令提示符执行npm install <name> [-g] [--save-dev]；
<name>：node插件名称。例：npm install gulp-less --save-dev

-g：全局安装
非全局安装：将会安装在当前定位目录,全局安装可以通过命令行在任何地方调用它，本地安装将安装在定位目录的node_modules文件夹下，通过require()调用；

使用npm卸载插件：npm uninstall <name> [-g] [--save-dev] PS：不要直接删除本地插件包
使用npm更新插件：npm update <name> [-g] [--save-dev]
查看npm帮助：npm help
当前目录已安装插件：npm list

#国内npm安装地址
npm install cnpm -g --registry=https://registry.npm.taobao.org
cnpm跟npm用法完全一致，只是在执行命令时将npm改为cnpm（以下操作将以cnpm代替npm）

#全局安装gulp
安装：命令提示符执行cnpm install gulp -g；
查看是否正确安装：命令提示符执行gulp -v，出现版本号即为正确安装。


#新建package.json文件
cnpm init


```


### Tomcat相关问题
使用高版本Tomcat时报Invalid character found in the request target. The valid characters are defined in RFC 3986的400错误
```
Invalid character found in the request target. The valid characters are defined in RFC 7230 and RFC 3986
```
最新的tomcat6 7 8 都有这个问题，这个问题是由于tomcat的新版本增加了一个新特性，就是严格按照 RFC 3986规范进行访问解析，而 RFC 3986规范定义了Url中只允许包含英文字母（a-zA-Z）、数字（0-9）、-_.~4个特殊字符以及所有保留字符(RFC3986中指定了以下字符为保留字符：! * ’ ( ) ; : @ & = + $ , / ? # [ ])。

 **解决方案** 
- Tomcat 配置文件 catalina.properties上配置
- tomcat.util.http.parser.HttpParser.requestTargetAllow=|{}
- 就可以解决问题了。

### git使用笔记-fatal:multiple stage entries for merged file处理办法
该错误是在cherry-pick时出现 无法确定冲突原因 分支无法checkout ，reset等等全都失效
在网上给出的解决办法全部都是
```
rm .git/index
git add -A 
git commit
```

