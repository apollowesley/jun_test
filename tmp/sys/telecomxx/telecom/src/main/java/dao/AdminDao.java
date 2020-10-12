package dao;

import entity.Admin;

public interface AdminDao {
	Admin findByAdminCode(String adminCode);
}
