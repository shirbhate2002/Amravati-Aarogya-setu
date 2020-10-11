package com.amravati.amravatifigtscovid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

public class MarathiMainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private  listitem adapter;
    TextView tconfirmed,trecovered,tdeceased,update;
    private DatabaseReference scoresRef;
    LottieAnimationView link;
    static  int tconfirmedint=0,trecoveredint=0,tdeceasedint=0;
    Vibrator v1;
    ImageView langchange;
    public static final int[] PIECHART_COLORS = {
            rgb("#ffaaa5"), rgb("#363636"), rgb("#a8e6cf"), rgb("#3498db")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marathi_main);
        tconfirmed=findViewById(R.id.Tconfirmedno);
        trecovered=findViewById(R.id.Trecoveredno);
        tdeceased=findViewById(R.id.Tdeceasedno);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(MarathiMainActivity.this));
        link=findViewById(R.id.link);
        update=findViewById(R.id.update);
        langchange=findViewById(R.id.langchangem);
        v1= (Vibrator) getSystemService(VIBRATOR_SERVICE);



        langchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v1.vibrate(100);
                SharedPreferences sharedPreferences = getSharedPreferences("com.amravati.amravatifigtscovid19", MODE_PRIVATE);
                SharedPreferences.Editor ediot = sharedPreferences.edit();
                ediot.putString("lang","E");
                ediot.apply();
                Intent changlang =new Intent(MarathiMainActivity.this,splashScreen.class);
                startActivity(changlang);
                finish();
            }
        });


        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoapstor=new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=nic.goi.aarogyasetu"));
                v1.vibrate(200);
                startActivity(gotoapstor);

            }
        });

        scoresRef = FirebaseDatabase.getInstance().getReference();
        scoresRef.keepSynced(true);

        FirebaseRecyclerOptions<modle> options =
                new FirebaseRecyclerOptions.Builder<modle>()
                        .setQuery(scoresRef.child("city"), modle.class)
                        .build();

        adapter=new listitem(options,MarathiMainActivity.this,"M");
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        setTotalCases();
        setupdate();

    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    private void Setpiechart() {

        PieChart pieChart=findViewById(R.id.piechart);
        //pieChart.setUsePercentValues(true);

        List<PieEntry> value=new ArrayList<>();
        value.add(new PieEntry(tconfirmedint,"उपचार सुरू"));
        value.add(new PieEntry(tdeceasedint,"मृत्यू"));
        value.add(new PieEntry(trecoveredint,"कोरोनामुक्त"));

        PieDataSet pieDataSet=new PieDataSet(value, "COVID-19 graph");

        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieDataSet.setColors(PIECHART_COLORS);
        pieChart.animateXY(1400,1400);
    }

    private void setupdate() {
            scoresRef.child("update").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    update.setText(String.valueOf(dataSnapshot.child("update").getValue()));
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }

    private void setTotalCases() {
        scoresRef.child("case").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tconfirmedint=Integer.valueOf(dataSnapshot.child("tconfirmed").getValue(Integer.class));
                tdeceasedint=Integer.valueOf(dataSnapshot.child("tdeceased").getValue(Integer.class));
                trecoveredint=Integer.valueOf(dataSnapshot.child("trecovered").getValue(Integer.class));
                tconfirmedint-=(tdeceasedint + trecoveredint);

                //Toast.makeText(MarathiMainActivity.this,tconfirmedint, Toast.LENGTH_SHORT).show();

                Log.i("treat", String.valueOf(tconfirmedint));
                Log.i("death", String.valueOf(tdeceasedint));
                Log.i("recovered",String.valueOf(trecoveredint));

                tconfirmed.setText(String.valueOf(dataSnapshot.child("tconfirmed").getValue()));
                trecovered.setText(String.valueOf(dataSnapshot.child("trecovered").getValue()));
                tdeceased.setText(String.valueOf(dataSnapshot.child("tdeceased").getValue()));

                Setpiechart();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Setpiechart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
