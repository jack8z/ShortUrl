package com.lingxin.shorturl.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lingxin.shorturl.manager.ShortUrlBean;
import com.lingxin.shorturl.manager.ShortUrlModel;

/**
 * 短网址Servlet，用于将短网址重定向到长网址
 */
public class ShortUrlServlet extends HttpServlet {
	private static final long serialVersionUID = 2199937547265657034L;

	private static final Logger log = LoggerFactory.getLogger(ShortUrlServlet.class);

	private ShortUrlModel shortUrlModel = new ShortUrlModel();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());

		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String k = request.getParameter("k");
		k = (null == k ? "" : k.trim());

		String the_url = request.getRequestURL().toString();

		log.debug("the_url={}, k={}", the_url, k);

		if ("".equals(k)) {
			response.getWriter().append("Can not found the url:").append(request.getRequestURL()).append("\n\n");
			return;
		}

		String the_short_url = the_url + "?k=" + k;

		log.debug("the_short_url={}", the_short_url);

		// 获取短网址记录
		ShortUrlBean shortUrl = shortUrlModel.getShortUrl(the_short_url);
		if (null == shortUrl) {
			response.getWriter().append("Can not found the url:").append(request.getRequestURL()).append("\n\n");
			return;
		}

		// 更新短网址的访问次数
		shortUrlModel.addAccessTimes(shortUrl.getId());

		// 将请求转发到长网址
		response.sendRedirect(shortUrl.getUrl());
	}
}
