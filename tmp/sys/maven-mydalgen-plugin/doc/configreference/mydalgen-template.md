
mydalgen模板文件，是整个插件最核心的一部分。MyBatis的`Mapper XML`，`DO类`以及`DAO接口`都是通过解析模板文件生成的。

模板文件的格式为XML，同时我提供了xsd文件辅助模板文件的编写工作。下面是一个mydalgen模板文件`uc_user_info.xml`的例子，查阅每个元素单独的页面查看更多有关元素的更多信息和属性值。

```
<?xml version="1.0" encoding="UTF-8"?>
<table xmlns="http://smartboot.oschina.io/schema/mydalgen/table" 
	   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	   xsi:schemaLocation="http://smartboot.oschina.io/schema/mydalgen/table 
	    	http://smartboot.oschina.io/schema/mydalgen/table.xsd"
	   sqlname="uc_user_info">

	<operation name="insert" paramType="object">
		<sql><![CDATA[
			insert into uc_user_info(id,name,sex,birthday,phone,remark,createUser,createTime,modifyUser,modifyTime,deleted)
			values(?,?,?,?,?,?,?,now,?,now,0);
      ]]></sql>
		<sqlmap>
	  <![CDATA[
		insert into uc_user_info(id,name,sex,birthday,phone,remark,createUser,createTime,modifyUser,modifyTime,deleted)
			values(#{id},#{name},#{sex},#{birthday},#{phone},#{remark},#{createUser},now(),#{modifyUser},now(),0);
      ]]>
		</sqlmap>
	</operation>

	<operation name="updateById" paramType="object">
		<sql><![CDATA[
			update uc_user_info set modifyTIme=now;
      ]]></sql>
		<sqlmap>
	  <![CDATA[
		update uc_user_info set modifyTime=now()
			<if test="name != null">
				,name=#{name}
			</if>
			<if test="sex != null">
				,sex=#{sex}
			</if>
			<if test="birthday != null">
				,birthday=#{birthday}
			</if>
			<if test="phone != null">
				,phone=#{phone}
			</if>
			<if test="remark != null">
				,remark=#{remark}
			</if>
			<if test="modifyUser != null">
				,modifyUser=#{modifyUser}
			</if>
			where id=#{id} and deleted=0
      ]]>
		</sqlmap>
	</operation>
	
	<operation name="selectByCondition" multiplicity="many">
		<extraparams>
			<param name="query"
				javatype="net.vinote.user.dal.customize.query.UserInfoQuery" />
		</extraparams>
		<sql><![CDATA[
			select id,name,sex,birthday,phone,remark,createUser,createTime,modifyUser,modifyTime,deleted from uc_user_info 
      ]]></sql>
		<sqlmap>
	  <![CDATA[
		select id,name,sex,birthday,phone,remark,createUser,createTime,modifyUser,modifyTime,deleted from uc_user_info
			where deleted='0'
			<if test="query.id != null">
				and id=#{query.id}
			</if>
			<if test="query.name != null">
				and name like concat('%',#{query.name},'%')
			</if>
			<if test="query.sex != null">
				and sex=#{query.sex}
			</if>
			<if test="query.phone != null">
				and phone=#{query.phone}
			</if>
			order by createTime desc 
			<if test="query.rows > 0">
			  limit #{query.offset},#{query.rows} 
			</if>
      ]]>
		</sqlmap>
	</operation>
	
	<operation name="selectById" multiplicity="one" paramType="primitive">
		<sql><![CDATA[
			select id,name,sex,birthday,phone,remark,createUser,createTime,modifyUser,modifyTime,deleted from uc_user_info where id=?
      ]]></sql>
		<sqlmap>
	  <![CDATA[
		select id,name,sex,birthday,phone,remark,createUser,createTime,modifyUser,modifyTime,deleted from uc_user_info
			where deleted='0' and id=#{id}
      ]]>
		</sqlmap>
	</operation>
	
</table>
```

