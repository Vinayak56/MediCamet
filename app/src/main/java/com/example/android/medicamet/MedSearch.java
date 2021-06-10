package com.example.android.medicamet;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MedSearch extends AppCompatActivity {

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    SearchView search;
    ListView myList;
    TextView medtext,locationtext;
    Button done;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    double userLatitude, userLongitude;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_search);
        locationtext = (TextView)findViewById(R.id.textView3);
        done = (Button) findViewById(R.id.button5);
        medtext = (TextView)findViewById(R.id.textView19);
        search = (SearchView)findViewById(R.id.searchbar);
        myList = (ListView)findViewById(R.id.MyList);
        medtext.setVisibility(View.GONE);
        search.setVisibility(View.GONE);
        myList.setVisibility(View.GONE);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            MedSearch.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_CODE_LOCATION_PERMISSION
                    );
                } else {
                    getCurrentLocation();
                    locationtext.setVisibility(View.GONE);
                    done.setVisibility(View.GONE);
                    medtext.setVisibility(View.VISIBLE);
                    search.setVisibility(View.VISIBLE);
                    myList.setVisibility(View.VISIBLE);
                }
            }

        });

        list = new ArrayList<String>();
        list.add("Paracetamol");
        list.add("wincold");
        list.add("Dexamethasone");
        list.add("Diclowin Plus");
        list.add("Disprin");
        list.add("Acilok");
        list.add("Omeprazole");
        list.add("Rabiprazole");
        list.add("Brufen");
        list.add("Azithromycin");
        list.add("Betamethasone");
        list.add("Ciprobid");
        list.add("Cefaxime");
        list.add("Amoxicillin");
        list.add("Salbutamol");
        list.add("Cetrizine");
        list.add("Ofloxacin");
        list.add("Aspirin");
        list.add("Chloramphenicoll");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,list);
        myList.setAdapter(adapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                search.setQuery(adapterView.getItemAtPosition(i).toString(),true);
            }
        });
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                MedSearch.this.adapter.getFilter().filter(s);
                Toast.makeText(MedSearch.this, "Medicine : "+s, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),MedList.class);
                i.putExtra("medicine",s);
                i.putExtra("latitude",""+userLatitude);
                i.putExtra("longitude",""+userLongitude);
                startActivity(i);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                MedSearch.this.adapter.getFilter().filter(s);
                return false;
            }
        });


    }

    private void getCurrentLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(MedSearch.this).requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                LocationServices.getFusedLocationProviderClient(MedSearch.this)
                        .removeLocationUpdates(this);
                if(locationResult != null && locationResult.getLocations().size() > 0){
                    int latestLocationIndex = locationResult.getLocations().size() - 1;
                    userLatitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                    userLongitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                    Location location = new Location("providerNA");
                    Toast.makeText(MedSearch.this, "Latitude: "+userLatitude+"\nLongitude :"+userLongitude, Toast.LENGTH_SHORT).show();

                }
            }
        }, Looper.getMainLooper());
    }
}