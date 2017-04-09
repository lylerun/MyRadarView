package com.studys.lyle.radarview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity  {

    private RadarView mRadar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRadar = (RadarView)findViewById(R.id.rv_Radar);

    }
    public void startSweep(View v){
        mRadar.startSweep();
    }

    public void stopSweep(View v){
        mRadar.stopSweep();
    }

}
