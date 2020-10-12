# Java-Anotation
Java注解开发，案例

### 1. Java中常见的注解

 **Jdk自带注解：** @Override @Deprecated @Suppvisewarnings

 **第三方注解：** Spring,SpringMVC,Mybatis,Hibernate....

### 2. Java注解分类
 
 **按照运行机制分：** 

- 源码注解： 注解只在源码中存在，编译后就不存在了
- 编译时注解： @Override @Deprecated @Suppvisewarnings 为编译时注解，只在编译的时候起作用，提示作用
- 运行时注解： 在运行阶段能起作用。

 **按照来源来分：** 

- 来自JDK的注解
- 来自第三方的注解
- 自定义的注解

 **注意：**  **_元注解_** 为给注解的注解


### 3. Java自定义注解开发

引自:[更多内容，来源于此博客](https://www.cnblogs.com/peida/archive/2013/04/24/3036689.html)

元注解的作用就是负责注解其他注解。

 **_@Target_** 

1.CONSTRUCTOR:用于描述构造器

2.FIELD:用于描述域

3.LOCAL_VARIABLE:用于描述局部变量

4.METHOD:用于描述方法

5.PACKAGE:用于描述包

6.PARAMETER:用于描述参数

7.TYPE:用于描述类、接口(包括注解类型) 或enum声明

 **_@Retention_** 
1.SOURCE:在源文件中有效（即源文件保留）

2.CLASS:在class文件中有效（即class保留）

3.RUNTIME:在运行时有效（即运行时保留）

 **_@Documented_** 

@Documented用于描述其它类型的annotation应该被作为被标注的程序成员的公共API，因此可以被例如javadoc此类的工具文档化。Documented是一个标记注解，没有成员。

 **_@Inherited_** 

@Inherited 元注解是一个标记注解，@Inherited阐述了某个被标注的类型是被继承的。如果一个使用了@Inherited修饰的annotation类型被用于一个class，则这个annotation将被用于该class的子类。



