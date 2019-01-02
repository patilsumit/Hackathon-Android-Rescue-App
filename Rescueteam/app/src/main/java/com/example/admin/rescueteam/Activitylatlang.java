package com.example.admin.rescueteam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

/**
 * Created by lenovo on 24-Mar-18.
 */

public class Activitylatlang extends AppCompatActivity {
    String number;
    JSONObject jsonobject;
    String JSON_STRING;
    private RequestQueue mQueue;
    private ProgressDialog pd;
    private static final String URL_PRODUCTS = "https://womensafty.000webhostapp.com/PHP/fetch_latlagrescue.php";
    TextView name,lat,lang;
    Button btn_location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lati_long);
        name=(TextView)findViewById(R.id.txtname);
        lat=(TextView)findViewById(R.id.lat);
        lang=(TextView)findViewById(R.id.lang);
       btn_location=(Button)findViewById(R.id.btnLocation);
        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Activitylatlang.this,MapsActivity.class);
                startActivity(i);
            }
        });
            mQueue = Volley.newRequestQueue(this);
            try {
                jsonParse();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

    private void jsonParse() throws UnsupportedEncodingException {
        String data=null;
       /* SharedPreferences prf = getSharedPreferences("Rescue", Context.MODE_PRIVATE);
        final String str = prf.getString("rcontact", "none");
        data = "?rcontact=" + URLEncoder.encode(str, "UTF-8");*/
        String url = "https://womensafty.000webhostapp.com/PHP/fetch_latlagrescue.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("product");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                 jsonobject = jsonArray.getJSONObject(i);
                                name = (TextView) findViewById(R.id.txtname);
                                lat = (TextView) findViewById(R.id.lat);
                                lang = (TextView) findViewById(R.id.lang);

                                //String firstName = employee.getString("name");

                                String txtname = jsonobject.getString("name");
                                String txtlat = jsonobject.getString("lati");
                                String txtlang = jsonobject.getString("lang");
                              //  String txtmobile = jsonobject.getString("contact");

                                name.setText(txtname);
                                lat.setText(txtlat);
                                lang.setText(txtlang);
                                SharedPreferences myPrefs = getApplication().getSharedPreferences("contact", 0);
                                SharedPreferences.Editor prefsEditor = myPrefs.edit();
                                prefsEditor.putString("lati",txtlat);
                                prefsEditor.putString("logi",txtlang);
                                prefsEditor.putString("wname",txtname);
                                prefsEditor.commit();
                              //  state.setText(txtstate);

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

