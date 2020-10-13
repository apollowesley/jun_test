代码生成器一支
## 项目简介
- 修改配置文件`src/main/resources/generator.xml`指向正确的数据库/用户名/密码
- 通过JDBC读取数据库中的表信息, 传递给`freemarker`模板引擎以生成需要的文件
- 任何出现在模板文件夹: `src/main/resources/template`下的、包含`freemarker`语法的文件夹与文件， 都将被模板引擎解析执行, 输出相应结构的结果
- 对模板文件作相应的修改, 你就拥有了一键生成可执行代码的功能啦