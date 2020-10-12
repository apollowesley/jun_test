# mybatis-plus-generate

#### 介绍
利用mybatisplus自动生成代码工具，结合springboot2.x、lombok，swagger2进行快速代码生成，使用freemaker模板生成，模板均可修改，配置文件generate.properties一次配置，无需代码修改，即可生成，包含controller(具备restful风格),entity,mapper,mapper.xml,service

#### 软件架构
软件架构说明


#### 安装教程

1.  直接导入使用

#### 使用说明

1.  目前生成的Controller，具备新增，修改，删除，条件查询，分页查询，单一详情
2.  数据库表结构如果不具备乐观锁version，创建时间gmt_create，更新时间gmt_modified，逻辑删除deleted四个字段，请将datatables.isNormalize改为false
3.  配置文件支持多表同时生成，需要逗号隔开
4.  MyMetaObjectHandler类中对应创建时间和更新时间的机制
5.  controller中的wapper推荐使用LambdaQueryWrapper



#### 码云特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5.  码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
