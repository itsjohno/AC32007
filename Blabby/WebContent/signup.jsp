<!--  Check if user is logged in, if so - redirect them to their page (main.jsp) -->
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
          <a class="navbar-brand" href="index.jsp">MyBlabby</a>
        </div>
        <div class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li><a href="index.jsp">Home</a></li>
            <li><a href="login.jsp">Login</a></li>
            <li class="active"><a href="signup.jsp">Signup</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>

    <!-- Begin page content -->
    <div class="container">
      <div class="page-header">
        <h1>Sign up to myBlabby</h1>
      </div>
      <form action="/user/register" class="form-signin" method="POST">
        
        <%
	      if (request.getAttribute("error") != null)
	      {
	      %>
	      	<div class="alert alert-danger"><%= request.getAttribute("error") %></div>
	      <%
	      }
	    %>
        
        <input name="username" type="text" class="form-control formTop" placeholder="Username" required autofocus>
        <input name="password" type="password" class="form-control formMiddle" placeholder="Password" required>
        <input name="password-confirm" type="password" class="form-control formMiddle" placeholder="Confirm Password" required>
        <input name="email" type="email" class="form-control formBottom" placeholder="E-Mail Address" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign Up</button>
      </form>
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