<!--  If user is logged in, this page will re-direct them to their home (main.jsp) -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
  <head>
   <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="myBlabby is a micro-blogging platform">
    <meta name="author" content="Johnathan Law">

    <title>myBlabby - Sign Up</title>

    <!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
	
	<!-- Custom styles for this template -->
	<link href="styles/main.css" rel="stylesheet">
	
	<!-- Latest compiled and minified JavaScript -->
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
	
	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
		<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
		<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
	  </head>
	<body>

    <div class="container">

      <!-- Static navbar -->
      <div class="navbar navbar-default" role="navigation">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="/">myBlabby</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="/">Home</a></li>
            <li><a href="about.jsp">About</a></li>
            <li class="active"><a href="signup">Sign Up</a></li>
            <li><a href="login">Login</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>

      <form action="signup" class="form-signin" method="POST">
        <h2 class="form-signin-heading">Sign up to myBlabby</h2>
        
        <%
	      if (request.getAttribute("error") != null)
	      {
	      %>
	      	<div class="alert alert-danger"><%= request.getAttribute("error") %></div>
	      <%
	      }
	    %>
        
        <input name="user" type="text" class="form-control formTop" placeholder="Username" required autofocus>
        <input name="pass" type="password" class="form-control formMiddle" placeholder="Password" required>
        <input name="pass-confirm" type="password" class="form-control formMiddle" placeholder="Confirm Password" required>
        <input name="email" type="email" class="form-control formBottom" placeholder="E-Mail Address" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign Up</button>
      </form>

    </div> <!-- /container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
  </body>
</html>