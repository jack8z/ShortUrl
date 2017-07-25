package com.lingxin.shorturl.manager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ShortUrlCtrl
 */
public class ShortUrlCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String m = request.getParameter("m");
		m = (null == m ? "" : m.trim());

		if ("createPage".equals(m)) {// 生成短网址页面

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
	}
}
