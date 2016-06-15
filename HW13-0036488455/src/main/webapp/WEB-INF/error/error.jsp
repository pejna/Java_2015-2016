<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>

<html>
<head>
<title>Peđanet Error - From you to Peđa</title>
<link rel="icon" href="http://www.deviantpics.com/images/2016/06/08/icon.png">
</head>
<body>
<body
	bgcolor=${(sessionScope.color == null) ? "WHITE" : sessionScope.color}>
	<h1>${ error }</h1>
</body>
</html>