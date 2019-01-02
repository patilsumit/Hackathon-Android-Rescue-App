package com.example.admin.rescueteam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by lenovo on 10-Mar-18.
 */

public class ActivityOTP extends AppCompatActivity {
    EditText otp,contactNo;
    Button btnLogin;
    Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        otp = (EditText) findViewById(R.id.txt_otp);
        contactNo = (EditText) findViewById(R.id.txtContact);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login(v);
            }
        });
    }


        public void Login(View v) {
            String otpNo = otp.getText().toString();
            String contactno = contactNo.getText().toString();

            SharedPreferences pref1 = context.getSharedPreferences("RMyPrefR", Context.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor=pref1.edit();
            prefEditor.putString("contact",contactno);
            prefEditor.commit();

                Toast.makeText(this, "logging up...", Toast.LENGTH_SHORT).show();
                new OTPActivity(this).execute(contactno, otpNo);


        }


    }


