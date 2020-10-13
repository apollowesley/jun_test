/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.oschina.durcframework.easymybatis;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import junit.framework.TestCase;
import net.oschina.durcframework.easymybatis.dao.CrudDao;
import net.oschina.durcframework.easymybatis.ext.code.client.ClassClient;

@Table(name = "stu")
class Stu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private int age;

	@Column(name = "create_time")
	private Date createTime;
	private String userName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}

interface StuDao extends CrudDao<Stu>{}

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

	public void testGen() {
		System.out.println(Stu.class.getPackage().getName());
		
		EasymybatisConfig config = new EasymybatisConfig();
		config.setCamel2underline(false);
		ClassClient classClient = new ClassClient(config);
		
		String xml = classClient.genMybatisXml(StuDao.class, "/easymybatis/tpl/mysql.vm","/global.vm");

		System.out.println(xml);

	}
}
