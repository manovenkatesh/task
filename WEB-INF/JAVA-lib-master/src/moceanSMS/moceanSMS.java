package moceanSMS;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.HttpsURLConnection;

public class moceanSMS {
	
	private final String USER_AGENT = "Mozilla/5.0";

    // Account credentials
    private String api_key;
    private String api_secret;

    // REST API URL
    public String rest_base_url = "https://rest-api.moceansms.com/rest/1";

    //public String[] rest_commands;
    public Map<String, HashMap<String, String>> rest_commands = new HashMap<String, HashMap<String, String>>();

    public String response_format = "json";

    public Map<String, Integer> message_type_option = new HashMap<String, Integer>();

    public moceanSMS(String api_key, String api_secret) {
        this.rest_commands.put("send_sms", new HashMap<String, String>());
        this.rest_commands.get("send_sms").put("url", "/sms");
        this.rest_commands.get("send_sms").put("method", "POST");

        this.rest_commands.put("get_message_status", new HashMap<String, String>());
        this.rest_commands.get("get_message_status").put("url", "/report/message");
        this.rest_commands.get("get_message_status").put("method", "GET");

        this.rest_commands.put("get_balance", new HashMap<String, String>());
        this.rest_commands.get("get_balance").put("url", "/account/balance");
        this.rest_commands.get("get_balance").put("method", "GET");

        this.rest_commands.put("get_pricing", new HashMap<String, String>());
        this.rest_commands.get("get_pricing").put("url", "/account/pricing");
        this.rest_commands.get("get_pricing").put("method", "GET");

        this.message_type_option.put("7-bit", 1);
        this.message_type_option.put("8-bit", 2);
        this.message_type_option.put("Unicode", 3);

        this.api_key = api_key;
        this.api_secret = api_secret;
    }

    public String sendSMS(String from, String to, String message) throws IOException {
        return this.sendSMS(from, to, message, 0, "", "");
    }

    public String sendSMS(String from, String to, String message, Integer message_type) throws IOException {
        return this.sendSMS(from, to, message, message_type, "", "");
    }

    public String sendSMS(String from, String to, String message, Integer message_type, String dlr_url) throws IOException {
        return this.sendSMS(from, to, message, message_type, dlr_url, "");
    }

    public String sendSMS(String from, String to, String message, Integer message_type, String dlr_url, String udh) throws IOException {
        // Auto detect message type
        // Convert to UTF-16 hexadecimal if it's unicode SMS
        if (message_type == 0) {
            boolean unicode = checkIsUnicode(message);
            if (unicode) {
                message = unicodeConversion(message);
                message_type = this.message_type_option.get("Unicode");
            } else {
                message_type = this.message_type_option.get("7-bit");
            }
        }
        
        // Send request to MoceanSMS gateway
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mocean-from", from);
        params.put("mocean-to", to);
        params.put("mocean-text", message);
        params.put("mocean-coding", message_type);
        params.put("mocean-charset", "UTF-8");
        params.put("mocean-dlr-mask", 1);
        params.put("mocean-dlr-url", dlr_url);
        params.put("mocean-udh", udh);
        
        return this.invokeApi("send_sms", params);
    }

    private static boolean checkIsUnicode(String message) {
        // Check if message contains non GSM characters
        String gsm_greek_character_ascii_value = ",132,133,134,135,137,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,163,164,165,166,167,168,169,172,177,178,182,184,185,188,191,194,195,206,226,130,172,";

        for (int i = 0; i < message.length(); i++) {
            String pattern = "," + (int) message.charAt(i) + ",";
            if ((int) message.charAt(i) > 127 && !pattern.matches(gsm_greek_character_ascii_value)) {
                return true;
            }
        }
        return false;
    }

    private static String unicodeConversion(String message) throws UnsupportedEncodingException {
        //Convert string to UTF-16 hexadecimal		
        message = strToHex(message);           
  
        return message;
    }

    public static String strToHex(String base) {
        StringBuffer buffer = new StringBuffer();
        int intValue;
        for (int x = 0; x < base.length(); x++) {
            intValue = base.charAt(x);
            buffer.append(Integer.toHexString(0x10000 | intValue).substring(1));
        }
        return buffer.toString();
    }
    
    public String invokeApi(String command) throws IOException {
    	Map<String, Object> params = new HashMap<String, Object>();
        return this.invokeApi(command, params);
    }

    public String invokeApi(String command, Map<String, Object> params) throws IOException {
        // Get REST URL and HTTP method
        HashMap<String, String> command_info = this.rest_commands.get(command);
        String url = this.rest_base_url + command_info.get("url");
        String method = command_info.get("method");
        
        // Build the data string
        params.put("mocean-api-key", this.api_key);
        params.put("mocean-api-secret", this.api_secret);
        params.put("mocean-resp-format", this.response_format);

        String urlParameters = this.getQuery(params);
       
        // check sumbit method GET/POST
        if(method.equals("GET"))
        {
        	url += "?"+urlParameters;
        }

        // INIT URL Object
        URL urlObject = new URL(url);
        
        HttpURLConnection httpcon = (HttpURLConnection) urlObject.openConnection();
        if (url.matches("/http:/")) {
            httpcon = (HttpURLConnection) urlObject.openConnection();
        } else if (url.matches("/https:/")) {
            HttpsURLConnection httpscon = (HttpsURLConnection) urlObject.openConnection();
            httpcon = httpscon;

        }

        httpcon.setRequestMethod(method);
        httpcon.setRequestProperty("User-Agent", USER_AGENT);
        httpcon.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        
        httpcon.setDoOutput(true);
        
        // post request
        if(method.equals("POST"))
        {
        	// add parameter
	 		DataOutputStream dOS = new DataOutputStream(httpcon.getOutputStream());
	 		dOS.writeBytes(urlParameters);
	 		dOS.flush();
	 		dOS.close();
        }

        // read response
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferReader.readLine()) != null) {
        	stringBuilder.append(line+"\n");
        }
        bufferReader.close();
        return stringBuilder.toString();
    }

    public String getQuery(Map<String, Object> params) throws UnsupportedEncodingException {
        String query_string = "";
        
        Iterator<Entry<String, Object>> it = params.entrySet().iterator();
        while (it.hasNext()) {   
            Map.Entry pairs = (Map.Entry) it.next();
            pairs.setValue(pairs.getValue().toString());
            query_string += '&' + URLEncoder.encode((String) pairs.getKey(), "UTF-8") + '=' + URLEncoder.encode((String) pairs.getValue(), "UTF-8");
            it.remove(); // avoids a ConcurrentModificationException
        }
        query_string = query_string.substring(1, query_string.length());

        return query_string ;
    }
    
    public String messageStatus(String msgid) throws IOException
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mocean-msgid", msgid);

        return this.invokeApi("get_message_status", params);
    }
    
    public String accountBalance() throws IOException
    { 
        return this.invokeApi("get_balance");
    }    
    
    public String accountPricing(String mcc, String mnc) throws IOException
    {
        Map<String, Object> params = new HashMap<String, Object>();
        if(mcc != null) {
            params.put("mocean-mcc", mcc);
        } 
        if(mnc != null) {
            params.put("mocean-mnc", mnc);
        }
        return this.invokeApi("get_pricing", params);
    }
}
