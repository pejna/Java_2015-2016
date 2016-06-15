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
	<h1>Peđanet Site</h1>

	<h2>
		<a href="setcolor">Background color chooser</a>
	</h2>
	<h2>
		<a href="trigonometric?a=0&b=90">Trigonometric</a>
	</h2>

	<h2>
		<a href="funny">Funny joke he he</a>
	</h2>

	<h2>
		<a href="report">OS usage report</a>
	</h2>

	<h2>
		<a href="power?a=1&b=100&n=3">Power of a number</a>
	</h2>

	<h2>
		<a href="appinfo">App info</a>
	</h2>

	<h2>
		<a href="glasanje">Music band voting contest</a>
	</h2>

	<h2>
		<a href="glasanje-rezultati">Music band voting contest results</a>
	</h2>
</body>
</html>
