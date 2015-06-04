<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="domain.SearchResult,java.util.*"%>

<% 
 	List<SearchResult> results = (List<SearchResult>)request.getAttribute("results");
 	if (results == null) {
 		results = new ArrayList<SearchResult>();
 	}
 	//int found = results.size();
 
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>CS 121 Search Engine</title>
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
		<br></br>
		<form name="searchForm" method="post" action="DisplayPage.cd">
			<div class="form-group">
				<input type="text" class="form-control" id="searchBar" name="query"
					placeholder="Search" autofocus required>
			</div>
		</form>
		<h1 id="header">Search Results</h1>
		<%	
			for (int i = 0; i < results.size(); ++i) {
				SearchResult sr = results.get(i);
		%>
			<p><a href="<%= sr.getUrl() %>"><%= sr.getTitle() %></a></p>
			<p><%= sr.getUrl() %></p>
			<br></br>
		<% } %>
		<a href="">Next Page</a>
	</div>
</body>
</html>