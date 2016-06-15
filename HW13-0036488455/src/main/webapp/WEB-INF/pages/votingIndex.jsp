<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Peđanet Site</title>
<link rel="icon"
	href="http://www.deviantpics.com/images/2016/06/08/icon.png">
</head>
<body>
<body
	bgcolor=${(sessionScope.color == null) ? "WHITE" : sessionScope.color}>
	<h1>Voting for the favorite music band</h1>
	<p>Which one of the following music bands is your favorite?</p>
	<ol>
		<c:forEach items="${idlist}" var="item">
			<c:set var="current" value="${item.toString()}" />
			<li><a href="glasanje-glasaj?id=${item}">${ requestScope[current] }</a></li>
		</c:forEach>
	</ol>
	<h3>
		<a href='http://127.0.0.1:8080/webapp2'>return</a>
	</h3>

</body>
</html>
