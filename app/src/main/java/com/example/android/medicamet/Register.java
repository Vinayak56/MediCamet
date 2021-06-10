package com.example.android.medicamet;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private static final String TAG = "Tag";
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    EditText FullName, Email, Password, Phone, shopName,address;
    Button getLocation, register;
    TextView login;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressBar progressBar;
    String userID,livelocation;
    double userLatitude, userLongitude;
    private ResultReceiver resultReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FullName = (EditText) findViewById(R.id.editTextTextPersonName);
        Email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        Password = (EditText) findViewById(R.id.editTextTextPassword);
        Phone = (EditText) findViewById(R.id.editTextPhone);
        shopName = (EditText)findViewById(R.id.editTextTextPersonName2);
        address = (EditText)findViewById(R.id.editTextTextPersonName4);
        getLocation = (Button) findViewById(R.id.location);
        register = (Button) findViewById(R.id.button);
        login = (TextView) findViewById(R.id.textView5);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        resultReceiver = new AddressResultReceiver(new Handler());

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Profile.class));
            finish();
        }

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            Register.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_CODE_LOCATION_PERMISSION

                    );
                } else {
                    getCurrentLocation();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();
                final String fullName = FullName.getText().toString();
                final String phone = Phone.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Email.setError(" Email is Required. ");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Password.setError(" Password is Required. ");
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    Phone.setError(" Email is Required. ");
                    return;
                }

                if (password.length() < 6) {
                    Password.setError(" Password must be Strong and more than 6 characters. ");
                    return;
                }

//                if (phone.length() != 10) {
//                    Phone.setError(" Phone Number must be valid and of 10 characters. ");
//                    return;
//                }

                progressBar.setVisibility(View.VISIBLE);

                //register the user in firebase

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {    //task = process of registering user
                        if (task.isSuccessful()) {
//
//                            // send verification email
//                            FirebaseUser fuser = firebaseAuth.getCurrentUser();
//                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Toast.makeText(Register.this, "Verification Email Has been sent.", Toast.LENGTH_SHORT).show();
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.d(TAG, "onFailure: Email not sent" + e.getMessage());
//                                }
//                            });

                            Toast.makeText(Register.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                            userID = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
                            DocumentReference documentReference1 = firebaseFirestore.collection("userid").document("geitUMSzNmzPDU1k90Lg");
                            String user_name = FullName.getText().toString();
                            String user_email = Email.getText().toString();
                            String user_phone = Phone.getText().toString();
                            String user_shopname = shopName.getText().toString();
                            String user_address = address.getText().toString();
                            String address_latitude = Double.toString(userLatitude);
                            String address_longitude = Double.toString(userLongitude);

                            Map<String, Object> user = new HashMap<>();      //for input one user details in database users table
                            user.put("user_name", user_name);
                            user.put("user_email", user_email);
                            user.put("user_phone", user_phone);
                            user.put("user_shop",user_shopname);
                            user.put("user_address", user_address);
                            user.put("address_latitude", address_latitude);
                            user.put("address_longitude", address_longitude);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for " + userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });

                            ArrayList<String> euserids = new ArrayList<>();
                            documentReference1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.exists()){
                                        int i =1;
                                        while(documentSnapshot.contains("Hello All")){
                                            if(documentSnapshot.contains(""+i)){
                                                euserids.add(documentSnapshot.getString(""+i));
                                                i++;
                                            }
                                            else {
                                                Map<String, Object> userids = new HashMap<>();
                                                for(int j=0;j<i-1;j++){
                                                    userids.put((j+1)+"",euserids.get(j));
                                                }
                                                userids.put(""+i,userID);
                                                userids.put("Hello All","Welcome");
                                                documentReference1.set(userids).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(Register.this, "User id added", Toast.LENGTH_SHORT).show();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(Register.this, "User id not added: "+e, Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                break;
                                            }
                                        }
                                    }
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), Profile.class));
                            finish();
                        } else {
                            Toast.makeText(Register.this, "Error !! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "PERMISSION DENIED!", Toast.LENGTH_SHORT).show();
            }
        }
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
        LocationServices.getFusedLocationProviderClient(Register.this).requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                LocationServices.getFusedLocationProviderClient(Register.this)
                        .removeLocationUpdates(this);
                if(locationResult != null && locationResult.getLocations().size() > 0){
                    int latestLocationIndex = locationResult.getLocations().size() - 1;
                    userLatitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                    userLongitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                    Location location = new Location("providerNA");
                    location.setLatitude(userLatitude);
                    location.setLongitude(userLongitude);
                    fetchAddressFromLatLong(location);
                }
            }
        }, Looper.getMainLooper());
    }

    private void fetchAddressFromLatLong(Location location){
        Intent intent = new Intent(this,FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA,location);
        startService(intent);
    }

    private class AddressResultReceiver extends ResultReceiver{
        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if(resultCode == Constants.SUCCESS_RESULT){
                livelocation = ""+resultData.getString(Constants.RESULT_DATA_KEY);
                String givenAddress = address.getText().toString();
                if(locationVerified(livelocation,givenAddress)){
                    Toast.makeText(Register.this, "The Address is Correct", Toast.LENGTH_SHORT).show();
                }
                else{
                    address.setError("Please Enter Correct Address of shop");
                }
            }
            else {
                Toast.makeText(Register.this, resultData.getString(Constants.RESULT_DATA_KEY), Toast.LENGTH_SHORT).show();
            }

        }
    }

    private boolean locationVerified(String livelocation, String givenAddress) {
        int countystartindex = livelocation.lastIndexOf(' ');
        String country = livelocation.substring(countystartindex+1,livelocation.length()).trim();
        String pincode = livelocation.substring(countystartindex-7,countystartindex-1);
        if(givenAddress.contains(country) && givenAddress.contains(pincode)){
            return true;
        }
        return false;
    }
}