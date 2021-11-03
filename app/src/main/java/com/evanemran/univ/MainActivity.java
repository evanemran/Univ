package com.evanemran.univ;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements WebClickListener{

    RecyclerView recyclerView;
    UnivAdapter adapter;
    ImageButton button;
    EditText editText_name, editText_country;
    String name = "";
    String country = "";
    RequestManager manager;
    ProgressDialog dialog;
    CardView recycler_holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_list);
        editText_name = findViewById(R.id.editText_name);
        editText_country = findViewById(R.id.editText_country);
        button = findViewById(R.id.button_search);
        recycler_holder = findViewById(R.id.recycler_holder);
        dialog = new ProgressDialog(this);
        manager = new RequestManager(MainActivity.this);

        dialog.setTitle("Connecting...");
        dialog.show();
        manager.getUniv(listener, "", "");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = editText_name.getText().toString();
                country = editText_country.getText().toString();
                if (name.equals("") && country.equals("")){
                    Toast.makeText(MainActivity.this, "Enter University name or country name.", Toast.LENGTH_SHORT).show();
                }else{
                    dialog.setTitle("Fetching Universities...");
                    dialog.show();
                    manager.getUniv(listener, name, country);
                }

            }
        });
    }

    private final OnFetchDataListener listener = new OnFetchDataListener() {
        @Override
        public void onResponse(List<APIResponse> responses, String message) {

            dialog.dismiss();
            if (responses==null){
                Toast.makeText(MainActivity.this, "No Data Found!", Toast.LENGTH_SHORT).show();
                return;
            }
            showResult(responses);
        }

        @Override
        public void onError(String message) {
            dialog.dismiss();
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private void showResult(List<APIResponse> responses) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UnivAdapter(this, responses, this);
        recyclerView.setAdapter(adapter);
        recycler_holder.setVisibility(View.VISIBLE);
    }


    @Override
    public void OnClicked(String web) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(web));
        startActivity(browserIntent);
    }
}