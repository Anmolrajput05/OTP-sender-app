package com.nopupio.www.otpsenderupdated;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    ScrollView scrollView;
    public Context context ;
    public Handler handler = new Handler();
    public  Handler handlerSms = new Handler();

    private String getUrl(){
        DBIP dbip = new DBIP(this);
        String ip = dbip.getIP();
        //String ip = "192.168.1.104/parking/";
        final String loginURL = "http://"+ip+"/get_otp_request.php";
        return loginURL;
    }
    private String getUrlUpdate(){
        DBIP dbip = new DBIP(this);
        String ip = dbip.getIP();
        //String ip = "192.168.1.104/parking/";
        final String loginURL = "http://"+ip+"/register_otp_confirmation.php";
        return loginURL;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv =(TextView) findViewById(R.id.textView);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        context = this;
    }





    public void onClickStart(View view) {
        requesterThread.start();
    }

    public void onClickIPSetting(View view) {
        Intent intent = new Intent(this,IPSetting.class);
        startActivity(intent);
    }




    private Thread requesterThread = new Thread(){
        @Override
        public void run(){
            sendCheckRequest();
        }

    };

    private Thread waitAndSend = new Thread(){
        @Override
        public void run(){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendCheckRequest();
        }

    };





    private void sendCheckRequest(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, getUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onResponseReceived(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                messageToast(error.getMessage());
                setMessage(error.getMessage());
            }
        }){
            protected Map<String, String> getParams() {
                Map<String, String> myData = new HashMap<String, String>();
                myData.put("get_otp_request", "e");
                return myData;
            }
        };
        queue.add(myStringRequest);
    }

    public void onResponseReceived(String response){
            final OTPRequest otpRequest = new Gson().fromJson(response,new TypeToken<OTPRequest>(){}.getType());
            setMessage("\nRESPONSE :"+response);
            if(otpRequest.getUserPhone().equals("NO_REQUESTS")) {
                waitAndSend.start();
            }
            else{
                setMessage("\nSending sms to"+otpRequest.getUserPhone() +"\nOTP : "+otpRequest.getOTP());
                handlerSms.post(new Runnable() {
                                 @Override
                                 public void run() {
                                     SmsSender smsSender = new SmsSender(context, otpRequest.getUserPhone(), otpRequest.getMessage()
                                             + otpRequest.getOTP());
                                     smsSender.sendSms();
                                 }
                             });
                sendUpdateRequest(otpRequest);
            }
    }


    private void sendUpdateRequest(final OTPRequest otpRequest){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, getUrlUpdate(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onResponseReceivedUpdateRequest(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                messageToast(error.getMessage());
                setMessage(error.getMessage());
            }
        }){
            protected Map<String, String> getParams() {
                Map<String, String> myData = new HashMap<String, String>();
                myData.put("update_otp", "T");
                myData.put("userPhone", otpRequest.getUserPhone());
                myData.put("otp_for", otpRequest.getOtp_for());
                return myData;
            }
        };
        queue.add(myStringRequest);
    }


    public void onResponseReceivedUpdateRequest(String response){
        setMessage("\nRESPONSE :"+response);
            waitAndSend.start();
    }



    public void setMessage(final String mess){

        handler.post(new Runnable() {
            @Override
            public void run() {
                tv.setText(tv.getText().toString() +"\n> "+mess);
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });


    }


    private void messageToast(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }


}
