package com.lingxin.shorturl.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lingxin.dbtools.DbTool;

public class ShortUrlModel {
	private static final Logger log = LoggerFactory.getLogger(ShortUrlModel.class);

	/**
	 * 获取短网址的总数量
	 * 
	 * @return
	 */
	public Integer getAllShortUrlCount() {
		Integer totalCount = -1;

		String sql = "select count(1) from short_urls";

		// 获取数据库连接
		Connection conn = DbTool.getConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			int n = 1;
			rs = ps.executeQuery();
			while (rs.next()) {
				totalCount = rs.getInt(1);
			}
		} catch (Exception e) {
			log.error("获取所有的短网址列表时出错啦", e);
		} finally {
			DbUtils.closeQuietly(conn, ps, rs);
		}

		return totalCount;
	}

	/**
	 * 获取所有的短网址列表
	 * 
	 * @param pageSize-每页大小
	 * @param currPage-当前页
	 * @return
	 */
	public List<ShortUrlBean> getAllShortUrlList(int pageSize, int currPage) {
		List<ShortUrlBean> list = new ArrayList<ShortUrlBean>();

		String sql = "select * from short_urls order by id LIMIT ? OFFSET ?";

		// 获取数据库连接
		Connection conn = DbTool.getConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			int n = 1;
			ps.setInt(n++, pageSize);
			ps.setInt(n++, (currPage - 1) * pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				long id = rs.getLong("id");
				String url = rs.getString("url");
				String title = rs.getString("title");
				String memo = rs.getString("memo");
				int flag = rs.getInt("flag");
				String short_url = rs.getString("short_url");
				long access_times = rs.getLong("access_times");
				Date created = rs.getTimestamp("created");
				Date modified = rs.getTimestamp("modified");
				Date accessed = rs.getTimestamp("accessed");

				ShortUrlBean shortUrl = new ShortUrlBean();
				shortUrl.setId(id);
				shortUrl.setUrl(url);
				shortUrl.setTitle(title);
				shortUrl.setMemo(memo);
				shortUrl.setFlag(flag);
				shortUrl.setShortUrl(short_url);
				shortUrl.setAccessTimes(access_times);
				shortUrl.setCreated(created);
				shortUrl.setModified(modified);
				shortUrl.setAccessed(accessed);

				list.add(shortUrl);
			}
		} catch (Exception e) {
			log.error("获取所有的短网址列表时出错啦", e);
		} finally {
			DbUtils.closeQuietly(conn, ps, rs);
		}

		return list;
	}

	/**
	 * 获取所有的短网址列表
	 * 
	 * @param pageSize-每页大小
	 * @param currPage-当前页
	 * @return
	 */
	public ShortUrlBean getShortUrl(String key) {
		ShortUrlBean shortUrl = null;

		String sql = "select * from short_urls where short_url=? order by id LIMIT 1";

		// 获取数据库连接
		Connection conn = DbTool.getConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			int n = 1;
			ps.setString(n++, key);
			rs = ps.executeQuery();
			while (rs.next()) {
				long id = rs.getLong("id");
				String url = rs.getString("url");
				String title = rs.getString("title");
				String memo = rs.getString("memo");
				int flag = rs.getInt("flag");
				String short_url = rs.getString("short_url");
				long access_times = rs.getLong("access_times");
				Date created = rs.getTimestamp("created");
				Date modified = rs.getTimestamp("modified");
				Date accessed = rs.getTimestamp("accessed");

				shortUrl = new ShortUrlBean();
				shortUrl.setId(id);
				shortUrl.setUrl(url);
				shortUrl.setTitle(title);
				shortUrl.setMemo(memo);
				shortUrl.setFlag(flag);
				shortUrl.setShortUrl(short_url);
				shortUrl.setAccessTimes(access_times);
				shortUrl.setCreated(created);
				shortUrl.setModified(modified);
				shortUrl.setAccessed(accessed);
			}
		} catch (Exception e) {
			log.error("获取所有的短网址列表时出错啦", e);
		} finally {
			DbUtils.closeQuietly(conn, ps, rs);
		}

		return shortUrl;
	}

	/**
	 * 插入一条短网址，返回此短网址的ID
	 * 
	 * @param shortUrl
	 * @return
	 */
	public Long insertShortUrl(ShortUrlBean shortUrl) {
		StringBuffer insertSql = new StringBuffer();
		insertSql.append("insert into ");
		insertSql.append("short_urls");
		insertSql.append("(");
		insertSql.append("url,");
		insertSql.append("title,");
		insertSql.append("memo,");
		insertSql.append("flag,");
		insertSql.append("short_url,");
		insertSql.append("access_times,");
		insertSql.append("created,");
		insertSql.append("modified,");
		insertSql.append("accessed");
		insertSql.append(") values(");
		insertSql.append("?,?,?,?,?,");
		insertSql.append("?,?,?,?");
		insertSql.append(") ");

		long shortUrlId = -1;

		// 获取数据库连接
		Connection conn = DbTool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(insertSql.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			int n = 1;
			String url = shortUrl.getUrl();
			url = (null == url ? "" : url.trim());
			ps.setString(n++, url);

			String title = shortUrl.getTitle();
			title = (null == title ? "" : title.trim());
			ps.setString(n++, title);

			String memo = shortUrl.getMemo();
			memo = (null == memo ? "" : memo.trim());
			ps.setString(n++, memo);

			ps.setInt(n++, shortUrl.getFlag());

			String short_url = shortUrl.getShortUrl();
			short_url = (null == short_url ? "" : short_url.trim());
			ps.setString(n++, short_url);

			ps.setInt(n++, 0);// access_times

			Date now = new Date();
			ps.setTimestamp(n++, new Timestamp(now.getTime()));// created
			ps.setTimestamp(n++, new Timestamp(now.getTime()));// modified
			ps.setTimestamp(n++, new Timestamp(now.getTime()));// accessed

			if (log.isDebugEnabled()) {
				log.debug("[SQL] " + ps);
			}

			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			if (null != rs && rs.next()) {
				shortUrlId = rs.getLong(1);
			}
		} catch (SQLException e) {
			log.error("往数据库中插入一条短网址时出错啦", e);
		} catch (RuntimeException e) {
			log.error("往数据库中插入一条短网址时出错啦", e);
		} catch (Exception e) {
			log.error("往数据库中插入一条短网址时出错啦", e);
		} finally {
			DbUtils.closeQuietly(conn, ps, rs);
		}

		return shortUrlId;
	}

	/**
	 * 更新指定的短网址
	 * 
	 * @param id
	 * @param shortUrl
	 */
	public boolean updateShortUrl(Long id, ShortUrlBean shortUrl) {
		StringBuffer updateSql = new StringBuffer();
		updateSql.append("update ").append("short_urls").append(" set ");
		updateSql.append("url=?,");
		updateSql.append("title=?,");
		updateSql.append("memo=?,");
		updateSql.append("flag=?,");
		updateSql.append("short_url=?,");
		updateSql.append("modified=? ");
		updateSql.append("where id=? ");

		boolean result = true;

		// 获取数据库连接
		Connection conn = DbTool.getConnection();
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(updateSql.toString());

			int n = 1;
			String url = shortUrl.getUrl();
			url = (null == url ? "" : url.trim());
			ps.setString(n++, url);

			String title = shortUrl.getTitle();
			title = (null == title ? "" : title.trim());
			ps.setString(n++, title);

			String memo = shortUrl.getMemo();
			memo = (null == memo ? "" : memo.trim());
			ps.setString(n++, memo);

			ps.setInt(n++, shortUrl.getFlag());

			String short_url = shortUrl.getShortUrl();
			short_url = (null == short_url ? "" : short_url.trim());
			ps.setString(n++, short_url);

			Date now = new Date();
			ps.setTimestamp(n++, new Timestamp(now.getTime()));// modified

			ps.setLong(n++, id);

			if (log.isDebugEnabled()) {
				log.debug("[SQL] " + ps);
			}

			int count = ps.executeUpdate();
			if (count > 0) {
				result = true;
			} else {
				result = false;
			}
		} catch (SQLException e) {
			log.error("修改数据库中的短网址信息时出错啦", e);
			result = false;
		} catch (RuntimeException e) {
			log.error("修改数据库中的短网址信息时出错啦", e);
			result = false;
		} catch (Exception e) {
			log.error("修改数据库中的短网址信息时出错啦", e);
			result = false;
		} finally {
			DbUtils.closeQuietly(ps);
			DbUtils.closeQuietly(conn);
		}
		return result;
	}

	/**
	 * 将短网址的访问次数加一
	 * 
	 * @param id
	 * @param shortUrl
	 */
	public boolean addAccessTimes(Long id) {
		StringBuffer updateSql = new StringBuffer();
		updateSql.append("update ").append("short_urls").append(" set ");
		updateSql.append("access_times=access_times+1,");
		updateSql.append("accessed=? ");
		updateSql.append("where id=? ");

		boolean result = true;

		// 获取数据库连接
		Connection conn = DbTool.getConnection();
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(updateSql.toString());

			int n = 1;

			Date now = new Date();
			ps.setTimestamp(n++, new Timestamp(now.getTime()));// accessed

			ps.setLong(n++, id);

			if (log.isDebugEnabled()) {
				log.debug("[SQL] " + ps);
			}

			int count = ps.executeUpdate();
			if (count > 0) {
				result = true;
			} else {
				result = false;
			}
		} catch (SQLException e) {
			log.error("将短网址的访问次数加一时出错啦", e);
			result = false;
		} catch (RuntimeException e) {
			log.error("将短网址的访问次数加一时出错啦", e);
			result = false;
		} catch (Exception e) {
			log.error("将短网址的访问次数加一时出错啦", e);
			result = false;
		} finally {
			DbUtils.closeQuietly(ps);
			DbUtils.closeQuietly(conn);
		}
		return result;
	}
}
