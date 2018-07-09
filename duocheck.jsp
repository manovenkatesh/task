
<html>
<head>
<script src="js/Duo-Web-v2.js"></script>
<% String sig_request=(String)session.getAttribute("sig_request");
	out.print(sig_request);
%>

<style>
  #duo_iframe {
    width: 100%;
    min-width: 304px;
    max-width: 620px;
    height: 330px;
    border: none;
  }
</style>
<script>

Duo.init({
          iframe: "duo_iframe",
          host: "api-099e3aab.duosecurity.com",
          sig_request: "<%=sig_request%>",
          post_action: "duosecurity.do?operation=response",
          post_argument: "sig_response"
     });
</script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
</head>
<body>
	<iframe id="duo_iframe" width="620" height="500" frameborder="0" allowtransparency="true" ></iframe>
<body>
</html>