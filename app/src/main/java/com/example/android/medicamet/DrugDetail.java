package com.example.android.medicamet;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DrugDetail extends AppCompatActivity {
    TextView drug,quantity;
    EditText newQuantity;
    Button save,edit;
    FirebaseAuth fAuth;
    FirebaseFirestore rootRef;
    String userId;
    String amount;
    int a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_detail);
        fAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseFirestore.getInstance();
        userId = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        drug = (TextView)findViewById(R.id.textView17);
        quantity = (TextView) findViewById(R.id.textView16);
        save=(Button)findViewById(R.id.button7);
        edit = (Button)findViewById(R.id.button4);
        newQuantity = (EditText)findViewById(R.id.editTextNumber2);
        drug.setText(getIntent().getExtras().getString("med_name"));

        DocumentReference ref = rootRef.collection("users").document(userId);
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.contains(drug.getText().toString())){
                    quantity.setText(documentSnapshot.getString(drug.getText().toString()));
                }
                else{
                    quantity.setText(getIntent().getExtras().getString("med_quantity"));
                }
            }
        });

        newQuantity.setVisibility(View.GONE);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity.setVisibility(View.GONE);
                edit.setVisibility(View.GONE);
                newQuantity.setVisibility(View.VISIBLE);
                Toast.makeText(DrugDetail.this, "Please Enter Quantity Of Medicine To Add", Toast.LENGTH_SHORT).show();
                Toast.makeText(DrugDetail.this, "IF You Want to Reduce then add 0 at start and the amount of quantity to reduce ", Toast.LENGTH_LONG).show();
            }
        });



        save.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view)  {
                if(TextUtils.isEmpty(newQuantity.getText().toString())){
                    newQuantity.setError("Please Enter The Proper Amount: ");
                    Toast.makeText(DrugDetail.this, "Please Enter Some Amount", Toast.LENGTH_SHORT).show();
                    return;
                }
                userId = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                DocumentReference ref = rootRef.collection("users").document(userId);
                if(quantity.getText().toString().equals("0")){
                    if(newQuantity.getText().toString().equals("0")){
                        Toast.makeText(DrugDetail.this,
                                "Your Inventory Does Not Have "+drug.getText().toString()+" medicine", Toast.LENGTH_SHORT).show();
                    }else{
                        Map<String, Object> map = new HashMap<>();
                        map.put(drug.getText().toString(),newQuantity.getText().toString());
                        ref.set(map, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                    }
                }
                else{
                    if(newQuantity.getText().toString().equals("0")){
                        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Map<String, Object> updates = new HashMap<>();
                                updates.put(drug.getText().toString(), FieldValue.delete());
                                ref.update(updates);
                            }
                        });
                    }else{
                        Map<String, Object> map = new HashMap<>();
                        int final_quantity;
                        if(newQuantity.getText().toString().charAt(0) == '0'){
                            final_quantity = Integer.parseInt(quantity.getText().toString())-Integer.parseInt(newQuantity.getText().toString());
                        }
                        else {
                            final_quantity = Integer.parseInt(quantity.getText().toString())+Integer.parseInt(newQuantity.getText().toString());
                        }
                        map.put(drug.getText().toString(),""+final_quantity);
                        ref.set(map, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                    }

                }
//                if(amount.equals("0") || amount.trim().length() == 0){
//                    newQuantity.setText("0");
//                }
//                if(newQuantity.getText().toString().equals("0")){
//                    userId = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
//                    DocumentReference ref = rootRef.collection("users").document(userId);
//                    ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                        @Override
//                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//                            if(documentSnapshot.contains(drug.getText().toString())){
//                                Map<String, Object> updates = new HashMap<>();
//                                updates.put(drug.getText().toString(), FieldValue.delete());
//                                ref.update(updates);
//                            }
//                            else{
//                                Toast.makeText(DrugDetail.this, "Your Inventory Does Not Have "+drug.getText().toString()+" medicine", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                }
//                else{
//                    DocumentReference ref = rootRef.collection("users").document(userId);
//                    Map<String, Object> map = new HashMap<>();
//                    map.put(drug.getText().toString(),newQuantity.getText().toString());
//                    ref.set(map, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                        }
//                    });
//                }

//                    DocumentReference ref = rootRef.collection("users").document(userId);
//                    Map<String, Object> map = new HashMap<>();
//                    map.put(drug.getText().toString(),newQuantity.getText().toString());
//                    ref.set(map, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                        }
//                    });




                Intent intent = new Intent(getApplicationContext(),Inventory.class);
                intent.putExtra("new_quantity",quantity.toString());
                startActivity(intent);
                finish();
            }
        });

    }
}