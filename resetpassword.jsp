<%
if(request.getAttribute("info")!=null){
	out.print(request.getAttribute("info"));
}

%>
<script>
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
	if(newpass==conpass){
		document.getElementById("submit").disabled=false;
		document.getElementById("message").innerHTML="newpassword and conform password are same";
	}
	else{
		document.getElementById("message").innerHTML="newpassword and conform password are not same";
		document.getElementById("submit").disabled=true;
	}
	
}
</script>

<form action="reset.do?operation=resetpassword" method="post" name="pass">
	Newpassword: <input type="password" name="newpassword" id="newpassword" onkeyup="validate()"><br>
	conformPassword: <input type="password" name="conpassword" id="conpassword" onkeyup="validate()" ><br>
	<input type="submit" name="submit" id="submit">
</form>

<br>
<span id="message"></span>
<br>
<a href="/task/logout.do">Logout</a>