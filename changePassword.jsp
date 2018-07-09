<% if(request.getAttribute("cpresult")!=null){
		if(request.getAttribute("cpresult")=="oldpasswrong"){
			out.print("oldpassword not matched");
		}
		else if(request.getAttribute("cpresult")=="conpasswrong"){
			out.print("conformpassword and newpassword were not matched");
		}
}
%>
<script>
var oldpass=false;
function func(str)
{
    //Creating a new XMLHttpRequest object
    var xmlhttp;
    if (window.XMLHttpRequest){
        xmlhttp = new XMLHttpRequest(); 
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); 
    }
	reqURL="ajaxAction.do?operation=checkpass&oldpass="+str;
    //Create a asynchronous GET request
    xmlhttp.open("GET", reqURL, true);
	    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200 ) {
                document.getElementById("message").innerHTML = xmlhttp.responseText;
				if(xmlhttp.responseText=="invalid user"){
					document.getElementById("submit").disabled=true;
					oldpass=false;
				}
				else{
					document.getElementById("submit").disabled=false;
					oldpass=true;
				}
        }
    };
	xmlhttp.send();
	
}


function validate()
{
	 var xmlhttp;
    if (window.XMLHttpRequest){
        xmlhttp = new XMLHttpRequest(); 
    } 
	else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); 
    }
	var newpass=document.forms["pass"]["newpassword"].value;
	var conpass=document.forms["pass"]["conpassword"].value;
	if(oldpass==true){
		if(newpass==conpass){
		document.getElementById("submit").disabled=false;
		document.getElementById("message").innerHTML="newpassword and conform password are same";
	}
	else{
		document.getElementById("message").innerHTML="newpassword and conform password are not same";
		document.getElementById("submit").disabled=true;
	}
		
	}
	
	
}
</script>



<form action="changePassword.do?operation=change" method="post" name="pass" >
	OldPassword: <input type="password" name="oldpassword" onkeyup="func(this.value)"><br>
	NewPassword: <input type="password" name="newpassword" onkeyup="validate()"><br>
	confirmPassword: <input type="password" name="conpassword" onkeyup="validate()"><br>
	<input type="submit" name="change" id="submit">
</form>
<br>

<span id="message"></span>
<br><br>
<a href="/task/logout.do">Logout</a>