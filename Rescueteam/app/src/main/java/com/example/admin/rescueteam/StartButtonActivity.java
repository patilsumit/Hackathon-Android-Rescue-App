package com.example.admin.rescueteam;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.test.mock.MockPackageManager;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by Arti on 3/11/2018.
 */

public class StartButtonActivity  extends Activity {

    private static final int REQUEST_CODE_PERMISSION = 2;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    Context context=this;
    // GPSTracker class
    GPSTracker gps;
    ImageButton startBtn,addContact,callBtn,rescueBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startbtn);
        SharedPreferences prf = getSharedPreferences("RMyPref", Context.MODE_PRIVATE);
        final String tempMobileNo = prf.getString("Uname", "none");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(tempMobileNo);
        ValueEventListener valueEventListener = myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);

//notification builder


               /* try {
                    Uri path = Uri.parse("android.resource://"+getPackageName()+"/raw/sound.mp3");
                    // The line below will set it as a default ring tone replace
                    // RingtoneManager.TYPE_RINGTONE with RingtoneManager.TYPE_NOTIFICATION
                    // to set it as a notification tone
                    RingtoneManager.setActualDefaultRingtoneUri(
                            getApplicationContext(), RingtoneManager.TYPE_RINGTONE,
                            path);
                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), path);
                    r.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }

        });

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                //execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        startBtn = (ImageButton) findViewById(R.id.button);
        addContact=(ImageButton)findViewById(R.id.btnAddContact);
        rescueBtn=(ImageButton)findViewById(R.id.btnlatlang);
        //lat=(TextView)findViewById(R.id.textViewLat);
        //lon=(TextView)findViewById(R.id.textViewLong);
        // show location button click event

        startBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //sendmessage();
                SendLocation();
                // create class object
                /*gps = new GPSTracker(StartButtonActivity.this);

                // check if GPS enabled
                if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                   // lat.setText(String.valueOf(latitude));

                    //lon.setText(String.valueOf(longitude));
                    // \n is for new line

                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                            + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }*/

            }
        });

        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendcontact();
                Intent intent = new Intent(context,WomenDetailsActivity.class);
                context.startActivity(intent);

            }
        });
        rescueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Activitylatlang.class);
                startActivity(i);
            }
        });

    }

    private void SendLocation() {
        gps = new GPSTracker(StartButtonActivity.this);
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

            String tempLat = String.valueOf(latitude);
            String tempLon = String.valueOf(longitude);
            SharedPreferences prf = getSharedPreferences("RMyPref", Context.MODE_PRIVATE);
            final String tempMobileNo = prf.getString("Uname", "none");
           // Toast.makeText(this, tempMobileNo, Toast.LENGTH_SHORT).show();
            //if (tempOTP != null && tempMobileNo != null) {
            Toast.makeText(this, "Processing...", Toast.LENGTH_SHORT).show();

            new StartButtonServerActivity(this).execute(tempLat, tempLon,tempMobileNo);

        }else{
            gps.showSettingsAlert();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Alert")
                        .setContentText("women safety");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

    }
}
