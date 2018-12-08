package com.nopupio.www.otpsenderupdated;

import android.content.Context;
import android.telephony.SmsManager;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by PS on 1/21/2017.
 */
public class SmsSender {
    private String phoneNo = null ;
    private String message = null;

    private Context context ;
    public SmsSender(Context context){
     this.context = context ;
    }
    public SmsSender(Context context, String phoneNo, String message){
        this.context = context;
        this.phoneNo = phoneNo;
        this.message = message;

    }

    public boolean sendSms(){
        if(phoneNo.equals(null)) {
            Toast.makeText(context,"Phone no is empty",Toast.LENGTH_SHORT).show();
            return false;
        }else if(message.equals(null)){
            Toast.makeText(context,"Message is empty",Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(context, "Message Sent",
                    Toast.LENGTH_LONG).show();
            return true;
        } catch (Exception ex) {
            Toast.makeText(context,ex.toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
            return false;
        }
    }





}
