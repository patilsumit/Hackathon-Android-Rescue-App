package com.example.admin.rescueteam;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lenovo on 10-Mar-18.
 */

 class GetWomenActivity extends AsyncTask<String, Void, String> {

    private Context context;
    TextView name, address, city, state, contact, telephone_no, guardian_name, guardian_no;

    public GetWomenActivity(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... arg0) {


        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {


            link = "https://womensafty.000webhostapp.com/PHP/fetch_women.php";
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
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
                if (query_result.equals("success")) {


                    String id = jsonObj.getString("id");
                    String txtname = jsonObj.getString("name");
                    String txtaddress = jsonObj.getString("address");
                    String txtcity = jsonObj.getString("city");
                    String txtmobile = jsonObj.getString("contact");
                    String txtstate = jsonObj.getString("state");
                    String txtguardian_name = jsonObj.getString("guardian_name");
                    String txtguardian_number = jsonObj.getString("guardian_number");

                    Intent intent = new Intent(context, GetWomenActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("name",txtname);
                    intent.putExtra("address",txtaddress);

                    context.startActivity(intent);
                   // Toast.makeText(context, "Data inserted successfully. Signup successful.", Toast.LENGTH_SHORT).show();
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
