package com.evanemran.univ;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.Wave;

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
    ProgressBar progressBar;


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

        progressBar = (ProgressBar)findViewById(R.id.loader);
        Sprite doubleBounce = new Wave();
        progressBar.setIndeterminateDrawable(doubleBounce);

        manager.getUniv(listener, "", "");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = editText_name.getText().toString().trim();
                country = editText_country.getText().toString().trim();
                if (name.equals("") && country.equals("")){
                    Toast.makeText(MainActivity.this, "Enter University name or Country name.", Toast.LENGTH_LONG).show();
                }else{
                    recyclerView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    if (name.equals("")){
                        manager.getUnivByCountry(listener, country);
                    }
                    else if (country.equals("")){
                        manager.getUnivByName(listener, name);
                    }
                    else{
                        manager.getUniv(listener, name, country);
                    }
                }

            }
        });
    }

    private final OnFetchDataListener listener = new OnFetchDataListener() {
        @Override
        public void onResponse(List<APIResponse> responses, String message) {
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            if (responses==null || responses.isEmpty()){
//                Toast.makeText(MainActivity.this, "No Data Found!", Toast.LENGTH_SHORT).show();
                final Dialog alertDialog = new Dialog(MainActivity.this);
                alertDialog.setContentView(R.layout.custom_alert_dialog);
                alertDialog.setTitle("Oops!");
                Button ok_button= (Button) alertDialog.findViewById(R.id.button_alert);
                alertDialog.show();
                ok_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                Window window = alertDialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                return;
            }
            showResult(responses);
        }

        @Override
        public void onError(String message) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private void showResult(List<APIResponse> responses) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UnivAdapter(this, responses, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
    }


    @Override
    public void OnClicked(String web) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(web));
        startActivity(browserIntent);
    }
}