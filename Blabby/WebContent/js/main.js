/**
 * http://stackoverflow.com/questions/5533053/textarea-character-limit
 */
function maxLength(el) {    
    if (!('maxLength' in el)) {
        var max = el.attributes.maxLength.value;
        el.onkeypress = function () {
            if (this.value.length >= max) return false;
        };
    }
}

maxLength(document.getElementById("tweetBox"));

$( "#tweetForm" ).submit(function(event) 
{
	event.preventDefault();
	
	var url = "http://blabby.cloudapp.net:8080/tweet";
	
	if ($( "#tweetBox" ).val() != "" || $( "#tweetBox" ).val() != null || $( "#tweetBox" ).val() != "What's on your mind?")
	{
		var params = "message="+$( "#tweetBox" ).val();
		
		var http = new XMLHttpRequest();
		
		http.open("POST", url, true);
	
		//Send the proper header information along with the request
		http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
		http.onreadystatechange = function() {//Call a function when the state changes.
		    if (http.readyState == 4 && http.status == 202)
		    {
		    	$( "#alert" ).remove();
	    		$( "#tweetForm" ).prepend( '<div id="alert" class="alert alert-success">Tweet posted!</div>' );
		    	
		    	window.location.replace("/page/main");
		    }
		    else if (http.readyState == 4 && http.status == 400)
		    {
	    		$( "#alert" ).remove();
	    		$( "#signUpForm" ).prepend( '<div id="alert" class="alert alert-warning">Something has gone wrong, please try again</div>' );
		    }
		};
		
		http.send(params);
	}
});