package com.zoho;

import java.io.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.actions.*;
import java.util.*;
import java.sql.*;
import com.adventnet.persistence.DataAccess;
import com.adventnet.persistence.Row;
import com.adventnet.ds.query.Column;
import com.adventnet.persistence.DataObject;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.SelectQuery;
import com.adventnet.ds.query.UpdateQuery;
import com.adventnet.ds.query.UpdateQueryImpl;
import java.util.Iterator;
import com.adventnet.persistence.WritableDataObject;


public class duosecure extends DispatchAction{
	public ActionForward request(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		HttpSession session=request.getSession(false);
		String username=(String)session.getAttribute("username");
		System.out.println("your username is "+username);	
		DuoWeb dw=new DuoWeb();
		String sig_request=dw.signRequest("DI5I31SH2EZW01DI1IY6","FuPfVPKAU7pSB6G1XEzMNRUFtJpeNfkwkzUjUvIa","ebb3c8e46e376d7c8fea9fc3b49cb9331db6d42e",username);
		session.setAttribute("sig_request",sig_request);
		try{
			Row row=new Row("Users");
			row.set("USERNAME",username);
			Criteria c=new Criteria(new Column("Users","USERNAME"),username,QueryConstants.EQUAL);
			DataObject d=DataAccess.get("Users",c);
			int n=(int)d.getValue("Users","DUOSTATUS",row);
			if(n==0){
				session.setAttribute("duostatus","Nonuser");
			}
			else{
				session.setAttribute("duostatus","user");
			}
		}
		catch(Exception ex){
          System.out.println(""+ex);
        }
		
		return mapping.findForward("getotp");
	}
	
	public ActionForward response(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		HttpSession session=request.getSession(false);
		String username=(String)session.getAttribute("username");
		DuoWeb dw=new DuoWeb();
		String sig_response=(String)request.getParameter("sig_response");
		String authenticated= dw.verifyResponse("DI5I31SH2EZW01DI1IY6","FuPfVPKAU7pSB6G1XEzMNRUFtJpeNfkwkzUjUvIa","ebb3c8e46e376d7c8fea9fc3b49cb9331db6d42e",sig_response);
		try{
			UpdateQuery updateQuery = new UpdateQueryImpl("Users");
					updateQuery.setUpdateColumn("DUOSTATUS",1);
					Criteria criteria = new Criteria(new Column("Users","USERNAME"),username,QueryConstants.EQUAL);
					updateQuery.setCriteria(criteria);
					DataAccess.update(updateQuery);	
		}
		catch(Exception ex){
          System.out.println(""+ex);
        }
		
		
		if(authenticated!= null ){
			return mapping.findForward("success");
		}
		else{
			return mapping.findForward("failure");	
		}
		
	}
	
}