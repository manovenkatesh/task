<% 
String username = request.getRemoteUser();
	session.setAttribute("username",username);
	request.setAttribute("username",username);




%> 

<% 


if(request.getAttribute("enrollinfo")!=null){
	out.println("You have enrolled successfully");
}
else if(request.getAttribute("cpresult")!=null){
	out.println("you have changed password successfully");
}
else{
		out.println("Hi "+username+"\n welcome to this website you can change password or Enroll the answers for your future use");
}
%>
<br>
<a href="/task/enroll.do?operation=view">Enroll</a>
<br>
<a href="/task/changePassword.do?operation=view">ChangePassword</a>
<br>
<a href="/task/logout.do">logout</a>
