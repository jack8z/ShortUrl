package com.lingxin.dbtools;

import java.sql.Connection;
import java.sql.DriverManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbTool {
	private static final Logger log = LoggerFactory.getLogger(DbTool.class);

	public static Connection getConnection() {
		return DbTool.getConnectionByDriverManager();
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 */
	private static Connection getConnectionByDriverManager() {
		Connection conn = null;
		try {
			DbConfig dbConfig = DbConfig.getInstance();
			String driver = dbConfig.getDriverClassName();
			String url = dbConfig.getUrl();
			String userName = dbConfig.getUsername();
			String password = dbConfig.getPassword();

			Class.forName(driver);
			conn = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			log.error("获取数据库连接时出错啦", e);
		}
		return conn;
	}
}
