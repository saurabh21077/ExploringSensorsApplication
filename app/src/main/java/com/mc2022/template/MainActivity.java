package com.mc2022.template;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String  TAG = "MainActivity";

    TextView light_tv;
    ToggleButton light_tb;

    private SensorManager sensorManager;
    private Sensor lightSensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        light_tv = findViewById(R.id.textView4);
        light_tb = findViewById(R.id.toggleButton4);



        light_tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                {
                    lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                    if (lightSensor != null) {

                        sensorManager.registerListener(MainActivity.this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
                        Toast.makeText(MainActivity.this, " Light sensor started", Toast.LENGTH_SHORT).show();

                        lightSensor.
                    }
                    else{
                        Log.i(TAG,"Light Not supported");
                    }
                }
                else{
                    sensorManager.unregisterListener(MainActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
                    //light_tv.setText("0");
                    Toast.makeText(MainActivity.this, " Light sensor stopped", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        if (sensor.getType() == Sensor.TYPE_LIGHT) {
            Log.d(TAG, "Light:" + sensorEvent.values[0]);
            if(sensorEvent.values[0] < 10000.0){

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}