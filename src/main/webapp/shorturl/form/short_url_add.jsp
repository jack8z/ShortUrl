<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="pub/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="shorturl/form/short_url.css" />
<title>生成短网址</title>
</head>
<body>
<div class="header">
</div>

<div class="content">
	<div class="box">
		<form id="form1" method="post" action="shorturl.do?m=doCreate">
			<c:if test="${not empty error_msg}">
				<div class="error_msg">${error_msg}</div>
			</c:if>
			<div class="row">
				<label for="url" class="label">长网址：</label> <input id="url" name="url" type="text" class="input-text" />
			</div>
			<div class="row">
				<label for="title" class="label">网址标题：</label> <input id="title" name="title" type="text" class="input-text" />
			</div>
			<div class="row">
				<label for="memo" class="label">备注：</label> <input id="memo" name="memo" type="text" class="input-text" />
			</div>
			<div class="toolbar">
				<button id="btn_create">生成短网址</button>
				<button id="btn_reset">重置</button>
			</div>
		</form>
	</div>
	<c:if test="${not empty short_url}">
		<div class="box">
			<div class="row">
				<span class="success">成功生成短网址：${short_url}</span>
			</div>
		</div>
	</c:if>
</div>

<div class="footer">
</div>
<script type="text/javascript" src="pub/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="shorturl/form/short_url_add.js"></script>
</body>
</html>