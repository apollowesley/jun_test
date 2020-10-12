package site.zhouinfo.mysql.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.zhouinfo.mysql.bean.Area;

import java.sql.*;
import java.util.List;

/**
 *
 * 单线程 进行插入数据库 速度太慢了，2s才插入一百条数据
 * Author:      zhou
 * Create Date：2016-02-08 18:12
 */
public class OrdinaryWrite {

	private final static Logger logger = LoggerFactory.getLogger(OrdinaryWrite.class);

	public void cityWriteToArea(String like) {
		Connection conn = Dao.getConnection();
		ReadData readData = new ReadData();
		String sql = "INSERT INTO sys_area (id,parent_id,parent_ids,name,sort,code,type,create_by,create_date,update_by,update_date,remarks) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
		Statement stmt;
		PreparedStatement pstmt;
		String useSQL = "USE zhouinfo";

		if (conn != null) {
			try {
				conn.setAutoCommit(false);
				stmt = conn.createStatement();
				stmt.executeUpdate(useSQL);
				int i = 0;
				readData.readCityByMysql(like);
				readData.cityToArea();
				List<Area> areaList = readData.getAreaList(0, readData.getAreaListSize());
				logger.debug("有" + String.valueOf(areaList.size()) + "条数据需要插入数据库");
				pstmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				for (Area area : areaList) {
					//logger.debug(city.getArea());
					pstmt.setString(1, area.getId());
					pstmt.setString(2, area.getParent());
					pstmt.setString(3, area.getParentIds());
					pstmt.setString(4, area.getName());
					pstmt.setInt(5, area.getSort());
					pstmt.setString(6, area.getCode());
					pstmt.setInt(7, area.getType());
					pstmt.setString(8, area.getCreateBy());
					pstmt.setTimestamp(9, new Timestamp(area.getCreateDate().getTime()));
					pstmt.setString(10, area.getUpdateBy());
					pstmt.setTimestamp(11, new Timestamp(area.getUpdateDate().getTime()));
					pstmt.setString(12, area.getRemarks());
					//pstmt.execute();
					pstmt.addBatch();
					i++;
					if (i % 10000 == 0) {
						pstmt.executeBatch();
						conn.commit();
						logger.debug(String.valueOf(i));
					}
				}
				pstmt.executeBatch();
				conn.commit();
				logger.debug("插入了" + String.valueOf(i) + "条数据");
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
}
