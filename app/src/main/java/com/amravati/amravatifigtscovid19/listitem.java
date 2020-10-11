package com.amravati.amravatifigtscovid19;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import static android.content.Context.MODE_PRIVATE;

public class listitem extends FirebaseRecyclerAdapter<modle, listitem.listitemholder> {

    public Context context;
    public  String lang;

    public listitem(@NonNull FirebaseRecyclerOptions<modle> options,Context context,String lang) {
        super(options);
        this.context=context;
        this.lang=lang;
    }

    @Override
    protected void onBindViewHolder(@NonNull listitemholder holder, int position, @NonNull modle model) {
        holder.location.setText((lang.trim()=="E")?model.getLocation():model.getLocationm());
        holder.confirmed.setText(String.valueOf(model.getConfirmed()));
        holder.recovered.setText(String.valueOf(model.getRecovered()));
        holder.deceased.setText(String.valueOf(model.getDeceased()));
    }

    @NonNull
    @Override
    public listitemholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.citylistiem, parent, false);
        return new listitemholder(view);
    }

    class listitemholder extends RecyclerView.ViewHolder{
        TextView location,confirmed,recovered,deceased;
        public listitemholder(@NonNull View itemView) {
            super(itemView);
            location=itemView.findViewById(R.id.location);
            confirmed=itemView.findViewById(R.id.confirmedno);
            recovered=itemView.findViewById(R.id.recoceredno);
            deceased=itemView.findViewById(R.id.deceasedno);
        }
    }
}
