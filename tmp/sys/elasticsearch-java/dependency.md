
## 安装

### Maven Repository

Elasticsearch Java API包已经上传到 [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22elasticsearch%22)

在`pom.xml`文件中增加：

> transport 版本号最好就是与Elasticsearch版本号一致。

```
<dependency>
    <groupId>org.elasticsearch.client</groupId>
    <artifactId>transport</artifactId>
    <version>5.6.3</version>
</dependency>
```