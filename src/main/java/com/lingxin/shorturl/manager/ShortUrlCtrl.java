package com.lingxin.shorturl.manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lingxin.utils.StaticProps;
import com.lingxin.utils.XDecimalConvertor;

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
			createPage(request, response);
		} else if ("doCreate".equals(m)) {// 生成短网址
			doCreate(request, response);
		} else if ("listPage".equals(m)) {// 短网址列表页面
			listPage(request, response);
		} else {
			response.getWriter().append("Served at: ").append(request.getContextPath()).append("\n\n")
					.append("Method Not Exist!");
		}
	}

	// 生成短网址页面
	public void createPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/shorturl/form/short_url_add.jsp").forward(request, response);
		return;
	}

	// 生成短网址
	public void doCreate(HttpServletRequest request, HttpServletResponse response)
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
			return;
		}

		request.setAttribute("url", url);
		request.setAttribute("title", title);
		request.setAttribute("memo", memo);

		ShortUrlBean shortUrl = new ShortUrlBean();
		shortUrl.setUrl(url);
		shortUrl.setTitle(title);
		shortUrl.setMemo(memo);

		Long shortUrlId = shortUrlModel.insertShortUrl(shortUrl);
		String shortKey = XDecimalConvertor.octal2XDecimal(shortUrlId);// 将ID转为短字符串

		String short_url_domain_prefix = StaticProps.getInstance().getProperty("short_url_domain_prefix");
		String short_url = short_url_domain_prefix + shortKey;
		shortUrl.setShortUrl(short_url);

		boolean ret = shortUrlModel.updateShortUrl(shortUrlId, shortUrl);
		if (!ret) {
			request.setAttribute("error_msg", "生成短网址失败！！");
		}

		request.setAttribute("short_url", short_url);
		request.getRequestDispatcher(page).forward(request, response);
	}

	// 短网址列表页面
	public void listPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page_size = request.getParameter("page_size");
		String curr_page = request.getParameter("curr_page");
		page_size = (null == page_size ? "" : page_size.trim());
		curr_page = (null == curr_page ? "" : curr_page.trim());

		log.debug("curr_page={}, page_size={}", curr_page, page_size);

		int pageSize = 20;
		try {
			pageSize = Integer.parseInt(page_size);
		} catch (NumberFormatException e) {
			pageSize = 20;
		}
		int currPage = 1;
		try {
			currPage = Integer.parseInt(curr_page);
		} catch (NumberFormatException e) {
			currPage = 1;
		}

		Integer totalCount = shortUrlModel.getAllShortUrlCount();
		List<ShortUrlBean> shortUrlList = shortUrlModel.getAllShortUrlList(pageSize, currPage);

		int pageCount = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			pageCount++;
		}

		request.setAttribute("total_count", totalCount);
		request.setAttribute("page_count", pageCount);
		request.setAttribute("page_size", pageSize);
		request.setAttribute("curr_page", currPage);
		request.setAttribute("shortUrlList", shortUrlList);

		request.getRequestDispatcher("/shorturl/form/short_url_list.jsp").forward(request, response);
		return;
	}
}
