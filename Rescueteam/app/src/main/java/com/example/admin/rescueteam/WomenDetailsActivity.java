package com.example.admin.rescueteam;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by admin on 25-03-2018.
 */

public class WomenDetailsActivity extends Activity {
    String number;
    String JSON_STRING;
    private RequestQueue mQueue;
    TextView name, address, city, state, contact, telephone_no, guardian_name, guardian_no;
    private ProgressDialog pd;
    private static final String URL_PRODUCTS = "https://womensafty.000webhostapp.com/PHP/fetch_women.php";
Context context=this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.women_details);
        mQueue = Volley.newRequestQueue(this);
        jsonParse();
        // new GetWomenActivity(this).execute();
       name = (TextView) findViewById(R.id.tvName);
        address = (TextView) findViewById(R.id.tvAddress);
        city = (TextView) findViewById(R.id.tvCity);
        state = (TextView) findViewById(R.id.tvState);
        contact = (TextView) findViewById(R.id.tvPhoneNo);
        guardian_name = (TextView) findViewById(R.id.tvGuardianName);
        guardian_no = (TextView) findViewById(R.id.tvGuardianNo);

        //String s = getIntent().getStringExtra("txtname");
        //String s1 = getIntent().getStringExtra("txtaddress");
        //name.setText(s);
        //address.setText(s1);

        /*try {
            Loaddata();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

    }
    private void jsonParse() {
    String data = null;
        SharedPreferences pref = context.getSharedPreferences("RRes", Context.MODE_PRIVATE);
        String str = pref.getString("wcontact", "none");
        Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG).show();
        try {
            data = "?wcontact=" + URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        String url = "https://womensafty.000webhostapp.com/PHP/fetch_women.php"+ data;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("product");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonobject = jsonArray.getJSONObject(i);
                                name = (TextView) findViewById(R.id.tvName);
                                address = (TextView) findViewById(R.id.tvAddress);
                                city = (TextView) findViewById(R.id.tvCity);
                                state = (TextView) findViewById(R.id.tvState);
                                contact = (TextView) findViewById(R.id.tvPhoneNo);
                                guardian_name = (TextView) findViewById(R.id.tvGuardianName);
                                guardian_no = (TextView) findViewById(R.id.tvGuardianNo);
                                //String firstName = employee.getString("name");
                                String id = jsonobject.getString("wid");
                                String txtname = jsonobject.getString("name");
                                String txtaddress = jsonobject.getString("address");
                                String txtcity = jsonobject.getString("city");
                                String txtmobile = jsonobject.getString("contact");
                                String txtstate = jsonobject.getString("state");
                                String txtguardian_name = jsonobject.getString("guardian_name");
                                String txtguardian_number = jsonobject.getString("guardian_number");
                                name.setText(txtname);
                                address.setText(txtaddress);
                                city.setText(txtcity);
                                state.setText(txtstate);
                                contact.setText(txtmobile);
                                guardian_name.setText(txtguardian_name);
                                guardian_no.setText(txtguardian_number);

                                // int age = employee.getInt("age");
                                //String mail = employee.getString("mail");

                                //  mTextViewResult.append(firstName + ", " + String.valueOf(age) + ", " + mail + "\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);

    }
}
