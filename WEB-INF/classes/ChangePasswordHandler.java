package com.zoho;

import java.io.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.actions.*;
import java.util.*;
import java.sql.*;

public class ChangePasswordHandler{
	public String changePassword(String username,String oldpass,String newpass,String conpass){
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
					if(newpass.equals(conpass)){
					sql="update tomcat_users set password=? where user_name=?";
					stmt=connection.prepareStatement(sql);
					stmt.setString(1,newpass);
					stmt.setString(2,username);
					stmt.executeUpdate();
					
					return "changed";
					}
					else{
						return "conpasswrong";
					}
				}
				else{
					return "oldpasswrong";
					
				}
				
			}
            connection.close();
			return "end";
        }
		
	catch(Exception ex){
          return "jdbc"+ex;
        }
	}

}