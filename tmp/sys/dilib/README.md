# dilib 简易接口访问工具

* 当前版本：1.0.2
* 构建时间：2016.3.29
* 主页：https://git.oschina.net/loongzcx/dilib

## 项目依赖包

* cglib 3.2.0
* gson 2.6.1
* httpclient 4.5.1
* commons-logging 1.2

## API文档

### 1. package com.loong.dilib

* ApiFactory
    * Api实体工厂类
    * 方法：
     * Object createApi(String className) 创建API实体对象
     * &lt;T&gt; T createApi(Class&lt;T&gt; targetClass) 创建API实体对象
* ApiProxyFactory
    * Api代理工厂类
    * setCache(ApiCache cache) 添加缓存处理器
    * setShowLog(Boolean showLog) 是否打印日志（默认不打印）
    * createApiProxy() 创建Api代理类

### 2. package com.loong.dilib.annotation

* @ DIRequest
    * 接口请求注释
    * 用于 API接口类、API接口方法
    * DIRequest.Method
        * 请求方式类型
        * GET（默认）、POST
* @ DIResponse
    * 接口响应注释
    * 用于 API接口类、API接口方法
    * DIResponse.Type
        * 响应格式化类型 
        * STRING - 字符串响应类型（不用使用，API接口方法直接返回String类型即可）
        * JSON - JSON格响应式类型（默认）
        * JSONP - JSONP格式响应类型
        * ~~XML~~ - XML响应类型（暂不支持）
* @ DIHeader
    * 请求头部信息注释
    * 用于 API接口方法参数注释
* @ DICookie
    * 请求Cookie信息注释
    * 用于 API接口方法参数注释
* @ DIJson
    * 请求体JSON参数注释
    * 用于 API接口方法参数注释 
* @ DIPath
    * REST参数注释
    * 用于 API接口方法参数注释
* @ DIParam
    * 请求参数注释
    * 用于 API接口方法参数注释
    * 主要用于标明简单访问参数的参数名
* @ DICache
    * 缓存配置注释
    * 用于 API接口类、API接口方法
    * 缓存时间单位：秒


### 3. package com.loong.dilib.exception

* DIAnnotationException
    * API接口注释异常
    * 如果API接口注释有问题，会直接在API创建时抛出
* DIConnectException
    * 接口访问失败异常，接口访问失败时抛出
* DIFormatException
    * 响应信息格式化异常，接口响应后，响应体格式化失败时抛出

### 4. package com.loong.dilib.cache

* ApiCache
    * API缓存处理类接口
* AbstractApiCache
    * API缓存处理抽象类
    * 实现了 buildKey() 方法
* MemoryApiCache
    * 简单内存缓存处理器 

## 使用说明

### 简单例子：

API接口类：

```java
public interface TestApi {

    @DIRequest("http://xxx/test")
    public String test(@DIParam("p") int value);
    
    @DIRequest("http://xxx/test2")
    public String test2(Bean bean);
}
```

```java
public class Bean {

    private String p1;
    private String p2;
    
    // get set
    // ......
}
```

简单使用：

```java
TestApi api = ApiFactory.createApi(TestApi.class);
String html = api.test(1);

Bean bean = new Bean();
bean.setP1("111");
bean.setP2("222");
String htnl2 = api.test2(bean);
```

1. 调用 `api.test(1)` 相当于GET方式访问 http://xxx/test?p=1
2. 调用 `api.test2(bean)` 相当于GET方式访问 http://xxx/test2?p1=111&p2=222

Spring环境下：

```xml
<bean id="testApi" class="com.loong.dilib.ApiFactory" factory-method="createApi">
    <constructor-arg value="com.test.TestApi" />
</bean>
```

```java
@Service
public interface Test {

    @Resource
    private TestApi api;

    public String test() {
    
        String html = api.test(1);
    }
}
```

### 复杂点的例子：

```java
@DIRequest("http://xxx")
@DIResponse(Type.JSONP)
public interface TestApi {

    @DIRequest(value = "test", method = DIRequest.Method.POST)
    @DIResponse
    @DICache(3600)
    public Result<Test> test(
        @DIHeader Map<String, ?> h1,
        @DIHeader HBean h2,
        @DIHeader("xxx") String h3,
        @DICookie Map<String, ?> c1,
        @DICookie CBean c2,
        @DICookie("xxx") String c3,
        @DIJson PBean p
    );

    @DIRequest(value = "test2", method = DIRequest.Method.POST, charset = "GBK")
    @DICache(60)
    public Map<String, Linked<Test2>> test2(
        @DIHeader Map<String, ?> h1,
        @DIHeader HBean h2,
        @DIHeader("xxx") String h3,
        @DICookie Map<String, ?> c1,
        @DICookie CBean c2,
        @DICookie("xxx") String c3,
        Map<String, ?> p1,
        PBean p2,
        @DIParam("xxx") String p3
    );

    @DIRequest(value = "test3/{xxx}/{x2}/{x3}", method = DIRequest.Method.POST)
    public Map<String, Linked<Test2>> test3(
        @DIHeader HBean h,
        @DICookie CBean c,
        PBean p,
        @DIPath Map<String, ?> path1,
        @DIPath PathBean path2,
        @DIPath("xxx") String path3,
    );
}
```

说明：

1. @DIHeader
    * 标明方法参数是请求头
    * 一个方法里可以有多个
    * 使用方式：
        * `example(@DIHeader Map<String, ?> h)`
        * `example(@DIHeader Bean h)`
        * `example(@DIHeader("name") String h)`
2. @DICookie
    * 标明方法参数是请求Cookie
    * 一个方法里可以有多个
    * 使用方式：
        * `example(@DICookie Map<String, ?> c)`
        * `example(@DICookie Bean c)`
        * `example(@DICookie("name") int c)`
3. @DIPath
    * 标明方法参数是REST参数
    * 一个方法里可以有多个
    * 参数会通过 `name` 替换 `@DIRequest` 中访问路径被标识 `{name}` 的值
    * 多余的字段会被忽略（没有找到对应的`{name}`）
    * 使用方式：
        * `example(@DIPath Map<String, ?> p)`
        * `example(@DIPath Bean p)`
        * `example(@DIPath("name") String p)`
4. 访问请求参数
    * 被 `@Param` 注释 或 没有被 `@DIHeader`、`@DICookie`、`@DIPath` 注释的字段，都会被认定为是请求参数
    * 一份方法里可以有多个
    * 使用方式：
        * `example(Map<String, ?> p)`
        * `example(Bean p)`
        * `example(@Param("name") String p)`
5. @DIJson
    * 一般用于复杂请求参数的传递
    * 被注释的参数会转换为 Json 格式，加入到 Request Body 里面
    * 只能在 POST 方式中使用
    * 会自动添加头部信息 `Content-Type = application/json`
    * 一个方法里只能有一个
    * 使用方式：
        * `example(@DIJson Map<String, ?> p)`
        * `example(@DIJson Bean p)`
6. @DIRequest
    * 用户注释请求路径及请求方式
    * 接口类上的 `@DIRequest` 的 `method`、`charset` 参数无效，只有接口方法上的有效
    * DIRequest.Method.GET 为默认请求方式
    * 默认编码方式为  `UTF-8 `
7. @DIResponse 
    * 如果接口方法的返回类型为 `String`，会将 Response Body 的内容直接返回
    * 可以使用在接口类及接口方法上，若接口方法上没有使用，则会取接口类上的注释，接口类上没有使用则使用默认值
    * Type.JSON 是默认的处理方式
    * Type.JSONP 是针对 JSonp 接口的处理
8. @DICache
    * 具体说明见下方

## API实体类的创建方式

###方法一：

简单方式

```java
TestApi api = ApiFactory.createApi(TestApi.class);
TestApi2 api2 = ApiFactory.createApi(TestApi2.class);
```

###方法二：

Api代理工厂方式创建

```java
// 新建API代理工厂
ApiProxyFactory proxyFactory = new ApiProxyFactory();
// proxyFactory.setShowLog(true);

// 创建代理类
ApiProxy proxy = proxyFactory.createApiProxy();

TestApi api = proxy.createApi(TestApi.class);
TestApi2 api2 = proxy.createApi(TestApi2.class);
```

## 缓存配置

### 简单使用方式

1. 在 API方法（或者API接口类）上添加 `@DICache` 注释（时间以秒为单位）
2. 使用 API代理工厂方式创建 API实体处理类
3. 在 API代理工厂中注入缓存处理器

```java
// 缓存处理器
ApiCache apiCache = new MemoryApiCache();
// API代理工厂
ApiProxyFactory proxyFactory = new ApiProxyFactory();
proxyFactory.setCache(apiCache);
```

### 自定义缓存处理器

1. 必须实现 `ApiCache` 接口或继承 `AbstractApiCache` 抽象类
2. `ApiCache` 接口中包含三个方法：
    * buildKey(Object api, Method method, Object[] params) 构建存储键
    * put(Object api, Method method, String key, String html, int expire) 添加缓存
    * get(Object api, Method method, String key) 获取缓存
    * 参数：
        * `Object api` API接口实体类
        * `Mehtod method` API接口方法
        * `Object[] params` 方法访问参数集
        * `String key`通过 `buildKey()` 构建的缓存键
        * `String html` 接口响应信息
        * `int expire` 过期时间（秒）
3. `AbstractApiCache` 抽象接口已实现了 `buildKey()` 方法
4. `MemoryApiCache` 是已经写好的一个简单的缓存处理器，对于少量数据的情况可以直接使用。

备注：

1. 最好还是使用 Redis 这样的 Key-Value数据库来保存缓存
2. `AbstractApiCache` 现在的 `buildKey()` 还有待优化，主要是对于参数是Bean类型的，并且Bean里面包含Map或者Set的情况。
3. `AbstractApiCache` 中构建的缓存键已经可以区分接口类和接口方法，因此如果用继承抽象类的方式，在 `put()` 和 `get()` 中，可以直接忽略掉方法中的 `Object api` 和 `Mehtod method` 参数（当然用来分类储存也可以）。
4. ps: 其实参数 `Object api` 基本没啥用...从 `Mehtod method` 中其实可以取到接口类型的，暂时保留方便直接使用。

## 版本更新历史
* 1.0.2
    * **新增缓存及缓存拓展机制**
    * `@DIRequest` 注释中新增 `charset()` 编码设置
    * 新增 `ApiProxyFactory` API代理工厂类
    * 新增请求日志打印及打印设置
* 1.0.1
    * **新增 REST 访问方式的处理**
    * 废弃 `@DIN` 注释，新增 `@DIParam` 注释
    * 优化对 `Map<String, ?>` 的处理（以前只支持`Map<String, String>`）
* 1.0.0
    * **基础版本**

## 新版本预期
* 增加对 XML 的支持

## 鸣谢
* 感谢 Longlive 提供了很多很好的修改建议。
