package com.evanemran.univ;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UnivAdapter extends RecyclerView.Adapter<UnivViewHolder> {
    Context context;
    List<APIResponse> list;
    WebClickListener listener;

    public UnivAdapter(Context context, List<APIResponse> list, WebClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UnivViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UnivViewHolder(LayoutInflater.from(context).inflate(R.layout.list_univ, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull UnivViewHolder holder, int position) {
        holder.textView_name.setText(list.get(position).getName());
        holder.textView_country.setText(list.get(position).getCountry()+" - "+list.get(position).getAlpha_two_code());
        holder.textView_province.setText(list.get(position).getWeb_pages().get(0));

        holder.button_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnClicked(list.get(position).getWeb_pages().get(0));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
