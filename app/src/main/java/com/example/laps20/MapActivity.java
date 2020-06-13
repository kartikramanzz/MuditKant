package com.example.laps20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MapActivity extends AppCompatActivity {
    Button logout;
    TextView tv;
    Button loc;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    DatabaseReference db;
    private static final int REQUEST_CODE_LOCATION_PERMISSION=1;
    String id;
    String s;
    String a;
    double lat;
    double longi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        logout=findViewById(R.id.logoutbttn);
        loc=findViewById(R.id.dbss);
        tv=findViewById(R.id.tv);
        db= FirebaseDatabase.getInstance().getReference("User Info");
        id="Kartik";
        //taking permission module
        if (ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapActivity.this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },REQUEST_CODE_LOCATION_PERMISSION);
        }
        //permission module ended

        // getting the devices latlong
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();
            }
        });

        //latlong module ended
        //DataBase referencing

        //Database Button Working

        //logout Working
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i=new Intent(MapActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }
    //
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE_LOCATION_PERMISSION && grantResults.length>0)
        {
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                getCurrentLocation();
            }
            else
            {
                Toast.makeText(MapActivity.this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getCurrentLocation()
    {

        final LocationRequest locationRequest=new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(30000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.getFusedLocationProviderClient(MapActivity.this).requestLocationUpdates(locationRequest,new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                LocationServices.getFusedLocationProviderClient(MapActivity.this).removeLocationUpdates(this);
                if(locationResult !=null && locationResult.getLocations().size()>0)
                {
                    int latestLocationIndex= locationResult.getLocations().size()-1;
                    lat=locationResult.getLocations().get(latestLocationIndex).getLatitude();
                    longi=locationResult.getLocations().get(latestLocationIndex).getLongitude();

                    s = Double.toString(lat);
                    a=Double.toString(longi);
                    Info n=new Info(s,a);
                    db.child(id).setValue(n);
                    tv.setText(s+" "+a);
                    Intent z=new Intent(MapActivity.this,Map2Activity.class);
                    startActivity(z);
                }
            }
        }, Looper.getMainLooper());
    }

}
