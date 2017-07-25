package com.lingxin.shorturl.manager;

import java.util.Date;

public class ShortUrlBean implements java.io.Serializable {
	private static final long serialVersionUID = 5170127621322435667L;

	private Integer id;// ID
	private String url;// 长网址
	private String title;// 网址标题
	private String memo;// 备注
	private Integer flag = 0;// 网址分类
	private String shortUrl;// 短网址
	private Long accessTimes = 0L;// 短网址的访问次数
	private Date created;// 短网址的生成时间
	private Date modified;// 短网址的修改时间，当access_times加1时，不修改此时间
	private Date accessed;// 短网址的最后访问时间，当access_times加1时，修改此时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public Long getAccessTimes() {
		return accessTimes;
	}

	public void setAccessTimes(Long accessTimes) {
		this.accessTimes = accessTimes;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Date getAccessed() {
		return accessed;
	}

	public void setAccessed(Date accessed) {
		this.accessed = accessed;
	}
}
