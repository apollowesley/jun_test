一个基于sql语句的导出工具,虽然很多数据库已经支持sql语句导出到excel了,但我们小公司还是不希望工程人员去直接操作数据库,一来不安全,二来也可能环境不允许,如数据库可能单独放置在机房,一般不允许进出.

直接在软根目录sql-module文件夹下放置sql语句,软件在启动时就会根据这个文件夹下的sql语句生成单独的导出模块,模块的名称即为sql语句的文件名,数据表格即为sql语句查询的列名,数据内容即为sql语句查询的结果集.因为数据表格的列名直接取自sql语句,所有在编写sql时,需单独为每一个列添加一个别名.

如 sql-module 文件夹下存在如下sql文件:用户信息导出.sql

```
select cu.identifier as '编号',cu.name as '名称',cu.birthday as '出生日期' from CardUser cu
```
则启动软件时会看到界面多了一个名称为"用户信息导出.sql"的模块,如下图:
![输入图片说明](http://git.oschina.net/uploads/images/2016/0310/175913_0d7fcb5a_87848.png "在这里输入图片标题")

![输入图片说明](http://git.oschina.net/uploads/images/2016/0310/175953_4a5261a7_87848.png "在这里输入图片标题")

单击保存按钮可直接将数据表格中的内容导出来excel.这是导出成功后的提示:
![输入图片说明](http://git.oschina.net/uploads/images/2016/0310/180112_444bc26c_87848.png "在这里输入图片标题")

因为这个小软件是基于javafx做的,所以也依赖于作者另一个javafx窗口装饰器,这是一个很漂亮的头饰器,有兴趣的朋友可以看一下:
javafx窗口装饰器 http://git.oschina.net/panmingzhi/javafx-window