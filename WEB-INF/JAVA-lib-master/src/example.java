import java.io.IOException;
import moceanSMS.moceanSMS;

public class example {
	public static void main(String[] args) throws IOException {
		
		// Declare new MoceanSMS and specify API key and secret
		moceanSMS moceansms = new moceanSMS("api_key","api_secret");
		String rest_response;
		// 1. Send SMS - .sendSMS(from, to, message) method to send a message
		rest_response = moceansms.sendSMS("Mocean", "60123456789", "Hello");
		System.out.println(rest_response);   
				
		
		// 2. Query account balance
		rest_response = moceansms.accountBalance();
		System.out.println(rest_response);
	    
	    
	    // 3. Query account pricing .accountPricing(mcc, mnc)
		rest_response = moceansms.accountPricing("502","16");
		System.out.println(rest_response);
	    
	    
	    // 4. Query message status .messageStatus(msg_id)
		rest_response = moceansms.messageStatus("mocean123");
		System.out.println(rest_response);
	} 
}
