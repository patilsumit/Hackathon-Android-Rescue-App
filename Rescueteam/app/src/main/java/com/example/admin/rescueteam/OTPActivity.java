package com.example.admin.rescueteam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by lenovo on 10-Mar-18.
 */

public class OTPActivity extends AsyncTask<String, Void, String> {

    private Context context;

    public OTPActivity(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String[] arg) {
        String contact = arg[0];
        String otp= arg[1];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "?contact=" + URLEncoder.encode(contact, "UTF-8");
            data += "&otp=" + URLEncoder.encode(otp, "UTF-8");


            link = "https://womensafty.000webhostapp.com/PHP/adminotpverify.php" + data;
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
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                String query_result = jsonObj.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, "OTP inserted successfully. Login successful.", Toast.LENGTH_SHORT).show();
                    SharedPreferences pref = context.getSharedPreferences("RMyPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit= pref.edit();

                    SharedPreferences pref1 = context.getSharedPreferences("RMyPrefR", Context.MODE_PRIVATE);
                    String mobNo=pref1.getString("contact",null);
                    edit.putString("Uname",mobNo);
                    edit.commit();

                Intent intent = new Intent(context,StartButtonActivity.class);
                    context.startActivity(intent);

                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Data could not be inserted. Signup failed.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }
    }
}
