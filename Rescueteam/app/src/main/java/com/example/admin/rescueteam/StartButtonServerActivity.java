package com.example.admin.rescueteam;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by lenovo on 21-Mar-18.
 */

public class StartButtonServerActivity extends AsyncTask<String,Void,String> {
    Context context;
    public StartButtonServerActivity(Context context){
        this.context=context;
    }

    protected void onPreExecute() {

    }
    @Override
    protected String doInBackground(String[] strings) {
        String lat = strings[0];
        String lon=strings[1];
        String num=strings[2];
        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        //Toast.makeText(context.getApplicationContext(),"Num="+num,Toast.LENGTH_LONG).show();
        try {

            data = "?lati=" + URLEncoder.encode(lat, "UTF-8");
            data += "&lang=" + URLEncoder.encode(lon, "UTF-8");
            data += "&rcontact=" + URLEncoder.encode(num,"UTF-8");

            link = "https://womensafty.000webhostapp.com/PHP/getwrequest.php" + data;

            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            return result;
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }
    @Override
    protected void onPostExecute(String result) {
        String jsonStr = result;
        if (jsonStr != null) {
          //* try {
               // JSONObject jsonObj = new JSONObject(jsonStr);
                //String query_result = jsonObj.getString("query_result");
                //if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, jsonStr, Toast.LENGTH_SHORT).show();
            SharedPreferences pref = context.getSharedPreferences("RRes", Context.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor=pref.edit();
            prefEditor.putString("wcontact",jsonStr);
            prefEditor.commit();
            addNotification();
            MediaPlayer mp = MediaPlayer.create(context, R.raw.sound);
            try {
                if (mp.isPlaying()) {
                    mp.stop();
                    mp.release();
                    mp = MediaPlayer.create(context, R.raw.sound);
                } mp.start();
            } catch(Exception e) { e.printStackTrace(); }

                    /*Intent intent = new Intent(context,StopButtonActivity.class);
                    context.startActivity(intent);*/

              //  } else if (query_result.equals("FAILURE")) {
                //    Toast.makeText(context, "Location does not send.", Toast.LENGTH_SHORT).show();
                //} else {
                    //Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                //}
            /*} catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
            }*/
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }

    }
    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Alert")
                        .setContentText("women safety");

        Intent notificationIntent = new Intent(context, StartButtonActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

    }
}


