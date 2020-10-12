#jUtils

### 写在前面
- 团队内部使用的一些实用java工具,特分享与开源者共勉.

### 目的
- 当你开发中遇到一些问题,例如需要格式化时间,导入excel,校验邮件格式等问题时,你的第一反应是自己手动写,还是网上找答案,还是问同事,还是使用自己沉淀下来的技术积累?每个人有自己的答案,该项目只是给大家提供一种思路和方法.
- 项目中很多实用类提供了解决某一类问题的最佳实践或者解决问题的思路.
- 不要自造轮子,除非这个轮子确实需要造.

### 核心思路
- 从语义上抽象实用方法,例如你的工程中需要检验一个电子邮件地址是否合法,你的方法可能看起来像这样:

```
public static boolean isEmailValid(String email){
    //你应该修改和迭代的是这里.
}
```

- 在学习的不同阶段,对于这个方法你会有不同的实现.

有可能是这样:
```
import java.util.regex.Pattern;
public static boolean isEmailValid(String email){
    return Pattern.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", email);
}
```

也有可能是这样:
```
import org.apache.commons.validator.routines.EmailValidator;
public static boolean isEmailValid(String email){
    if (email == null) { return false;}
    return EmailValidator.getInstance().isValid(email);
}
```

- 项目逐渐变大的时候,这个方法会逐渐成为基石方法,我们的目标就是提供一种不停迭代的最佳实践,让你可以借鉴和引入自己的项目.

### 如何使用,注意事项
- 使用Git Clone工程,任何能够导入maven工程的ide直接导入工程即可.
- 推荐ide:IntelliJ IDEA.
- 正确配置/src/main/resources路径下的global-config.properties文件中的git和maven命令路径.
- 工程需要jdk8支持.
- 使用时请首先查看pom文件中依赖是否与自身一致.
- 涉及操作系统级的实用工具类和方法,例如文件io,仅支持osx以及windows,其它平台未充分测试.

### 代码结构
- 所有实用类在/jUtils/src/main/java/com/foo/common/base/utils下面找到.
- 实用类的命名形式:{{UtilsCategory}}Helper
- 例如与apache maven相关的实用类命名就是:MvnHelper,与apche poi处理Excel相关的就是ExcelHelper.

### 实用工具类一览
- PomHelper:解析pom.xml文件，获取pom的所有节点信息以进行操作. 例如:analyzePomDependency:实时从中央仓库获取最新依赖与本地进行对比.
 analyzePomPlugin:实时从中央仓库获取最新插件依赖与本地进行对比,等等.
 
- ValidationHelper:常用后台校验的一些简单示例.

- FooUtils:通用工具集,例如生成uuid,格式化double为2位小数点,等等.

- GitHelper:执行git命令例如diff,log等,是自动打包等功能的基类.

- ExcelHelper:excel的一些导入导出实例,也包含图片导入解析等复杂实例.

- FtpHelper:ftp操作,连接服务器,上传文件等.

- MacHelper:直接执行一些mac命令,例如显示隐藏文件夹等.

- CommonClassCompileHelper:操作java的编译后的class,以及读取java文件获取包信息等.

- More...


### 如果你也感兴趣
平时工作比较忙,诚邀osc'er一起开发维护.