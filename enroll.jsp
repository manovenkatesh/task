<script>
	
function validatemail(){
	var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
	var x;
	x=document.forms["mail"]["mailid"].value;
	if(reg.test(x)==false){
		document.getElementById("message").innerHTML = "Please enter the valid mail id";
		document.getElementById("enroll").disabled=true;
		return false;
	}
	else{
		document.getElementById("message").innerHTML="its a valid mail id";
		document.getElementById("enroll").disabled=false;
		return true;
	}
	
}

function validate(){
	var c;
	c=document.forms["mail"]["phone"].value;
	if(c.length <10){
	document.getElementById("enroll").disabled=true;
	alert("Enter valid phone number");
	return false;
	}
	var x;
	x=document.forms["mail"]["mailid"].value;
	if(x==""){
		document.getElementById("enroll").disabled=true;
		alert("please fill mail id info");
		return false;
	}
	
}



</script>

<% request.setAttribute("mailselect","mail");
	request.setAttribute("mailselect","phone");
%>

<form action="enroll.do?operation=enrollUser" method="post" name="mail">
	Enter the email address:
	<input type="text" name="mailid" id="mailid" onkeyup="validatemail()"><br><br>
	Enter the phone number: <input type="text" name="phone" id="phone"><br>
	<input type="submit" id="enroll" name="enroll" onclick="validate()">

</form>
<span id="message" ></span>
<br>
<a href="/task/logout.do">Logout</a>