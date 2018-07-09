<%
String username = request.getRemoteUser();
session.setAttribute("username",username);
request.setAttribute("username",username);
out.print(username);
response.sendRedirect("/task/duosecurity.do?operation=request");
%>

