<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>

<html>
<head>
<title>Peđanet Site</title>
<link rel="icon"
	href="http://www.deviantpics.com/images/2016/06/08/icon.png">
</head>
<body>
<body
	bgcolor=${(sessionScope.color == null) ? "WHITE" : sessionScope.color}>
	<h1>Trigonometric table</h1>
	<h2>Table of sine and cosine of values between ${a} and ${b}</h2>
	<h3>
		<a href='http://127.0.0.1:8080/webapp2'>return</a>
	</h3>

	${table}
</body>
</html>