

<script>
function func(str)
{
    //Creating a new XMLHttpRequest object
    var xmlhttp;
    if (window.XMLHttpRequest){
        xmlhttp = new XMLHttpRequest(); 
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); 
    }
	reqURL="ajaxAction.do?operation=checkusername&username="+str;
    //Create a asynchronous GET request
    xmlhttp.open("GET", reqURL, true);
	    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200 ) {
                document.getElementById("message").innerHTML = xmlhttp.responseText;
				if(xmlhttp.responseText=="invalid user"){
					document.getElementById("submit").disabled=true;
				}
				else{
					document.getElementById("submit").disabled=false;
				}
        }
    };
	xmlhttp.send();
	
}

function checkpassword(str)
{
	if (window.XMLHttpRequest){
        xmlhttp = new XMLHttpRequest(); 
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); 
    }
	reqURL="ajaxAction.do?operation=checkpassword&password="+str;
	xmlhttp.open("GET",reqURL,true);
	xmlhttp.onreadystatechange=function(){
		if(xmlhttp.readyState=4 && xmlhttp.status==200){
			document.getElementById("password").innerHTML=xmlhttp.responseText;
		}
	};
	xmlhttp.send();
}
</script>
 
 <html>
  <head><title></title>
  </head>
  <body>
        <h2>Login page</h2>
        <form name="loginForm" method="POST" action="j_security_check">
            <p>User name: <input type="text" name="j_username" size="20" onkeyup="func(this.value)"/></p>
            <p>Password: <input type="password" size="20" name="j_password"/></p>
            <p>  <input type="submit" value="Submit" id="submit"/></p>
        </form>
<br>

<p><span id="message"></span></p>
<br>
<a href="/task/reset.do?operation=just">reset</a>		
</body>		

</html> 