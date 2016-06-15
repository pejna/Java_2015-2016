<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>

<html>
<head>
<title>Peđanet Site - From Peđa to you</title>
<link rel="icon"
	href="http://www.deviantpics.com/images/2016/06/08/icon.png">
</head>
<body>
<body
	bgcolor=${(sessionScope.color == null) ? "WHITE" : sessionScope.color}>
	<h1>Background color chooser</h1>

	<h2>Choose the backgound color</h2>
	<h2>
		<a href='setcolor?color=white'>white</a>
	</h2>
	<h2>
		<a href='setcolor?color=red'>red</a>
	</h2>
	<h2>
		<a href='setcolor?color=green'>green</a>
	</h2>
	<h2>
		<a href='setcolor?color=cyan'>cyan</a>
	</h2>

	<h3>
		<a href='http://127.0.0.1:8080/webapp2'>return</a>
	</h3>
</body>
</html>