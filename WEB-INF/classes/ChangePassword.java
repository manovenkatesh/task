package com.zoho;

import java.io.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.actions.*;
import java.util.*;
import java.sql.*;

public class ChangePassword extends DispatchAction{
	public ActionForward view(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response){
		return mapping.findForward("success");
	}
	public ActionForward change(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		HttpSession session=request.getSession(false);
		String username=(String)session.getAttribute("username");
		String oldpass=(String)request.getParameter("oldpassword");
		String newpass=(String)request.getParameter("newpassword");
		String conpass=(String)request.getParameter("conpassword");
		ChangePasswordHandler handler=new ChangePasswordHandler();
		String result=handler.changePassword(username,oldpass,newpass,conpass);
		request.setAttribute("cpresult",result);
		return mapping.findForward(result);
	}
	
	
}