package com.amravati.amravatifigtscovid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
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
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private  listitem adapter;
    TextView tconfirmed,trecovered,tdeceased,update;
    static  int tconfirmedint=0,trecoveredint=0,tdeceasedint=0;
    private DatabaseReference scoresRef;
    LottieAnimationView link;
    Vibrator v1;
    ImageView langchange;
    public static final int[] PIECHART_COLORS = {
            rgb("#ffaaa5"), rgb("#363636"), rgb("#a8e6cf"), rgb("#3498db")
    };
    private loding lodingDiolag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialization of variable
        init();

        lodingDiolag=new loding(MainActivity.this);
        lodingDiolag.StartLoding();


        langchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v1.vibrate(100);
                SharedPreferences sharedPreferences = getSharedPreferences("com.amravati.amravatifigtscovid19", MODE_PRIVATE);
                SharedPreferences.Editor ediot = sharedPreferences.edit();
                ediot.putString("lang","M");
                ediot.apply();
                Intent changlang =new Intent(MainActivity.this,splashScreen.class);
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
        //set pie chart

        //post for firebase
        scoresRef = FirebaseDatabase.getInstance().getReference();
        scoresRef.keepSynced(true);

        FirebaseRecyclerOptions<modle> options =
                new FirebaseRecyclerOptions.Builder<modle>()
                        .setQuery(scoresRef.child("city"), modle.class)
                        .build();

        adapter=new listitem(options,MainActivity.this,"E");
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        setTotalCases();
        setupdate();

    }

    private void Setpiechart() {
        PieChart pieChart=findViewById(R.id.piechart);
        //pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(14f);

        List<PieEntry> value=new ArrayList<>();
        value.add(new PieEntry(tconfirmedint,"Active Cases"));
        value.add(new PieEntry(tdeceasedint,"Deceased"));
        value.add(new PieEntry(trecoveredint,"Recovered"));

        PieDataSet pieDataSet=new PieDataSet(value, "COVID-19 graph");

        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieDataSet.setColors(PIECHART_COLORS);
        pieChart.animateXY(1400,1400);
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        //Setpiechart();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void setupdate() {
        scoresRef.child("update").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                update.setText(String.valueOf(dataSnapshot.child("update").getValue(String.class)));
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

                tconfirmed.setText(String.valueOf(dataSnapshot.child("tconfirmed").getValue()));
                trecovered.setText(String.valueOf(dataSnapshot.child("trecovered").getValue()));
                tdeceased.setText(String.valueOf(dataSnapshot.child("tdeceased").getValue()));
                Setpiechart();
                lodingDiolag.stopLoding();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Intent starterror=new Intent(MainActivity.this,setting.class);
                startActivity(starterror);
            }
        });
    }

    private void init() {
        langchange=findViewById(R.id.langchange);
        tconfirmed=findViewById(R.id.Tconfirmedno);
        trecovered=findViewById(R.id.Trecoveredno);
        tdeceased=findViewById(R.id.Tdeceasedno);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        link=findViewById(R.id.link);
        update=findViewById(R.id.update);
        v1= (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }
}