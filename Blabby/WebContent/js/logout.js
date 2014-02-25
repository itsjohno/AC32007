/**
 * 
 */
$( document ).ready(function()
{
	var url = "http://blabby.cloudapp.net:8080/user/logout";
	var http = new XMLHttpRequest();
	
	http.open("GET", url, true);

	http.onreadystatechange = function()
	{
	    if (http.status == 204)
	    {
	    	$( "#alert" ).replaceWith( '<div id="alert" class="alert alert-info">You are now logged out</div>' );
	    }
	    else
	    {
	    	$( "#alert" ).replaceWith( '<div id="alert" class="alert alert-warning">Unexpected error, please try again.</div>' );
	    }
	};
	
	http.send();
});