/**
 * 
 */
$( "#loginForm" ).submit(function(event) 
{
	event.preventDefault();
	
	var url = "http://blabby.cloudapp.net:8080/user/login";
	var params = "username="+$( "input[name='username']" ).val()+"&password="+$( "input[name='password']" ).val();
	
	var http = new XMLHttpRequest();
	
	http.open("POST", url, true);

	//Send the proper header information along with the request
	http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

	http.onreadystatechange = function() {//Call a function when the state changes.
	    if (http.readyState == 4 && http.status == 200)
	    {
	    	$( "#alert" ).remove();
    		$( "#loginForm" ).prepend( '<div id="alert" class="alert alert-success">Logging you in...</div>' );
	    	
	    	window.location.replace("/page/main");
	    }
	    else if (http.readyState == 4 && http.status == 401)
	    {
    		$( "#alert" ).remove();
    		$( "#loginForm" ).prepend( '<div id="alert" class="alert alert-danger">Invalid username/password supplied, please try again.</div>' );
	    }
	    else
	    {

    		$( "#alert" ).remove();
    		$( "#loginForm" ).prepend( '<div id="alert" class="alert alert-warning">Unexpected error, please try again.</div>' );
	    }
	};
	
	http.send(params);
});