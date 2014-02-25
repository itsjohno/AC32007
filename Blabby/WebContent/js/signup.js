/**
 * 
 */
$( "#signUpForm" ).submit(function(event) 
{
	event.preventDefault();
	
	var url = "http://blabby.cloudapp.net:8080/user/register";
	var params = "username="+$( "input[name='username']" ).val()+"&password="+$( "input[name='password']" ).val()+"&password-confirm="+$( "input[name='password-confirm']" ).val()+"&email="+$( "input[name='email']" ).val();
	
	var http = new XMLHttpRequest();
	
	http.open("POST", url, true);

	//Send the proper header information along with the request
	http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

	http.onreadystatechange = function() {//Call a function when the state changes.
	    if (http.readyState == 4 && http.status == 201)
	    {
	    	$( "#alert" ).remove();
    		$( "#signUpForm" ).prepend( '<div id="alert" class="alert alert-success">Thanks for signing up! Logging you in...</div>' );
	    	
	    	window.location.replace("/page/main");
	    }
	    else if (http.readyState == 4 && http.status == 409)
	    {
    		$( "#alert" ).remove();
    		$( "#signUpForm" ).prepend( '<div id="alert" class="alert alert-danger">That username is already taken, please choose another</div>' );
	    }
	    else if (http.readyState == 4 && http.status == 400)
	    {
    		$( "#alert" ).remove();
    		$( "#signUpForm" ).prepend( '<div id="alert" class="alert alert-warning">The passwords you entered do not match, please try again</div>' );
	    }
	};
	
	http.send(params);
});