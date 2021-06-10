package com.example.android.medicamet;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class  Inventory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        final ArrayList<medicine> med =new ArrayList<>();
        med.add(new medicine("Paracetamol","0"));
        med.add(new medicine("wincold","0"));
        med.add(new medicine("Dexamethasone","0"));
        med.add(new medicine("Diclowin Plus","0"));
        med.add(new medicine("Disprin","0"));
        med.add(new medicine("Acilok","0"));
        med.add(new medicine("Omeprazole","0"));
        med.add(new medicine("Rabiprazole","0"));
        med.add(new medicine("Brufen","0"));
        med.add(new medicine("Azithromycin","0"));
        med.add(new medicine("Betamethasone","0"));
        med.add(new medicine("Ciprobid","0"));
        med.add(new medicine("Cefaxime","0"));
        med.add(new medicine("Amoxicillin","0"));
        med.add(new medicine("Salbutamol","0"));
        med.add(new medicine("Cetrizine","0"));
        med.add(new medicine("Ofloxacin","0"));
        med.add(new medicine("Aspirin","0"));
        med.add(new medicine("Chloramphenicoll","0"));


        final medLayout adapter = new medLayout(this,med);
        final ListView lv = (ListView)findViewById(R.id.rootview);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),DrugDetail.class);
                intent.putExtra("med_name",med.get(i).getDrug());
                intent.putExtra("med_quantity",med.get(i).getQuantity());
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.updat,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.exit){
            Intent intent = new Intent(getApplicationContext(),Profile.class);
            startActivity(intent);
            finish();
        }
        return true;
    }
}