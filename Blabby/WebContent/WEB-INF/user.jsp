<!--  Check if user is logged in, if so - redirect them to their page (main.jsp) -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="io.github.itsjohno.blabby.stores.TweetStore" %>
<%@ page import="io.github.itsjohno.blabby.libraries.Helper" %>

<!DOCTYPE html>
<html lang="en">
  <head>
   <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="myBlabby is a micro-blogging platform">
    <meta name="author" content="Johnathan Law">

    <title>myBlabby - <%= request.getAttribute("user") %></title>

    <!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
	
	<!-- Custom styles for this template -->
	<link href="http://blabby.cloudapp.net:8080/styles/main.css" rel="stylesheet">
	
	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
		<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
		<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
	  </head>
	<body>

	<%
	if (session.getAttribute("user") != null)
	{
	%>
	<!-- Fixed navbar -->
    <div class="navbar navbar-default navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="/page">MyBlabby</a>
        </div>
        <div class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="/page/main">Home</a></li>
            <li><a href="/page/logout">Logout</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>
	<%
	}
	else
	{
	%>
    <!-- Fixed navbar -->
    <div class="navbar navbar-default navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="/page">MyBlabby</a>
        </div>
        <div class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li><a href="/page">Home</a></li>
            <li class="active"><a href="/page/login">Login</a></li>
            <li><a href="/page/signup">Signup</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>
    <%
	}
    %>

    <!-- Begin page content -->
    <div class="container">
	  <div class="page-header">
        <h1><%= request.getAttribute("user") %>'s Page</h1>
      </div>	
		<%
		List<TweetStore> lTweet = (List<TweetStore>)request.getAttribute("tweets");
		if (lTweet==null)
		{
		 %>
			<p>No Tweet found</p>
		<% 
		}
		else
		{
			Iterator<TweetStore> iterator;
	
			iterator = lTweet.iterator();     
			while (iterator.hasNext())
			{
				TweetStore ts = (TweetStore)iterator.next();
			%>
			<p>"<%=ts.getTweet() %>"<br/>
			<span class="text-muted">- <%= Helper.getStringFromUUID(ts.getUUID()) %></span></p>
			<%
			}
		}
		%>
    </div>
    <div id="footer">
      <div class="container">
        <p class="text-muted">MyBlabby</p>
      </div>
    </div>


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
  </body>
</html>