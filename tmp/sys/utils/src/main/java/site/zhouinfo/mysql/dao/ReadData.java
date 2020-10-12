package site.zhouinfo.mysql.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.zhouinfo.mysql.bean.Area;
import site.zhouinfo.mysql.bean.City;
import site.zhouinfo.stringutils.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 预处理 多线程数据
 * Author:      zhou
 * Create Date：2016-02-06 0:56
 */
public class ReadData {

	private final static Logger logger = LoggerFactory.getLogger(ReadData.class);

	private List<City> cityList = new ArrayList<City>(2048);
	private List<Area> areaList = new ArrayList<Area>(2048);

	public List<City> getCityList() {
		return cityList;
	}

	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}

	public List<Area> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<Area> areaList) {
		this.areaList = areaList;
	}

	/*
	* 获取原始 城市信息
	* id :  主键
	* area: 地区名称
	* parent_code: 父地区id
	* city_code: 城市编号   *不知道它表达的内容*
	* */
	public void readCityByMysql(String like) {
		Connection conn = Dao.getConnection();
		String sql=null;
		if (!StringUtils.isEmpty(like)) {
			sql = "select * from city where id LIKE '" + like + "'";
		}else{
			sql = "select * from city";
		}
		String useSQL = "USE zhouinfo";
		Statement stmt;
		ResultSet rs;
		if (conn != null) {
			//create database and tables that will be needed
			try {

				stmt = conn.createStatement();
				stmt.executeUpdate(useSQL);
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					City city = new City();
					city.setId(rs.getString(1));
					city.setArea(rs.getString(2));
					city.setParentCode(rs.getString(3));
					city.setCityCode(rs.getString(4));
					cityList.add(city);
				}
				//logger.debug("总共" + String.valueOf(cityList.size()) + "条数据");
			} catch (Exception e) {
				logger.error(String.valueOf(e));
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	* 转换成需要的对象
	* Area
	* */
	public void cityToArea() {
		int i = 0;
		for (City city : cityList) {
			//logger.debug(city.getArea());
			/*if (i % 100 == 0) {
				logger.debug(String.valueOf(i));
			}*/
			i++;
			Area area = new Area();
			area.setId(city.getId());
			area.setName(city.getArea());
			area.setParent(city.getParentCode());
			area.setSort(i * 10);
			if (city.getId().length() == 2) {
				area.setType(2);
				area.setParent("1");
				area.setParentIds("0,1,");
			}
			if (city.getId().length() == 4) {
				area.setType(3);
				area.setParentIds("0,1," + city.getId().substring(0, 2) + ",");
			}
			if (city.getId().length() == 6) {
				area.setType(4);
				area.setParentIds("0,1," + city.getId().substring(0, 2) + "," + city.getId().substring(0, 4) + ",");
			}
			if (city.getId().length() == 9) {
				area.setType(5);
				area.setParentIds("0,1," + city.getId().substring(0, 2) + "," + city.getId().substring(0, 4) + "," + city.getId().substring(0, 6) + ",");
			}
			if (city.getId().length() == 12) {
				area.setType(6);
				area.setParentIds("0,1," + city.getId().substring(0, 2) + "," + city.getId().substring(0, 4) + "," + city.getId().substring(0, 6) + "," + city.getId().substring(0, 9) + ",");
			}
			areaList.add(area);
		}
	}

	/*
	* 获取某段list Area数据
	* */
	public List<Area> getAreaList(int start, int end) {
		return areaList.subList(start, end);
	}

	public int getAreaListSize() {
		return areaList.size();
	}
}
