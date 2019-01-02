package com.example.admin.rescueteam;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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

/**
 * Created by lenovo on 11-Mar-18.
 */

public class ActivityDetailMap extends AppCompatActivity
{
    Button btnLocation;
    String number;
    String JSON_STRING;
    private RequestQueue mQueue;
    TextView textlang,textlat,textname;
    private ProgressDialog pd;
    Context context;
    private static final String URL_PRODUCTS = "https://womensafty.000webhostapp.com/PHP/fetch_latlagrescue.php";
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mQueue = Volley.newRequestQueue(this);
        jsonParse();

// new GetWomenActivity(this).execute();
       /* name = (TextView) findViewById(R.id.txtname);
        address = (TextView) findViewById(R.id.txtAddress);
        city = (TextView) findViewById(R.id.txtcity);
        state = (TextView) findViewById(R.id.txtstate);
        contact = (TextView) findViewById(R.id.txtmobile);
        guardian_name = (TextView) findViewById(R.id.txtguardian_name);
        guardian_no = (TextView) findViewById(R.id.txtguardian_no);
        String s = getIntent().getStringExtra("txtname");
        String s1 = getIntent().getStringExtra("txtaddress");
        name.setText(s);
        address.setText(s1);*/

        /*try {
            Loaddata();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

    }
    private void jsonParse() {
        String url = "https://womensafty.000webhostapp.com/PHP/fetch_latlagrescue.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("ApplySharedPref")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("product");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonobject = jsonArray.getJSONObject(i);
                                textname=(TextView)findViewById(R.id.txtname);

                                //String firstName = employee.getString("name");
                                String txtname = jsonobject.getString("name");
                                String txtlati = jsonobject.getString("lati");
                                String txtlang = jsonobject.getString("lang");

                                textname.setText(txtname);
                                textlang.setText(txtlang);
                                textlat.setText(txtlati);
                                SharedPreferences myPrefs = getApplication().getSharedPreferences("contact", 0);
                                SharedPreferences.Editor prefsEditor = myPrefs.edit();
                                prefsEditor.putString("lati",txtlati);
                                prefsEditor.putString("logi",txtlang);
                                prefsEditor.putString("wname",txtname);
                                prefsEditor.commit();

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
