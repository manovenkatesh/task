package com.zoho;

import java.io.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.actions.*;
import java.util.*;
import java.sql.*;



public class ajaxAction extends DispatchAction
{
    public ActionForward checkusername(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ActionErrors errors = new ActionErrors();
        ActionForward forward = new ActionForward(); // return value
        PrintWriter out = response.getWriter();
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.setStatus(HttpServletResponse.SC_OK);
		String username=request.getParameter("username");
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
					out.write("valid user");
				}
				else{
					out.write("invalid user");
				}
				connection.close();
			}
		}
		catch(Exception ex){
			out.write(""+ex);
		}
        return null;
    }
	public ActionForward checkpass(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		HttpSession session=request.getSession(false);
		String username=(String)session.getAttribute("username");
        ActionErrors errors = new ActionErrors();
        ActionForward forward = new ActionForward(); // return value
        PrintWriter out = response.getWriter();
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.setStatus(HttpServletResponse.SC_OK);
		String oldpass=request.getParameter("oldpass");
		try {
            String connectionURL = "jdbc:mysql://localhost:3306/tomcat_realm";
            Connection connection = null; 
            Class.forName("com.mysql.jdbc.Driver").newInstance(); 
            connection = DriverManager.getConnection(connectionURL, "root", "");
            if(!connection.isClosed()){
				String sql="SELECT password from tomcat_users where user_name=?";
				PreparedStatement stmt=connection.prepareStatement(sql);
				stmt.setString(1,username);
				ResultSet rs=stmt.executeQuery();
				rs.next();
				if(oldpass.equals(rs.getString(1))){
					out.write("oldpassword matched");
				}
				else{
					out.write("oldpassword not matched");
			}
			}
			connection.close();
			return null;
		}
		catch(Exception ex){
          return null;
        }
}}