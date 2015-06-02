<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Not Google</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!-- bootstrap import -->
<link type="text/css" rel="stylesheet" href="CSS/bootstrap.min.css" />
<script type="text/javascript" src="JS/bootstrap.min.js"></script>

<link type="text/css" rel="stylesheet" href="CSS/jquery-ui.css" />
<script type="text/javascript" src="JS/jquery.min.js"></script>
<script type="text/javascript" src="JS/jquery-ui.min.js"></script>
<script type="text/javascript" src="JS/autocomplete.js"></script>

<link type="text/css" rel="stylesheet" href="CSS/style.css" />

</head>
<body>
	<div class="container">
		<div class="vertical-center-row" id="divControl">
			<div align="center">
				<h1 id="header">Not Google</h1>
				<form name="searchForm" method="post" action="DisplayPage.cd">
					<div class="form-group">
						<input type="text" class="form-control" id="searchBar"
							name="query" placeholder="Search" autofocus required>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>