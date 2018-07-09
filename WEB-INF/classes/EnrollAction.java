package com.zoho;

import java.io.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.actions.*;
import java.util.*;
import java.sql.*;
	  
public class EnrollAction extends DispatchAction{
	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session=request.getSession(false);
		String username=(String)session.getAttribute("username");
		/*
		try {
            String connectionURL = "jdbc:mysql://localhost:3306/tomcat_realm";
            Connection connection = null; 
            Class.forName("com.mysql.jdbc.Driver").newInstance(); 
            connection = DriverManager.getConnection(connectionURL, "root", "");
            if(!connection.isClosed()){
				Statement stmt=connection.createStatement();
				ResultSet rs=stmt.executeQuery("select * from questiontable");
				rs.next();
				request.setAttribute("question1",rs.getString(2));
				rs.next();
				request.setAttribute("question2",rs.getString(2));
				//setQuestion2(rs.getString(2));
				
			}
            connection.close();
			return mapping.findForward("success");
        }
		
	catch(Exception ex){
			System.out.println("This is the exception "+ex+"\n");
          return mapping.findForward("jdbc");
        }*/
		return mapping.findForward("success");
	}
	
	public ActionForward enrollUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
		HttpSession session=request.getSession(false);
		String username=(String)session.getAttribute("username");
		String mailselect=(String)request.getParameter("mailselect");
		String phoneselect=(String)request.getParameter("phoneselect");
		EnrollHandler handler=new EnrollHandler();
		String result,result1;;
		System.out.println("key \n"+mailselect);
		System.out.println(phoneselect);
		if(mailselect.equals("mail") ){
			if(phoneselect.equal("phone")){
			result=handler.enrollmail(username, (String)request.getParameter("mailid"));
			System.out.println("\nhaii"+result);
			result1=handler.enrollphone(username, (String)request.getParameter("phone"));
			System.out.println("\nhaii"+result);
			}
			else{
				result=handler.enrollmail(username, (String)request.getParameter("mailid"));
			}
		}
		else{
			//result=handler.enrollphone(username,(String)request.getParameter("phone"));
		}
		
		//EnrollHandler handler=new EnrollHandler(username, (String)request.getParameter("answer1"), (String)request.getParameter("answer2"));
		
		
		request.setAttribute("enrollinfo","enrolled "+mailselect+" "+phoneselect+" "+(mailselect.equals("mail")+" "+result+" "+result1);
		return mapping.findForward(result);
		
	}
}
