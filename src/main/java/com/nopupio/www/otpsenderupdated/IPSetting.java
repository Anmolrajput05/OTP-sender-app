package com.nopupio.www.otpsenderupdated;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class IPSetting extends AppCompatActivity {

    EditText ipText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipsetting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ipText = findViewById(R.id.editTextIPSetting);

        String ip = null;
        try {
            ip = getIP();
        } catch (Exception e) {
            e.printStackTrace();
            ip = null;
        }
        if(ip!= null){
            ipText.setText(ip);
        }
        else {
            ipText.setText("");
        }
    }

    public void save_ip(View view) {
        DBIP dbip = new DBIP(this);
        String ip = ipText.getText().toString();
        dbip.saveIP(ip);
        Toast.makeText(this,"Host address Saved",Toast.LENGTH_LONG).show();
    }

    public String getIP() throws Exception{
        DBIP dbip = new DBIP(this);
        return dbip.getIP();
    }

}
