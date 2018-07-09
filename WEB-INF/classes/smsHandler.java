package com.zoho;

import java.io.IOException;
import moceanSMS.moceanSMS;
import java.util.Random;

public class smsHandler{
	public int sendsms(String phonenumber)throws IOException{
		moceanSMS moceansms = new moceanSMS("mano123","venkatesh");
		String rest_response;
		Random rand=new Random();
		int otp=rand.nextInt(100000);
		phonenumber="91"+phonenumber;
		rest_response = moceansms.sendSMS("manovenkatesh", phonenumber, "OTP to change password is "+otp);
		System.out.println(rest_response);
		return otp;
	}
}