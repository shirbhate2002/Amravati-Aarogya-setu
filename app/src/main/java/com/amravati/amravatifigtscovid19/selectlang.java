package com.amravati.amravatifigtscovid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class selectlang extends AppCompatActivity {

    Button start;
    CheckBox marathi,english;
    Vibrator v1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectlang);

        init();

        SharedPreferences sharedPreferences = getSharedPreferences("com.amravati.amravatifigtscovid19", MODE_PRIVATE);
        //
        if (!sharedPreferences.getBoolean("skilang", false)) {
            if(isNetworkAvailable())
            {

                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (marathi.isChecked() && !english.isChecked()) {
                            Intent startmarathi = new Intent(selectlang.this, MarathiMainActivity.class);
                            addskip("M");
                            startActivity(startmarathi);
                            finish();
                        } else if (!marathi.isChecked() && english.isChecked()) {
                            Intent startenglish = new Intent(selectlang.this, MainActivity.class);
                            startActivity(startenglish);
                            addskip("E");
                            finish();
                        } else {
                            Toast.makeText(selectlang.this, "Please select one language", Toast.LENGTH_SHORT).show();
                            v1.vibrate(200);
                            marathi.setChecked(false);
                            english.setChecked(false);
                        }
                    }
                });

            }
            else {
                Intent starterror=new Intent(selectlang.this,setting.class);
                startActivity(starterror);
                finish();
            }
        }
        else
            {
            if (sharedPreferences.getString("lang",null).equals("M"))
            {
                Intent startmarathi = new Intent(selectlang.this, MarathiMainActivity.class);
                startActivity(startmarathi);
                finish();
            }
            else if (sharedPreferences.getString("lang",null).equals("E"))
            {
                Intent startenglish = new Intent(selectlang.this, MainActivity.class);
                startActivity(startenglish);
                finish();
            }
        }
    }
    private void addskip(String lan) {
        SharedPreferences sharedPreferences=getSharedPreferences("com.amravati.amravatifigtscovid19",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("skilang",true);
        editor.putString("lang",lan);
        editor.apply();
    }

    private void init() {
        start=findViewById(R.id.start);
        marathi=findViewById(R.id.marathi);
        english=findViewById(R.id.English);
        v1= (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
