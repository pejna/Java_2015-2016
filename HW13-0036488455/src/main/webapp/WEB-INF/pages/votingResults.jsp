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
	<h1>Voting results</h1>
	<p>These are the voting results:</p>
	${table}
	<h2>Result chart</h2>
	<img alt="Pie chart" src="glasanje-diagram">
	<h2>Results in XLS format</h2>
	<p>
		XLS formated results are available <a href="results.xls">here</a>
	</p>
	<h2>Miscleanous</h2>
	<p>Song examples of the winning bands:</p>
	<ul>
		<li><a href='${firstLink}'>${firstName}</a></li>
		<li><a href='${secondLink}'>${secondName}</a></li>
	</ul>

	<h3>
		<a href='http://127.0.0.1:8080/webapp2'>return to index</a>
	</h3>