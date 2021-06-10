package com.example.android.medicamet;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Objects;

public class medLayout extends ArrayAdapter<medicine> {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String userId;

    public medLayout(@NonNull Activity context, @NonNull List<medicine> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        medicine m = getItem(position);
        TextView drug = (TextView) listItemView.findViewById(R.id.textView12);
        drug.setText(m.getDrug());
        TextView quantity = (TextView) listItemView.findViewById(R.id.textView14);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        DocumentReference ref = firebaseFirestore.collection("users").document(userId);
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.contains(m.getDrug())){
                    quantity.setText(documentSnapshot.getString(m.getDrug()));
                }
                else{
                    quantity.setText(m.getQuantity());
                }
            }
        });
        return listItemView;

    }
}
