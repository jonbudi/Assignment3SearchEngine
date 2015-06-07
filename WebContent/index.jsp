<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>CS 121 Search Engine</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!-- bootstrap import -->
<link type="text/css" rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css" />
<script type="text/javascript" src="JS/bootstrap.min.js"></script>
<link type="text/css" rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" />

<link type="text/css" rel="stylesheet" href="CSS/jquery-ui.css" />
<script type="text/javascript" src="JS/jquery.min.js"></script>
<script type="text/javascript" src="JS/jquery-ui.min.js"></script>
<script type="text/javascript" src="JS/autocomplete.js"></script>

<link type="text/css" rel="stylesheet" href="CSS/style.css" />

</head>

<style>
/* Using Bootstrap 3.1.1 (just for the grid) //maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css */
/* and Fontawesome 4.3.0 (for the icon ) //maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css */

body{

  background: url('http://www.jiki.net/web/images/company/background.jpg') fixed center center no-repeat;
  background-size: cover;
  
}

a:link {
    color: #FF0000;
}

/* visited link */
a:visited {
    color: #00FF00;
}

/* mouse over link */
a:hover {
    color: #FF00FF;
}

/* selected link */
a:active {
    color: #0000FF;
}

.container {
  max-width: 750px;
}

.background {
  padding-top: 30px;
  padding-bottom: 30px;
  background: transparent;
}



/* Search Box */

.search-box {
  position: relative;
  height: 40px; /* Define the search box height and width here */
  width: 100%;
}

.search-box .search-input {
  box-sizing: border-box;
  width: 100%;
  height: 100%;
  padding: 0 50px 0 10px; /* padding-right = button width */
  border: solid 1px #808080;
  border-radius: 3px;
  color: #4b0082;
  outline: none;
  box-shadow: 0px 0px 3px 2px rgba(75,0,130,0);
  transition: box-shadow 0.3s ease, border-color 0.3s ease;
}

.search-box .search-input:focus {
  border-color: #4b0082;
  box-shadow: 0px 0px 4px 2px rgba(75,0,130,0.15);
}

/* Placeholder */
.search-box .search-input::-webkit-input-placeholder { color: #bfbfbf; }
.search-box .search-input::-moz-placeholder          { color: #bfbfbf; opacity: 1; }
.search-box .search-input:-ms-input-placeholder      { color: #bfbfbf; }

/* Icon */
.search-box .search-icon {
  content: '';
  position: absolute;
  z-index: 0;
  top: 1px; /* Keep away */
  right: 1px; /* from */
  bottom: 1px; /* border */
  width: 50px;
  line-height: 38px; /* 40 - 1 - 1 */
  font-size: 20px;
  text-align: center;
  color: #4b0082;
  transition: transform 0.2s ease;
}

/* Button */
.search-box .search-button {
  position: absolute;
  z-index: 1;
  top: -5px; /* using negative values, so that on touch */
  right: 1px; 
  bottom: -5px; /* devices it would be easier to hit */
  width: 50px;
  border: none;
  overflow: hidden; /* Just to be sure */
  opacity: 0; /* hidden */
  -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)"; /* IE8 */
}

</style>

<body>
<div class="container">
  <center><img src="logo.png" ></center>
  <hr>
  <div class="background">
    <div class="row">
      <div class="col-xs-10 col-xs-offset-1">
        
        <form action="DisplayPage.cd">
          <div class="search-box">
            <input class="search-input" id="searchBar" type="text" name="query" placeholder="Search for something..." autofocus required>
            <input class="search-button" type="submit" value="Search">
            <i class="search-icon fa fa-search"></i>
          </div>
        </form>
        
      </div>
    </div>
  </div>
</div>


</body>
</html>