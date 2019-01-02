package com.example.admin.rescueteam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ImageView imageView = (ImageView) findViewById(R.id.logo);

        SharedPreferences prf = getSharedPreferences("RMyPref", Context.MODE_PRIVATE);
        final String str = prf.getString("Uname", "none");

        if (str.equals("none")) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Intent in = new Intent(MainActivity.this, ActivityOTP.class);
                    startActivity(in);
                }


            }).start();
        /*imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigateRegister(view);
            }
        });*/
        } else {
            Intent in = new Intent(MainActivity.this, StartButtonActivity.class);
            startActivity(in);
        }
    }


}
