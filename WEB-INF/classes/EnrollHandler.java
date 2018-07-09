package com.zoho;

import java.io.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.actions.*;
import java.util.*;
import java.sql.*;

public class EnrollHandler{
	String username,mailid,phone;
	public String enrollmail(String username,String mailid){
		try  {
            String connectionURL = "jdbc:mysql://localhost:3306/tomcat_realm";
            Connection connection = null; 
            Class.forName("com.mysql.jdbc.Driver").newInstance(); 
            connection = DriverManager.getConnection(connectionURL, "root", "");
			
            if(!connection.isClosed()){
				
				//Statement stmt=connection.createStatement();
				String sql="SELECT * from mailtable where user_name=? AND mailid=?";
				PreparedStatement stmt=connection.prepareStatement(sql);
				stmt.setString(1,username);
				stmt.setString(2,mailid);
				ResultSet rs=stmt.executeQuery();
				if(rs.next()){
					connection.close();
					return "enrolled";
				}
				else{
					sql="INSERT INTO mailtable (user_name,mailid) values (?,?)";
					stmt=connection.prepareStatement(sql);
					stmt.setString(1, username);
					stmt.setString(2,mailid);
					stmt.executeUpdate();
					connection.close();
					return "enrolled";
				}
				
		           // connection.close();
        }
		}
	catch(Exception ex){
			System.out.println("\n Exception in your file "+ex+"\n");
          return "jdbc";
        }
		return "failed";
	}
	public String enrollphone(String username,String phone){
		try  {
            String connectionURL = "jdbc:mysql://localhost:3306/tomcat_realm";
            Connection connection = null; 
            Class.forName("com.mysql.jdbc.Driver").newInstance(); 
            connection = DriverManager.getConnection(connectionURL, "root", "");
			
            if(!connection.isClosed()){
				
				//Statement stmt=connection.createStatement();
				String sql="SELECT * from phonetable where user_name=? AND phonenumber=?";
				PreparedStatement stmt=connection.prepareStatement(sql);
				stmt.setString(1,username);
				stmt.setString(2,phone);
				ResultSet rs=stmt.executeQuery();
				if(rs.next()){
					connection.close();
					return "enrolled";
				}
				else{
					sql="INSERT INTO phonetable (user_name,phonenumber) values (?,?)";
					stmt=connection.prepareStatement(sql);
					stmt.setString(1, username);
					stmt.setString(2,phone);
					stmt.executeUpdate();
					connection.close();
					return "enrolled";
				}
				
		           // connection.close();
        }
		}
	catch(Exception ex){
			System.out.println("\n Exception in your file "+ex+"\n");
          return "jdbc";
        }
		return "failed";
	}
	/* REFERENCE
	
		if(rs.next()){
						sql="UPDATE answertable set answer=? where user_name=? AND qid=?";
						stmt=connection.prepareStatement(sql);
						stmt.setString(1,answer1);
						stmt.setString(2,username);
						stmt.setInt(3,1);
						
						stmt.executeUpdate();
						stmt=connection.prepareStatement(sql);
						stmt.setString(1,answer2);
						stmt.setString(2,username);
						stmt.setInt(3,2);
						stmt.executeUpdate();
						connection.close();
						return "enrolled";
				}
				else{
			
					sql="INSERT INTO answertable (user_name,qid, answer) values (?,?,?)";
					stmt=connection.prepareStatement(sql);
					stmt.setString(1, username);
					stmt.setInt(2,1);
					stmt.setString(3,answer1);
					
					stmt.executeUpdate();
					stmt=connection.prepareStatement(sql);
					stmt.setString(1,username);
					stmt.setInt(2,2);
					stmt.setString(3,answer2);
					stmt.executeUpdate();
					connection.close();
					return "enrolled";
				}
				
			}
*/



}