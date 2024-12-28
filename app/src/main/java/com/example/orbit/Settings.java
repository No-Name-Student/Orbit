package com.example.orbit;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
    public void onClick(View v)
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        String Start = ((Spinner)findViewById(R.id.Start)).getSelectedItem().toString();
        double Mass = Double.parseDouble(((TextView)findViewById(R.id.Mass)).getText().toString());
        double Distance= Double.parseDouble(((TextView)findViewById(R.id.Distance)).getText().toString());
        double SpeedX= Double.parseDouble(((TextView)findViewById(R.id.SpeedX)).getText().toString());
        double SpeedY= Double.parseDouble(((TextView)findViewById(R.id.SpeedY)).getText().toString());
        setContentView(new SceneView(this,Start,Distance,Mass*10E10,SpeedX,SpeedY,width,height));
        Runnable r =new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.activity_settings);
            }
        };
        Handler h = new Handler();
        h.postDelayed(r,1000*60);
    }
}