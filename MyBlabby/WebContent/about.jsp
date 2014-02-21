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

    <title>myBlabby - About Us</title>

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
            <li class="active"><a href="about.jsp">About</a></li>
            <li><a href="signup">Sign Up</a></li>
            <li><a href="login">Login</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
	  <h1>About Us</h1>
      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus porta condimentum risus a placerat. Sed pulvinar sed nibh vel semper. Integer in lorem tincidunt, molestie ante vitae, tempus diam. Nullam bibendum dignissim volutpat. Etiam rhoncus nunc eu metus molestie, a ultricies lacus vestibulum. Fusce et orci quis magna rhoncus ornare. Duis eu arcu ante. Nunc porta lorem vel vulputate tincidunt. Quisque lacinia tortor non justo ullamcorper, in dictum justo fringilla. Cras volutpat, magna vitae egestas molestie, urna erat convallis arcu, eu imperdiet magna nisl ullamcorper tellus. Mauris suscipit mattis velit eu porta. Ut convallis risus neque, eget placerat ligula fermentum at.</p>
    </div> <!-- /container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
  </body>
</html>