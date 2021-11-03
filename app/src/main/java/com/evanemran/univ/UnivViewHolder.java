package com.evanemran.univ;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UnivViewHolder extends RecyclerView.ViewHolder {
    TextView textView_name, textView_country, textView_province;
    Button button_web;
    public UnivViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_name = itemView.findViewById(R.id.textView_name);
        textView_country = itemView.findViewById(R.id.textView_country);
        textView_province = itemView.findViewById(R.id.textView_province);
        button_web = itemView.findViewById(R.id.button_web);
    }
}
