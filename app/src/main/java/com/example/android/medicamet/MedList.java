package com.example.android.medicamet;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class MedList extends AppCompatActivity {

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String med,result="";
    Button locate,search;
    int i = 1;
    ArrayList<String> ids = new ArrayList<>();
    ArrayList<Double> dist = new ArrayList<>();
    int r = 1;
    int rid;
    double min1,min2,min3;
    double fmin1,fmin2,fmin3;
    String uLatitude, uLongitude;
    TextView show,searchData,showLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_list);
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        show = (TextView) findViewById(R.id.textView22);
        search = (Button) findViewById(R.id.button6);
        searchData = (TextView) findViewById(R.id.textView23);
        searchData.setVisibility(View.GONE);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        med = getIntent().getExtras().getString("medicine");
        show.setVisibility(View.VISIBLE);
        search.setVisibility(View.VISIBLE);
        uLatitude = getIntent().getExtras().getString("latitude");
        uLongitude = getIntent().getExtras().getString("longitude");
        DocumentReference dref = firebaseFirestore.collection("userid").document("geitUMSzNmzPDU1k90Lg");

//        locate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (ContextCompat.checkSelfPermission(
//                        getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(
//                            MedList.this,
//                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                            REQUEST_CODE_LOCATION_PERMISSION
//                    );
//                } else {
//                    getCurrentLocation();
//                }
//            }
//
//        });


//        search.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onClick(View view) {
////                DocumentReference dref = firebaseFirestore.collection("userid").document("geitUMSzNmzPDU1k90Lg");
//                dref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if(documentSnapshot.exists()){
//                            int i=1;
//                            while(documentSnapshot.contains("Hello All")){
//                                if(documentSnapshot.contains(""+i)){
//                                    int j = i;
//                                    DocumentReference dref1 = firebaseFirestore.collection("users").document(Objects.requireNonNull(documentSnapshot.getString("" + i)));
//                                    dref1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                        @Override
//                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                            if(documentSnapshot.contains(""+med)){
//                                                ids.add(j);
////                                                Toast.makeText(MedList.this, ""+j, Toast.LENGTH_SHORT).show();
//                                                double lon1 = Math.toRadians(Double.parseDouble(uLongitude));
//                                                double lon2 = Math.toRadians(Double.parseDouble(Objects.requireNonNull(documentSnapshot.getString("address_longitude"))));
//                                                double lat1 = Math.toRadians(Double.parseDouble(uLatitude));
//                                                double lat2 = Math.toRadians(Double.parseDouble(Objects.requireNonNull(documentSnapshot.getString("address_latitude"))));
//
//                                                double dlon = Math.abs(lon2 - lon1);
//                                                double dlat = Math.abs(lat2 - lat1);
//                                                double a = Math.pow(Math.sin(dlat / 2), 2)
//                                                        + Math.cos(lat1) * Math.cos(lat2)
//                                                        * Math.pow(Math.sin(dlon / 2),2);
//
//                                                double c = 2 * Math.asin(Math.sqrt(a));
//                                                double r = 6371;
//                                                dist.add(c*r);
////                                                Toast.makeText(MedList.this, ""+c*r, Toast.LENGTH_SHORT).show();
////                                                result = result + "\t\t\t\t\t\t\t\t\tShop Name :  ".toUpperCase()+documentSnapshot.getString("user_shop")+
////                                                        "\n\nAddress :  ".toUpperCase()+documentSnapshot.getString("user_address")+"\n\nOwner :  ".toUpperCase()+
////                                                        documentSnapshot.getString("user_name")+"\n\nShop number :  ".toUpperCase()+
////                                                        documentSnapshot.getString("user_phone")+
////                                                        "\n---------------------------------------------------\n\n";
//                                            }
//                                        }
//                                    });
//                                    i++;
//                                }
//                                else{
//                                    break;
//                                }
//                            }
//                            rid = ids.get(dist.indexOf(Collections.min(dist)));
////                            DocumentReference fdref = firebaseFirestore.collection("userid").document("geitUMSzNmzPDU1k90Lg");
//                            dref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                @Override
//                                public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                    String frid = documentSnapshot.getString(""+rid);
//                                    DocumentReference dref1 = firebaseFirestore.collection("users").document(Objects.requireNonNull(documentSnapshot.getString("" + frid)));
//                                    dref1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                        @Override
//                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                            result = result + "\t\t\t\t\t\t\t\t\tShop Name :  ".toUpperCase()+documentSnapshot.getString("user_shop")+
//                                                        "\n\nAddress :  ".toUpperCase()+documentSnapshot.getString("user_address")+"\n\nOwner :  ".toUpperCase()+
//                                                        documentSnapshot.getString("user_name")+"\n\nShop number :  ".toUpperCase()+
//                                                        documentSnapshot.getString("user_phone") ;
////                                                        "+\n---------------------------------------------------\n\n";
//                                        }
//                                    });
//                                }
//                            });
//                            searchData.setVisibility(View.VISIBLE);
//                            searchData.setText(result);
//                            if(result.equals("")){
//                                if(r==1){
//                                    Toast.makeText(MedList.this, "Processing Data", Toast.LENGTH_SHORT).show();
//                                }
//                                r++;
//                                if(r==3 || r==4 ){
//                                    searchData.setText("No Data Available".toUpperCase());
//                                }
//                                if(r==5){
//                                    startActivity(new Intent(getApplicationContext(),MedSearch.class));
//                                    finish();
//                                }
//
//                            }
//                        }
//                    }
//                });
//            }
//        });
//
//    }

//    private static double getDistance(double uslt,double uslo,double lt,double lo) {
//        double lon1 = Math.toRadians(uslo);
//        double lon2 = Math.toRadians(lo);
//        double lat1 = Math.toRadians(uslt);
//        double lat2 = Math.toRadians(lt);
//
//        double dlon = Math.abs(lon2 - lon1);
//        double dlat = Math.abs(lat2 - lat1);
//        double a = Math.pow(Math.sin(dlat / 2), 2)
//                + Math.cos(lat1) * Math.cos(lat2)
//                * Math.pow(Math.sin(dlon / 2),2);
//
//        double c = 2 * Math.asin(Math.sqrt(a));
//        double r = 6371;
//        return (c * r);
//    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                getCurrentLocation();
//            } else {
//                Toast.makeText(this, "PERMISSION DENIED!", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    private void getCurrentLocation() {
//        LocationRequest locationRequest = new LocationRequest();
//        locationRequest.setInterval(10000);
//        locationRequest.setFastestInterval(3000);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        LocationServices.getFusedLocationProviderClient(MedList.this).requestLocationUpdates(locationRequest, new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                super.onLocationResult(locationResult);
//                LocationServices.getFusedLocationProviderClient(MedList.this)
//                        .removeLocationUpdates(this);
//                if(locationResult != null && locationResult.getLocations().size() > 0){
//                    int latestLocationIndex = locationResult.getLocations().size() - 1;
//                    userLatitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
//                    userLongitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
//                    Location location = new Location("providerNA");
//                    Toast.makeText(MedList.this, "Latitude: "+userLatitude+"\nLongitude :"+userLongitude, Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        }, Looper.getMainLooper());
//    }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    public void onSuccess(DocumentSnapshot documentSnapshot1) {
                        while (documentSnapshot1.contains(""+i)){
//                            int j = i;
                            firebaseFirestore.collection("users").document(Objects.requireNonNull(documentSnapshot1.getString("" + i)))
                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.contains("" + med)) {
                                        ids.add(documentSnapshot.getId());
                                        double lon1 = Math.toRadians(Double.parseDouble(uLongitude));
                                        double lon2 = Math.toRadians(Double.parseDouble(Objects.requireNonNull(documentSnapshot.getString("address_longitude"))));
                                        double lat1 = Math.toRadians(Double.parseDouble(uLatitude));
                                        double lat2 = Math.toRadians(Double.parseDouble(Objects.requireNonNull(documentSnapshot.getString("address_latitude"))));

                                        double dlon = Math.abs(lon2 - lon1);
                                        double dlat = Math.abs(lat2 - lat1);

                                        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
                                        double c = 2 * Math.asin(Math.sqrt(a));
                                        double r = 6371;
                                        dist.add(Math.abs(c*r));
                                        result = result + "\t\t\t\t\t\t\t\t\tShop Name :  ".toUpperCase()+documentSnapshot.getString("user_shop")+
                                                "\n\nAddress :  ".toUpperCase()+documentSnapshot.getString("user_address")+"\n\nOwner :  ".toUpperCase()+
                                                documentSnapshot.getString("user_name")+"\n\nShop number :  ".toUpperCase()+
                                                documentSnapshot.getString("user_phone")+" \n---------------------------------------------------\n\n";
//                                                "\nDistance From the Current Position: "+
//                                                Math.abs(c*r)+" Km"+" \n---------------------------------------------------\n\n";

                                    }
                                }
                            });
                            i++;
                        }
                        if(dist.isEmpty()){
                            if(result.contains("The Places Where You Can Get The Medicine")){
                                result = result + "No Data Available".toUpperCase();
                                Toast.makeText(MedList.this, "Sorry, No Data Available.\nSearch for different medicine", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MedSearch.class));
                                finish();
                            }
                            else{
                                result = result + "The Places Where You Can Get The Medicine"+"\n\n";
                            }
                        }
                        else{
                            min1 = dist.get(0);
                            for(i=1;i<dist.size();i++){
                                if(min1>dist.get(i)){
                                    min1=dist.get(i);
                                }
                            }
                            firebaseFirestore.collection("users")
                                    .document("" + ids.get(dist.indexOf(min1)))
                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    result = result +"\n\nThe Best Place Where You can get the Medicine NearBy : \n\n"+ "\t\t\t\t\t\t\t\t\tShop Name :  ".toUpperCase() + documentSnapshot.getString("user_shop") +
                                            "\n\nAddress :  ".toUpperCase() + documentSnapshot.getString("user_address") + "\n\nOwner :  ".toUpperCase() +
                                            documentSnapshot.getString("user_name") + "\n\nShop number :  ".toUpperCase() +
                                            documentSnapshot.getString("user_phone") +
//                                            "\n\nDistance From Your Current Location : "+min1 + " Km"+
                                            "\n\n--------------------------------------------";
                                }
                            });
                        }

                        searchData.setVisibility(View.VISIBLE);
                        searchData.setText(result);
//                        if (result.equals("")) {
//                            if (r == 1) {
//                                Toast.makeText(MedList.this, "Processing Data", Toast.LENGTH_SHORT).show();
//                            }
//                            r++;
//                            if (r == 3 || r == 4 || j==2 ) {
//                                searchData.setText("No Data Available".toUpperCase());
//                            }
//                            if (r == 5) {
//                                startActivity(new Intent(getApplicationContext(), MedSearch.class));
//                                finish();
//                            }
//
//                        }

                    }
                });

            }
        });
    }
}