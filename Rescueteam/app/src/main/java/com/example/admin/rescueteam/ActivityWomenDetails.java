package com.example.admin.rescueteam;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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
 * Created by lenovo on 10-Mar-18.
 */

public class ActivityWomenDetails extends AppCompatActivity {
    String number;
    String JSON_STRING;
    private RequestQueue mQueue;
        TextView name, address, city, state, contact, telephone_no, guardian_name, guardian_no;
    private ProgressDialog pd;
    private static final String URL_PRODUCTS = "https://womensafty.000webhostapp.com/PHP/fetch_women.php";
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.women_details);
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
        String url = "https://womensafty.000webhostapp.com/PHP/fetch_women.php";
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
                              //  contact = (TextView) findViewById(R.id.txtmobile);
                                //guardian_name = (TextView) findViewById(R.id.txtguardian_name);
                                //guardian_no = (TextView) findViewById(R.id.txtguardian_no);
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
     /*   private void Loaddata() throws IOException, JSONException {


      String link = "https://womensafty.000webhostapp.com/PHP/fetch_women.php";
       URL url = new URL(link);
       HttpURLConnection con = (HttpURLConnection) url.openConnection();

      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
       String result = bufferedReader.readLine();
                                name = (TextView) findViewById(R.id.txtname);
                                address = (TextView) findViewById(R.id.txtAddress);
                                city = (TextView) findViewById(R.id.txtcity);
                                state = (TextView) findViewById(R.id.txtstate);
                                contact = (TextView) findViewById(R.id.txtmobile);
                                guardian_name = (TextView) findViewById(R.id.txtguardian_name);
                                guardian_no = (TextView) findViewById(R.id.txtguardian_no);
       JSONObject jsonobject = new JSONObject(result);
                                String id = jsonobject.getString("wid");
                                String txtname = jsonobject.getString("name");
                                String txtaddress = jsonobject.getString("address");
                                String txtcity = jsonobject.getString("city");
                                String txtmobile = jsonobject.getString("contact");
                                String txtstate = jsonobject.getString("state");
                                String txtguardian_name = jsonobject.getString("guardian_name");
                                String txtguardian_number = jsonobject.getString("guardian_number");
                                name.setText("hello");
                                address.setText(txtaddress);
                                city.setText(txtcity);
                                state.setText(txtstate);
                                contact.setText(txtmobile);
                                guardian_name.setText(txtguardian_name);
                                guardian_no.setText(txtguardian_number);
                            }*/

}





