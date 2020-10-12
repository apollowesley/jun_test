### 背景

1个礼拜看完Mybatis源码，1个礼拜翻完一本书，去千岛湖玩了一趟，回来2天写完。

认真琢磨了一段时间把整个事情想清楚了再动手，现在很多软件开发领域的概念都是空洞无用的

程序员要有自己的思想！

#MyRWSplit
技术选型: Mybatis(ORM框架)  + 插件Plugin(捕捉各种感兴趣的东西) + Druid(阿里产品的数据库连接池)


###  **参与人员:** 

刘志强 (按照姓名顺序，本排名不分先后)
 

### 产品简介

1)设置数据源格式
```
<property name="url"
					value="jdbc:mysql://[1.1.1.1:3306,2.2.2.2:3306,3.3.3.3:3306]/ambari?zeroDateTimeBehavior=convertToNull" />
				
```
第1个IP:Port为master,剩下的是slave

2)读写规则

2.1）开启事务--->走master

2.2) 未开启事务

   2.2.1)增/修/删 --->走master

   2.2.2)查询,指定hint   /*FORCE_MASTER*/   --->走master
![输入图片说明](https://git.oschina.net/uploads/images/2017/0427/180129_9336fcb5_70679.png "在这里输入图片标题")

   2.2.3)查询，未指定hint ---> 走slave,轮询每个slave,取第1个可用的connection,此处支持slave的负载均衡算法。

### 代码片段

1)事务片段
```
package transaction;

import org.apache.ibatis.session.SqlSession;
import com.freedom.mysql.myrwsplit.bean.Role;
import com.freedom.mysql.myrwsplit.helper.LoggerHelper;
import com.freedom.mysql.myrwsplit.interfaces.RoleMapper;
import com.freedom.mysql.myrwsplit.runnable.MyRwSplitTransactionRunnable;

public class MyRwSplitWithTransactionAdvanced {
	private static LoggerHelper LOGGER = LoggerHelper.getLogger(MyRwSplitWithTransactionAdvanced.class);

	public static void main(String[] args) {
		// 初始化时，请设定你需要的参数，比如:Executor类型,事务级别
		Role result = new MyRwSplitTransactionRunnable<Role>() {
			@Override
			public Role execute(SqlSession sqlSession) {
				// 从这里,开始写任何你需要的的业务代码,
				// 处于一个事务里，事务相关的东西，业务不需要关心,框架已经做好了
				RoleMapper userMapper = sqlSession.getMapper(RoleMapper.class);// 获得mapper
				// delete
				Role role = new Role();
				role.setId(13);
				userMapper.deleteRole(role);
				// insert
				role.setId(13);
				role.setTitle("xxx");
				role.setAuthor("yyy");
				userMapper.insertRole(role);
				// select
				role = userMapper.getRole0(11);
				return role;
			}
		}.run();
		//
		LOGGER.debug("" + result);
	}
}

```

2)非事务片段
```
package notransaction;

import com.freedom.mysql.myrwsplit.bean.Role;
import com.freedom.mysql.myrwsplit.helper.LoggerHelper;
import com.freedom.mysql.myrwsplit.helper.MapperUtils;
import com.freedom.mysql.myrwsplit.interfaces.RoleMapper;

public class MyRwSplitWithNoTransactionAdvanced {
	@SuppressWarnings("unused")
	private static LoggerHelper LOGGER = LoggerHelper.getLogger(MyRwSplitWithNoTransactionAdvanced.class);

	// 在读写分离的情况下，必须重新获取mapper,才可以支持每次操作都重新判断来决定从master/slave来获取新的连接
	// 如果连续运行就会报错
	public static void main(String[] args) {
		//
		RoleMapper mapper = MapperUtils.getMapper(RoleMapper.class);
		Role role = mapper.getRole0(13);

		//
		mapper = MapperUtils.getMapper(RoleMapper.class);
		role = new Role();
		role.setAuthor("xxx");
		role.setTitle("yyy");
		mapper.insertRole(role);

	}
}

```

###  **友情链接** 
http://www.jianshu.com/p/2222257f96d3#

https://help.aliyun.com/document_detail/51073.html?spm=5176.doc51070.6.678.YN7j24 --- 阿里云的读写分离

https://help.aliyun.com/document_detail/29681.html?spm=5176.doc52132.6.554.WzsOR9 --- 阿里云的DRDS

https://help.aliyun.com/knowledge_detail/52221.html --- 主从的时效性

https://help.aliyun.com/document_detail/51244.html?spm=5176.doc29681.2.3.bS14zz ---  编写Hint

### QA:常见问题
1)master挂了怎么办?

A: 挂了我也没办法，接入统一监控早点发现master挂了然后让运维启动机器吧，这段时间内不可写，不影响读。

2)slave挂了怎么办？

A: 假设有N台slave,只要有1台存活，都可以完成读操作。N台都挂了怎么办？机器都挂了我也没办法，启动吧。

3)有无重试机制？

A: 针对slave的读操作，有重试机制，只要至少1台slave在，就可以完成读操作。

4)有无告警机制?

A: 这属于统一监控的范畴，不在此项目范围内，有兴趣的可以参考本人的另外一个统一监控MyEye.

5)有无slave落后探测机制?

A:暂时没有实现，需要的话参考:https://my.oschina.net/kkrgwbj/blog/614446


### 扩展阅读:

1)https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-master-slave-replication-connection.html