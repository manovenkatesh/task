<%@page import="org.json.*" %>

<% JSONArray maillist=(JSONArray)request.getAttribute("maillist"); 
	JSONArray phonelist=(JSONArray)request.getAttribute("phonelist");
%>

<script>
function mfunc(){
	document.getElementById("selector").value=document.getElementById("mail").value;
	
}
function pfunc(){
	document.getElementById("selector").value=document.getElementById("phone").value;
}
</script>

<form action="reset.do?operation=sendmail" method="post">
	<input type="radio" name="sendmessage" id="mail" value="mail" onclick="if(this.checked){mfunc()}">mail<br>
	<input type="radio" name="sendmessage" id="phone" value="phone" onclick="if(this.checked){pfunc()}">phone<br>
	<input type="hidden" name="selector" id="selector" value="mail">
	Choose your mail id:<select name="mailid">
	<% 
	String mailid;
	for(int i=0;i<maillist.length();i=i){

		 JSONObject mail = maillist.getJSONObject(i);
		 i=i+1;
		mailid=mail.optString(""+i+"");
		%>
	
	<option><%out.print(mailid);%></option>
	<%	}%>
	
	
	</select>
<br>
Choose your phonenumber:
	<%if(phonelist !=null){%>
	<select name="phonenumber">
	<% String phoneno;
		for(int i=0;i<phonelist.length();i=i){
			
			JSONObject phone=phonelist.getJSONObject(i);
			i=i+1;
			phoneno=phone.optString(""+i+"");
			%>
			<option>
			<%out.print(phoneno);%>
			</option>
			
		<% } %>
	
	</select>
	<%}%>
	<input type="submit" name="submit">
</form>

	<a href="/task/logout.do">Logout</a>