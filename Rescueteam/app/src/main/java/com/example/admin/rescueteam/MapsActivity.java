package com.example.admin.rescueteam;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Double li,la;
        Context context;
        SharedPreferences myPrefs = getApplication().getSharedPreferences("contact", 0);
        String lii=myPrefs.getString("lati","0");
        String loi=myPrefs.getString("logi","0");
        String n1=myPrefs.getString("wname","0");
        li =Double.parseDouble(lii);
        la=Double.parseDouble(loi);






        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(li,la);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker of"+n1));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
