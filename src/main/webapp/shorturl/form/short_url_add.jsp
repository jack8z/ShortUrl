<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="pub/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="short_url.css" />
<title>生成短网址</title>
</head>
<body>
	<div>
		<form id="form1" method="post">
			<div>
				<label for="url">长网址：</label> <input id="url" type="text"
					class="input-text" />
			</div>
			<div>
				<label for="title">网址标题：</label> <input id="title" type="text"
					class="input-text" />
			</div>
			<div>
				<label for="memo">网址标题：</label> <input id="memo" type="text"
					class="input-text" />
			</div>
			<div>
				<button id="btn_create">生成短网址</button>
			</div>
		</form>
	</div>

	<script type="text/javascript" src="pub/js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="short_url_add.js"></script>
</body>
</html>