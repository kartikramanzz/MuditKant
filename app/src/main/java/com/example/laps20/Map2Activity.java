package com.example.laps20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Map2Activity extends AppCompatActivity {
    GoogleMap map;
    DatabaseReference db;
    String a;
    String b;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        tv=findViewById(R.id.tvdb);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
        String x=FirebaseAuth.getInstance().getCurrentUser().getUid();
        db= FirebaseDatabase.getInstance().getReference().child("User Info");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               // a=dataSnapshot.getValue().toString();
//                b=dataSnapshot.child("longi").getValue().toString();
                //tv.setText(a);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Map2Activity.this,"Lookout",Toast.LENGTH_SHORT).show();
            }
        });

//        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        if (mapFragment == null) {
//            throw new AssertionError();
//        }
//        mapFragment.getMapAsync(this);
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        map=googleMap;
//        LatLng me=new LatLng(x[0],x[1]);
//        map.addMarker(new MarkerOptions().position(me).title("You"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(me));
//    }
}
