package com.lingxin.shorturl.manager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class ShortUrlCtrl
 */
public class ShortUrlCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(ShortUrlCtrl.class);

	private ShortUrlModel shortUrlModel = new ShortUrlModel();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String m = request.getParameter("m");
		m = (null == m ? "" : m.trim());

		if ("createPage".equals(m)) {// 生成短网址页面
			request.getRequestDispatcher("/shorturl/form/short_url_add.jsp").forward(request, response);
		} else if ("doCreate".equals(m)) {// 生成短网址
			createShortUrl(request, response);
		} else if ("listPage".equals(m)) {// 短网址列表页面

		} else {
			response.getWriter().append("Served at: ").append(request.getContextPath()).append("\n\n")
					.append("Method Not Exist!");
		}
	}

	// 生成短网址
	public void createShortUrl(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = "/shorturl/form/short_url_add.jsp";

		String url = request.getParameter("url");
		String title = request.getParameter("title");
		String memo = request.getParameter("memo");
		url = (null == url ? "" : url.trim());
		title = (null == title ? "" : title.trim());
		memo = (null == memo ? "" : memo.trim());

		log.debug("url={}, title={}, memo={}", url, title, memo);

		if ("".equals(url)) {
			request.setAttribute("error_msg", "长网址不能为空");
			request.getRequestDispatcher(page).forward(request, response);
		}

		ShortUrlBean shortUrl = new ShortUrlBean();
		shortUrl.setUrl(url);
		shortUrl.setTitle(title);
		shortUrl.setMemo(memo);

		Integer shortUrlId = shortUrlModel.insertShortUrl(shortUrl);
	}
}
