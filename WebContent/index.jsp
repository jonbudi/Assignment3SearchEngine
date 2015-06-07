<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>CS 121 Search Engine</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!-- bootstrap import -->
<link type="text/css" rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css" />
<script type="text/javascript" src="JS/bootstrap.min.js"></script>
<link type="text/css" rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" />

<link type="text/css" rel="stylesheet" href="CSS/jquery-ui.css" />
<script type="text/javascript" src="JS/jquery.min.js"></script>
<script type="text/javascript" src="JS/jquery-ui.min.js"></script>
<script type="text/javascript" src="JS/autocomplete.js"></script>

<link type="text/css" rel="stylesheet" href="CSS/style.css" />

</head>

<body>
	<div class="container">
		<br>
		<center>
			<img src="logo.png">
		</center>
		<hr>
		<div class="background">
			<div class="row">
				<div class="col-xs-10 col-xs-offset-1">

					<form action="DisplayPage.cd">
						<div class="search-box">
							<input class="search-input" id="searchBar" type="text"
								name="query" placeholder="Search for something..." autofocus
								required> <input class="search-button" type="submit"
								value="Search"> <i class="search-icon fa fa-search"></i>
						</div>
					</form>

				</div>
			</div>
		</div>
	</div>


</body>
</html>