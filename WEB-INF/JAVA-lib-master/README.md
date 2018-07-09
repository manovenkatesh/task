JAVA sample code for integration with MoceanSMS API Server.

With MoceanSMS REST API you can send SMS message, query account balance and pricing info.

Read more over at dev.moceansms.com/restapi

Examples
--------------
import moceanSMS.moceanSMS;

1) Send SMS

    moceanSMS moceansms = new moceanSMS("api_key","api_secret");
    String rest_response = moceansms.sendSMS("Mocean", "60123456789", "Hello");
    System.out.println(rest_response);


2) Query account balance

    moceanSMS moceansms = new moceanSMS("api_key","api_secret");
    String rest_response = moceansms.accountBalance();
    System.out.println(rest_response);


3) Query account pricing

    moceanSMS moceansms = new moceanSMS("api_key","api_secret");
    String rest_response = moceansms.accountPricing("502","16");
    System.out.println(rest_response);


4) Query message status

    moceanSMS moceansms = new moceanSMS("api_key","api_secret");
    String rest_response = moceansms.messageStatus("mocean123");
    System.out.println(rest_response);


--------------
# Steps to add SSL cert to keystore

1) Download InstallCert.java & Compile
javac InstallCert.java

2) Access server, and retrieve certificate (accept default certificate 1)
java InstallCert rest-api.moceansms.com:443

3) Extract certificate from created jssecacerts keystore
keytool -exportcert -alias rest-api.moceansms.com-1 -keystore jssecacerts -storepass changeit -file rest-api.moceansms.com-1.cer

4) Import certificate into system keystore
keytool -importcert -alias rest-api.moceansms.com-1  -keystore [path to system keystore] -storepass changeit -file rest-api.moceansms.com-1.cer

# Hint:
[path to system keystore] can refer to the first line after running cmd "java InstallCert rest-api.moceansms.com:443" 
e.g: Loading KeyStore /Library/Java/JavaVirtualMachines/jdk1.8.0_121.jdk/Contents/Home/jre/lib/security/cacerts
keytool -importcert -alias rest-api.moceansms.com-1  -keystore /Library/Java/JavaVirtualMachines/jdk1.8.0_121.jdk/Contents/Home/jre/lib/security/cacerts -storepass changeit -file rest-api.moceansms.com-1.cer
