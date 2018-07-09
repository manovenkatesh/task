<%
if(request.getAttribute("info")!=null){
	out.print(request.getAttribute("info"));
}
%>


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

</script>
<form action="reset.do?operation=verify" method="post">
	<input type="text" name="username" onkeyup="func(this.value)" >
	<input type="submit" name="submit" id="submit" >
</form>
<p><span id="message"></span></p> 