package com.zoho;
import duo.duosecurity.duoweb;
import java.io.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;
//import org.apache.struts.actions.*;
import java.util.*;
	  
public class ViewAction extends org.apache.struts.action.Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session=request.getSession(false);
		session.setAttribute("username",request.getRemoteUser("username"));
		username=request.getRemoteUser("username");
		DuoWeb dw=new DuoWeb();
		String sig_request=dw.signrequest("DI5I31SH2EZW01DI1IY6","FuPfVPKAU7pSB6G1XEzMNRUFtJpeNfkwkzUjUvIa","ebb3c8e46e376d7c8fea9fc3b49cb9331db6d42e",username);
		session.setAttribute("sig_request",sig_request);
		return mapping.findForward("success");
	}
}


