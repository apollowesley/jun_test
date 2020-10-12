package com.foo.common.base.utils.laboratory;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Assert;
import org.springframework.core.io.ClassPathResource;

import com.foo.common.base.pojo.Area;
import com.foo.common.base.pojo.OracleUserTabColumnsModel;
import com.foo.common.base.pojo.SysArea;
import com.foo.common.base.utils.FooUtils;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class CityDataHelper {
	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(CityDataHelper.class);

	private final static Date date = new Date();

	private static Session session;

	public static void main(String[] args) throws IOException {

		ClassPathResource myPath = new ClassPathResource(
				"hibernate-localhost-mysql.properties");

		Assert.assertTrue(myPath.exists());

		Properties p = new Properties();
		p.load(myPath.getInputStream());

		Configuration configuration = new Configuration().setProperties(p);

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();

		SessionFactory myFactory = configuration
				.addAnnotatedClass(OracleUserTabColumnsModel.class)
				.addAnnotatedClass(SysArea.class)
				.buildSessionFactory(serviceRegistry);

		session = myFactory.openSession();

		Transaction tx = null;

		tx = session.beginTransaction();

		Gson gson = new GsonBuilder().create();
		String source = IOUtils.toString(FooUtils
				.getClassPathResourceInputStream("cityData.json"));
		List<Area> areas = gson.fromJson(source, new TypeToken<List<Area>>() {
		}.getType());
		// FooUtils.pretyPrint(areas);

		saveAreaProvinceLevel(areas, "1", "");

		tx.commit();
		session.close();
		myFactory.close();

	}

	private static void saveAreaProvinceLevel(List<Area> provinces, String pId,
			String currentMaxDictId) {

		String areaId;
		currentMaxDictId = pId;
		int orderNo = 0;
		for (Area province : provinces) {
			areaId = getDictIdFromPid(pId, currentMaxDictId);
			currentMaxDictId = areaId;
			List<Area> cities = Lists.newArrayList(province.getS());
			SysArea sysArea = new SysArea();
			sysArea.setAreaName(province.getN());
			sysArea.setCreateTime(date);

			sysArea.setAreaId(areaId);
			sysArea.setInterfaceId("");
			if (cities.size() >= 1) {
				sysArea.setLeaf(0);// 父节点
			} else {
				sysArea.setLeaf(1);// 子节点
			}
			sysArea.setOrderNo(orderNo++);
			sysArea.setState(1);
			sysArea.setParentAreaId(pId);
			sysArea.setTreeLevel(1);
			sysArea.setTreeOpen(true);
			session.save(sysArea);

			logger.info("current province is:{}", areaId);

			saveAreaCityLevel(cities, areaId, "");
		}

	}

	private static void saveAreaCityLevel(List<Area> cities, String pId,
			String currentMaxDictId) {
		String areaId;
		int orderNo = 0;
		for (Area city : cities) {
			areaId = getDictIdFromPid(pId, currentMaxDictId);
			currentMaxDictId = areaId;

			List<Area> towns;
			if (city.getS() == null) {
				towns = Lists.newArrayList();
			} else {
				towns = Lists.newArrayList(city.getS());
			}

			SysArea sysArea = new SysArea();
			sysArea.setAreaName(city.getN());
			sysArea.setCreateTime(date);

			sysArea.setAreaId(areaId);
			sysArea.setInterfaceId("");
			if (towns.size() >= 1) {
				sysArea.setLeaf(0);// 父节点
			} else {
				sysArea.setLeaf(1);// 子节点
			}
			sysArea.setOrderNo(orderNo++);
			sysArea.setState(1);
			sysArea.setParentAreaId(pId);
			sysArea.setTreeLevel(2);
			sysArea.setTreeOpen(true);
			session.save(sysArea);

			logger.info("current city is:{},and name is:{}", areaId,
					sysArea.getAreaName());

			saveAreaTownLevel(towns, areaId, "");
		}

	}

	private static void saveAreaTownLevel(List<Area> towns, String pId,
			String currentMaxDictId) {
		String areaId;
		for (Area town : towns) {
			areaId = getDictIdFromPid(pId, currentMaxDictId);
			currentMaxDictId = areaId;

			SysArea sysArea = new SysArea();
			sysArea.setAreaName(town.getN());
			sysArea.setCreateTime(date);

			sysArea.setAreaId(areaId);
			sysArea.setInterfaceId("");
			sysArea.setLeaf(1);// 子节点
			sysArea.setOrderNo(0);
			sysArea.setState(1);
			sysArea.setParentAreaId(pId);
			sysArea.setTreeLevel(3);
			sysArea.setTreeOpen(true);
			session.save(sysArea);

			logger.info("current town is:{},and name is:{},and pid is:{}",
					areaId, sysArea.getAreaName(), pId);

		}
	}

	private static String getDictIdFromPid(String pid, String currentMaxDictId) {

		if (pid.equals("1") && currentMaxDictId.equals("1")) {
			return "1001";
		}

		if (Strings.nullToEmpty(currentMaxDictId).equals("")) {
			return pid + "001";
		}

		int tmpMax = Integer.parseInt(currentMaxDictId.substring(
				currentMaxDictId.length() - 3, currentMaxDictId.length()));
		if (tmpMax >= 998) {
			throw new RuntimeException();
		}

		String num = ++tmpMax + "";
		if (num.length() == 1) {
			return pid + "00" + num;
		} else if (num.length() == 2) {
			return pid + "0" + num;
		} else if (num.length() == 3) {
			return num;
		} else {
			return "-1";
		}
	}

}
