package site.zhouinfo.mysql.dao;

import org.slf4j.LoggerFactory;
import site.zhouinfo.mysql.bean.Area;

import java.sql.*;
import java.util.List;

/**
 * 执行多线程插入
 * Author:      zhou
 * Create Date：2016-02-08 18:07
 */
public class ThreadWrite extends Thread {

	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(ThreadWrite.class);

	private List<Area> areaList;

	public List<Area> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<Area> areaList) {
		this.areaList = areaList;
	}

	public void run() {
		long startTime = System.currentTimeMillis();
		Connection conn = Dao.getConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO sys_area (id,parent_id,parent_ids,name,sort,code,type,create_by,create_date,update_by,update_date,remarks) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
		String useSQL = "USE zhouinfo";
		Statement stmt = null;
		int i = 0;
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			stmt.executeUpdate(useSQL);
			logger.debug("有" + String.valueOf(areaList.size()) + "条数据需要插入数据库");
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for (Area area : areaList) {
				i++;
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
				pstmt.addBatch();
				//一次提交数量不能太多数据，多线程io速度有限
				if (i % 1000 == 0) {
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
		long spendTime = System.currentTimeMillis() - startTime;
		logger.debug("耗时：" + spendTime + " ms");
	}
}
