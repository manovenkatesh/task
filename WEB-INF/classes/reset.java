package com.zoho;

import java.io.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.actions.*;
import java.util.*;
import java.sql.*;
import org.json.JSONObject;
import org.json.JSONArray;

public class reset extends DispatchAction{
	public String username;
	public ActionForward just(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response){
		return mapping.findForward("success");
	}
	
	public ActionForward verify(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String username=(String)request.getParameter("username");
		HttpSession session=request.getSession(true);
		session.setAttribute("username",username);
		
		try {
            String connectionURL = "jdbc:mysql://localhost:3306/tomcat_realm";
            Connection connection = null; 
            Class.forName("com.mysql.jdbc.Driver").newInstance(); 
            connection = DriverManager.getConnection(connectionURL, "root", "");
            if(!connection.isClosed()){
				//Statement stmt=connection.createStatement();
				String sql= "SELECT * from tomcat_users where user_name=?";
				PreparedStatement stmt=connection.prepareStatement(sql);
				stmt.setString(1,username);
				ResultSet rs=stmt.executeQuery();
				if(rs.next()){
					sql="select mailid from mailtable where user_name=?";
					stmt=connection.prepareStatement(sql);
					stmt.setString(1,username);
					rs=stmt.executeQuery();
					JSONArray maillist=new JSONArray();
					int i=1;
					while(rs.next()){
						JSONObject mail=new JSONObject();
						mail.put(""+i+"",rs.getString(1));
						maillist.put(mail);
						i=i+1;
					}
					request.setAttribute("maillist",maillist);
					
					sql="select phonenumber from phonetable where user_name=?";
					stmt=connection.prepareStatement(sql);
					stmt.setString(1,username);
					rs=stmt.executeQuery();
					JSONArray phonelist=new JSONArray();
					i=1;
					while(rs.next()){
						JSONObject phone=new JSONObject();
						phone.put(""+i+"",rs.getString(1));
						System.out.println(rs.getString(1));
						phonelist.put(phone);
						i=i+1;
					}
					request.setAttribute("phonelist",phonelist);
					return mapping.findForward("user");
				}
				else{
					String message="non-valid user";
					request.setAttribute("info","username is not recognized perfectly");
					return mapping.findForward("nonuser");
				}
				
				/*
				if(rs.next()){
					//setId(rs.getInt(1));
					rs=stmt.executeQuery("select * from mailtable");
					rs.next();
					request.setAttribute("question1", rs.getString(2));
					//setQuestion1(rs.getString(2));
					rs.next();
					request.setAttribute("question2", rs.getString(2));
					//setQuestion2(rs.getString(2));
					
					return mapping.findForward("user");
				}
				else{
					request.setAttribute("info","username is not recognized perfectly");
					return mapping.findForward("nonuser");
				}*/
			}
            connection.close();
			return mapping.findForward("success");
        }
		
	catch(Exception ex){
		request.setAttribute("more","jdbc error");
          return mapping.findForward("jdbc");
        }
	}
	
	
	
	public ActionForward sendmail(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		HttpSession session=request.getSession(false);
		String username=(String)session.getAttribute("username");	
		
		String selector=(String)request.getParameter("selector");
		System.out.println(selector);
		if(selector.equals("mail")){
			mailHandler handler=new mailHandler();
			String mailid=(String)request.getParameter("mailid");
			int otp=handler.sendmail(mailid);
			session.setAttribute("otp",""+otp);
			return mapping.findForward("otpsend");
			
		}
		else{
			smsHandler handler=new smsHandler();
			String phone=(String)request.getParameter("phonenumber");
			System.out.println(phone);
			int otp=handler.sendsms(phone);
			System.out.println(otp);
			session.setAttribute("otp",""+otp);
			return mapping.findForward("otpsend");
		}
	}
	public ActionForward verifyotp(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String otp=request.getParameter("otp");
		HttpSession session=request.getSession(false);
		String sessionotp=(String)session.getAttribute("otp");
		if(sessionotp.equals(otp)){
			return mapping.findForward("matched");
		}
		else{
			return mapping.findForward("notmatched");
		}
		
	}
	
	
	public ActionForward resetpassword(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession(false);
		String username=(String)session.getAttribute("username");
		String newpass=request.getParameter("newpassword");
		String conpass=request.getParameter("conpassword");
		
		
		if(newpass.equals(conpass)){
			try {
            String connectionURL = "jdbc:mysql://localhost:3306/tomcat_realm";
            Connection connection = null; 
            Class.forName("com.mysql.jdbc.Driver").newInstance(); 
            connection = DriverManager.getConnection(connectionURL, "root", "");
            if(!connection.isClosed()){
				//Statement stmt=connection.createStatement();
				String sql="update tomcat_users set password=? where user_name=?";
					PreparedStatement stmt=connection.prepareStatement(sql);
					stmt.setString(1,newpass);
					stmt.setString(2,username);
					stmt.executeUpdate();
					 connection.close();
					return mapping.findForward("reset");
			}
            connection.close();
        }
		
	catch(Exception ex){
          return mapping.findForward("success");
        }
		return mapping.findForward("success");
	}
	else{
		request.setAttribute("info", "newpassword and conform password are different");
		return mapping.findForward("failure");
	}
		
		
	}
	
	}