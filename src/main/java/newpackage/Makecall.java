/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpackage;

/**
 *
 * @author heba
 */

import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.http.HttpMethod;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author W10 21H2 IOT
 */
public class Makecall {
       static  String Status;

    public Call createcall(String ACCOUNT_SID,String AUTH_TOKEN,String sendTo,String Twilio_number ) throws SQLException
  {
         Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Call call = Call.creator(
                new com.twilio.type.PhoneNumber(sendTo),
                new com.twilio.type.PhoneNumber(Twilio_number),
                URI.create("https://hagfish-sharing-ultimately.ngrok-free.app/servletCall/xmlcontent"))
            .setMethod(HttpMethod.GET).setSendDigits("1234#").create();
    waitForCallCompletion(call.getSid());
        return  call;
  }
    
    private static void waitForCallCompletion(String callSid) throws SQLException {
                DataBase DB = new DataBase();

        while (true) {
            Call call = Call.fetcher(callSid).fetch();
            Call.Status callStatus = call.getStatus();
 
            System.out.println("Call SID: " + call.getSid());
            System.out.println("Call Status: " + callStatus);
 
            if (callStatus == Call.Status.COMPLETED) {
                  Status="completed";
        DB.insertToDB(call.getSid(), call.getFrom(), call.getTo(), call.getDuration(), call.getDateCreated().toString(), call.getStatus().toString());
                break;
            }
 
            // Sleep for a while before checking again
            try {
                Thread.sleep(5000); // Sleep for 5 seconds (adjust as needed)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
  
  }