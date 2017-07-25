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
	 * 获取所有的短网址列表
	 * 
	 * @param pageSize-每页大小
	 * @param currPage-当前页
	 * @return
	 */
	public List<ShortUrlBean> getAllShortUrlList(int pageSize, int currPage) {
		List<ShortUrlBean> list = new ArrayList<ShortUrlBean>();

		String sql = "select * from test.short_urls order by id LIMIT ? OFFSET ?";

		// 获取数据库连接
		Connection conn = DbTool.getConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
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
	 * 插入一条短网址，返回此短网址的ID
	 * 
	 * @param shortUrl
	 * @return
	 */
	public Integer insertShortUrl(ShortUrlBean shortUrl) {
		StringBuffer insertSql = new StringBuffer();
		insertSql.append("insert into ");
		insertSql.append("test.short_urls");
		insertSql.append("(");
		insertSql.append("url,");
		insertSql.append("title,");
		insertSql.append("memo,");
		insertSql.append("flag,");
		insertSql.append("short_url,");
		insertSql.append("access_times,");
		insertSql.append("created,");
		insertSql.append("modified");
		insertSql.append(") values(");
		insertSql.append("?,?,?,?,?,");
		insertSql.append("?,?,?");
		insertSql.append(") ");

		int shortUrlId = -1;

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

			if (log.isDebugEnabled()) {
				log.debug("[SQL] " + ps);
			}

			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			if (null != rs && rs.next()) {
				shortUrlId = rs.getInt(1);
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
	public boolean updateShortUrl(Integer id, ShortUrlBean shortUrl) {
		StringBuffer updateSql = new StringBuffer();
		updateSql.append("update ").append("test.short_urls").append(" set ");
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

			ps.setInt(n++, id);

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
}
