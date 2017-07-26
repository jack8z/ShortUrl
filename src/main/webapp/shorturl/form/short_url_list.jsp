<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="pub/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="shorturl/form/short_url.css" />
<title>短网址列表</title>
</head>
<body>
<div class="header"></div>

<div class="content">
	<div class="box">
		<form id="form1" method="post" action="shorturl.do?m=listPage">
			<input type="hidden" id="total_count" name="total_count" value="${total_count}">
			<input type="hidden" id="page_count" name="page_count" value="${page_count}">
			<input type="hidden" id="page_size" name="page_size" value="${page_size}">
			<input type="hidden" id="curr_page" name="curr_page" value="${curr_page}">
			
			<label for="short_url">短网址：</label>
			<input type="text" id="short_url" name="short_url">
			<button id="btn_query">查询</button>
		</form>
	</div>
	
	<div class="box">
		<table>
			<thead>
				<tr>
					<td style="width:30px;"></td>
					<td style="width:200px;">短网址</td>
					<td style="width:400px;">长网址</td>
					<td style="width:150px;">标题</td>
					<td style="width:150px;">备注</td>
					<td style="width:80px;">转发次数</td>
					<td style="width:150px;">最后一次转发时间</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="short" items="${shortUrlList}" varStatus="row">
				<tr>
					<td>${row.count}</td>
					<td>${short.shortUrl}</td>
					<td>${short.url}</td>
					<td>${short.title}</td>
					<td>${short.memo}</td>
					<td>${short.accessTimes}</td>
					<td>${short.accessedStr}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<div>
			<c:if test="${page_count>1}">
				<c:if test="${curr_page>1}">
					<a id="btn_go_previous" href="javascript:void(0);">上一页</a>
				</c:if>
				<c:if test="${curr_page<page_count}">
					<a id="btn_go_next" href="javascript:void(0);">下一页</a>
				</c:if>
			</c:if>
		</div>
	</div>
</div>

<div class="footer"></div>
<script type="text/javascript" src="pub/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="shorturl/form/short_url_list.js"></script>
</body>
</html>