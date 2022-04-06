package com.mc2022.template;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Application;
import android.app.UiModeManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.mc2022.template.DAO.AmbientTemperatureDatabase;
import com.mc2022.template.DAO.GyroscopeDatabase;
import com.mc2022.template.DAO.LightDatabase;
import com.mc2022.template.DAO.LinearAccelerationDatabase;
import com.mc2022.template.DAO.UserLocationDatabase;
import com.mc2022.template.DAO.MagneticFieldDatabase;
import com.mc2022.template.DAO.ProximityDatabase;
import com.mc2022.template.model.AmbientTemperature;
import com.mc2022.template.model.Gyroscope;
import com.mc2022.template.model.Light;
import com.mc2022.template.model.LinearAcceleration;
import com.mc2022.template.model.MagneticField;
import com.mc2022.template.model.Proximity;
import com.mc2022.template.model.UserLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String  TAG = "MainActivity";

    TextView light_tv, light_values_tv,
            gyro_tv, gyro_values_tv,
            linear_tv, linear_values_tv,
            temp_tv, temp_values_tv,
            prox_tv, prox_values_tv,
            magnet_tv, magnet_values_tv,
            curr_location_lat_tv, curr_location_lat_value_tv,
            curr_location_name_tv, curr_location_name_value_tv,
            curr_location_addr_tv, curr_location_addr_value_tv,
            curr_location_long_tv, curr_location_long_value_tv,

            nearby_location1_label_tv, nearby_location2_label_tv, nearby_location3_label_tv,

            nearby_location1_distance_tv, nearby_location2_distance_tv, nearby_location3_distance_tv,
            nearby_location1_distance_value_tv, nearby_location2_distance_value_tv, nearby_location3_distance_value_tv,

            nearby_location1_lat_tv, nearby_location1_long_tv, nearby_location1_name_tv, nearby_location1_address_tv,
            nearby_location2_lat_tv, nearby_location2_long_tv, nearby_location2_name_tv, nearby_location2_address_tv,
            nearby_location3_lat_tv, nearby_location3_long_tv, nearby_location3_name_tv, nearby_location3_address_tv,
            nearby_location1_lat_value_tv, nearby_location1_long_value_tv, nearby_location1_name_value_tv, nearby_location1_address_value_tv,
            nearby_location2_lat_value_tv, nearby_location2_long_value_tv, nearby_location2_name_value_tv, nearby_location2_address_value_tv,
            nearby_location3_lat_value_tv, nearby_location3_long_value_tv, nearby_location3_name_value_tv, nearby_location3_address_value_tv,

            motion_detection_tv;
    ToggleButton light_tb, gyro_tb, linear_tb, temp_tb, prox_tb, magnet_tb, location_tb, nearby_location_tb;

    private SensorManager sensorManager;

    private LineChart linear_LineChart, proximity_LineChart;

    private LocationManager locationManager;
    LocationListener mlocListener;

    private Sensor lightSensor, gyroSensor, linearSensor, tempSensor, proxSensor, magnetSensor, accelerometerSensor;

    private UiModeManager uiModeManager;

    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        gyro_tv = findViewById(R.id.textView1);
        gyro_tb = findViewById(R.id.toggleButton1);
        gyro_values_tv = findViewById(R.id.textView11);

        linear_tv = findViewById(R.id.textView2);
        linear_tb = findViewById(R.id.toggleButton2);
        linear_values_tv = findViewById(R.id.textView10);

        temp_tv = findViewById(R.id.textView3);
        temp_tb = findViewById(R.id.toggleButton3);
        temp_values_tv = findViewById(R.id.textView12);

        light_tv = findViewById(R.id.textView4);
        light_tb = findViewById(R.id.toggleButton4);
        light_values_tv = findViewById(R.id.textView13);

        prox_tv = findViewById(R.id.textView5);
        prox_tb = findViewById(R.id.toggleButton5);
        prox_values_tv = findViewById(R.id.textView14);

        magnet_tv = findViewById(R.id.textView6);
        magnet_tb = findViewById(R.id.toggleButton6);
        magnet_values_tv = findViewById(R.id.textView15);
        location_tb = findViewById(R.id.toggleButton17);

        curr_location_lat_tv = findViewById(R.id.textView18);
        curr_location_long_tv = findViewById(R.id.textView20);
        curr_location_name_tv = findViewById(R.id.textView22);
        curr_location_addr_tv = findViewById(R.id.textView24);
        curr_location_lat_value_tv = findViewById(R.id.textView19);
        curr_location_long_value_tv = findViewById(R.id.textView21);
        curr_location_name_value_tv = findViewById(R.id.textView23);
        curr_location_addr_value_tv = findViewById(R.id.textView25);


        nearby_location_tb = findViewById(R.id.toggleButton26);

        nearby_location1_label_tv = findViewById(R.id.textView27);

        nearby_location1_lat_tv = findViewById(R.id.textView28);
        nearby_location1_long_tv = findViewById(R.id.textView30);
        nearby_location1_name_tv = findViewById(R.id.textView32);
        nearby_location1_address_tv = findViewById(R.id.textView34);

        nearby_location1_lat_value_tv = findViewById(R.id.textView29);
        nearby_location1_long_value_tv = findViewById(R.id.textView31);
        nearby_location1_name_value_tv = findViewById(R.id.textView33);
        nearby_location1_address_value_tv = findViewById(R.id.textView35);

        nearby_location2_label_tv = findViewById(R.id.textView36);

        nearby_location2_lat_tv = findViewById(R.id.textView37);
        nearby_location2_long_tv = findViewById(R.id.textView39);
        nearby_location2_name_tv = findViewById(R.id.textView41);
        nearby_location2_address_tv = findViewById(R.id.textView43);

        nearby_location2_lat_value_tv = findViewById(R.id.textView38);
        nearby_location2_long_value_tv = findViewById(R.id.textView40);
        nearby_location2_name_value_tv = findViewById(R.id.textView42);
        nearby_location2_address_value_tv = findViewById(R.id.textView44);

        nearby_location3_label_tv = findViewById(R.id.textView45);

        nearby_location3_lat_tv = findViewById(R.id.textView46);
        nearby_location3_long_tv = findViewById(R.id.textView48);
        nearby_location3_name_tv = findViewById(R.id.textView50);
        nearby_location3_address_tv = findViewById(R.id.textView52);

        nearby_location3_lat_value_tv = findViewById(R.id.textView47);
        nearby_location3_long_value_tv = findViewById(R.id.textView49);
        nearby_location3_name_value_tv = findViewById(R.id.textView51);
        nearby_location3_address_value_tv = findViewById(R.id.textView53);

        nearby_location1_distance_tv = findViewById(R.id.textViewDistance1);
        nearby_location2_distance_tv = findViewById(R.id.textViewDistance2);
        nearby_location3_distance_tv = findViewById(R.id.textViewDistance3);
        nearby_location1_distance_value_tv = findViewById(R.id.textViewDistance1Value);
        nearby_location2_distance_value_tv = findViewById(R.id.textViewDistance2Value);
        nearby_location3_distance_value_tv = findViewById(R.id.textViewDistance3Value);

        motion_detection_tv = findViewById(R.id.textViewMotionDetection);


        linear_LineChart = (LineChart) findViewById(R.id.linear_lc);
        proximity_LineChart = (LineChart) findViewById(R.id.proximity_lc);

        linear_LineChart.setTouchEnabled(true);
        linear_LineChart.setPinchZoom(true);
        proximity_LineChart.setTouchEnabled(true);
        proximity_LineChart.setPinchZoom(true);


        uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);


        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometerSensor != null) {
            sensorManager.registerListener(MainActivity.this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
            Toast.makeText(MainActivity.this, " Accelerometer Sensor sensor started", Toast.LENGTH_SHORT).show();
        }
        else{
            Log.i(TAG,"Accelerometer Sensor Not supported");
        }

        gyro_tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                {
                    gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                    if (gyroSensor != null) {
                        sensorManager.registerListener(MainActivity.this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
                        Toast.makeText(MainActivity.this, " Gyroscope sensor started", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Log.i(TAG,"Gyroscope Not supported");
                    }
                }
                else{
                    sensorManager.unregisterListener(MainActivity.this, gyroSensor);
                    gyro_values_tv.setText("0.0  0.0  0.0");
                    Toast.makeText(MainActivity.this, " Gyroscope sensor stopped", Toast.LENGTH_SHORT).show();
                }
            }
        });


        linear_tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                {
                    linearSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
                    if (linearSensor != null) {
                        sensorManager.registerListener(MainActivity.this, linearSensor, SensorManager.SENSOR_DELAY_NORMAL);
                        Toast.makeText(MainActivity.this, " Linear Acceleration sensor started", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Log.i(TAG,"Linear Acceleration Not supported");
                    }
                }
                else{
                    sensorManager.unregisterListener(MainActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION));
                    linear_values_tv.setText("0.0");
                    Toast.makeText(MainActivity.this, " Linear Acceleration sensor stopped", Toast.LENGTH_SHORT).show();

                }
            }
        });


        temp_tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                {
                    tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
                    if (tempSensor != null) {
                        sensorManager.registerListener(MainActivity.this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
                        Toast.makeText(MainActivity.this, " Ambient Temperature sensor started", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Log.i(TAG,"Ambient Temperature Not supported");
                    }
                }
                else{
                    sensorManager.unregisterListener(MainActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE));
                    linear_values_tv.setText("0.0");
                    Toast.makeText(MainActivity.this, " Ambient Temperature sensor stopped", Toast.LENGTH_SHORT).show();

                }
            }
        });


        light_tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                {
                    lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                    if (lightSensor != null) {
                        sensorManager.registerListener(MainActivity.this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
                        Toast.makeText(MainActivity.this, " Light sensor started", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Log.i(TAG,"Light Not supported");
                    }
                }
                else{
                    sensorManager.unregisterListener(MainActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
                    light_values_tv.setText("0.0");
                    Toast.makeText(MainActivity.this, " Light sensor stopped", Toast.LENGTH_SHORT).show();

                }
            }
        });

        prox_tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                {
                    proxSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                    if (proxSensor != null) {

                        sensorManager.registerListener(MainActivity.this, proxSensor, SensorManager.SENSOR_DELAY_NORMAL);
                        Toast.makeText(MainActivity.this, " Proximity sensor started", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Log.i(TAG,"Proximity Sensor Not supported");
                    }
                }
                else{
                    sensorManager.unregisterListener(MainActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY));
                    prox_values_tv.setText("0.0");
                    Toast.makeText(MainActivity.this, " Proximity sensor stopped", Toast.LENGTH_SHORT).show();

                }
            }
        });

        magnet_tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                {
                    magnetSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                    if (magnetSensor != null) {

                        sensorManager.registerListener(MainActivity.this, magnetSensor, SensorManager.SENSOR_DELAY_NORMAL);
                        Toast.makeText(MainActivity.this, " Magnetometer sensor started", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Log.i(TAG,"Magnetometer Sensor Not supported");
                    }
                }
                else{
                    sensorManager.unregisterListener(MainActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD));
                    prox_values_tv.setText("0.0  0.0  0.0");
                    Toast.makeText(MainActivity.this, " Magnetometer sensor stopped", Toast.LENGTH_SHORT).show();

                }
            }
        });

        location_tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},1);
                    }
                    else {
                        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        mlocListener = new MyLocationListener();
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, mlocListener);
                        curr_location_lat_tv.setVisibility(View.VISIBLE);
                        curr_location_long_tv.setVisibility(View.VISIBLE);
                        curr_location_name_tv.setVisibility(View.VISIBLE);
                        curr_location_addr_tv.setVisibility(View.VISIBLE);
                        curr_location_lat_value_tv.setVisibility(View.VISIBLE);
                        curr_location_long_value_tv.setVisibility(View.VISIBLE);
                        curr_location_name_value_tv.setVisibility(View.VISIBLE);
                        curr_location_addr_value_tv.setVisibility(View.VISIBLE);
                    }
                }
                else if (!isChecked){
                    locationManager.removeUpdates(mlocListener);
                    curr_location_lat_tv.setVisibility(View.GONE);
                    curr_location_long_tv.setVisibility(View.GONE);
                    curr_location_name_tv.setVisibility(View.GONE);
                    curr_location_addr_tv.setVisibility(View.GONE);
                    curr_location_lat_value_tv.setVisibility(View.GONE);
                    curr_location_long_value_tv.setVisibility(View.GONE);
                    curr_location_name_value_tv.setVisibility(View.GONE);
                    curr_location_addr_value_tv.setVisibility(View.GONE);
                }
            }
        });

        nearby_location_tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},1);
                    }
                    else {
                        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        mlocListener = new NearbyPlacesLocationListener();
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, mlocListener);

                        nearby_location1_label_tv.setVisibility(View.VISIBLE);
                        nearby_location1_lat_tv.setVisibility(View.VISIBLE);
                        nearby_location1_long_tv.setVisibility(View.VISIBLE);
                        nearby_location1_name_tv.setVisibility(View.VISIBLE);
                        nearby_location1_address_tv.setVisibility(View.VISIBLE);
                        nearby_location1_lat_value_tv.setVisibility(View.VISIBLE);
                        nearby_location1_long_value_tv.setVisibility(View.VISIBLE);
                        nearby_location1_name_value_tv.setVisibility(View.VISIBLE);
                        nearby_location1_address_value_tv.setVisibility(View.VISIBLE);

                        nearby_location2_label_tv.setVisibility(View.VISIBLE);
                        nearby_location2_lat_tv.setVisibility(View.VISIBLE);
                        nearby_location2_long_tv.setVisibility(View.VISIBLE);
                        nearby_location2_name_tv.setVisibility(View.VISIBLE);
                        nearby_location2_address_tv.setVisibility(View.VISIBLE);
                        nearby_location2_lat_value_tv.setVisibility(View.VISIBLE);
                        nearby_location2_long_value_tv.setVisibility(View.VISIBLE);
                        nearby_location2_name_value_tv.setVisibility(View.VISIBLE);
                        nearby_location2_address_value_tv.setVisibility(View.VISIBLE);

                        nearby_location3_label_tv.setVisibility(View.VISIBLE);
                        nearby_location3_lat_tv.setVisibility(View.VISIBLE);
                        nearby_location3_long_tv.setVisibility(View.VISIBLE);
                        nearby_location3_name_tv.setVisibility(View.VISIBLE);
                        nearby_location3_address_tv.setVisibility(View.VISIBLE);
                        nearby_location3_lat_value_tv.setVisibility(View.VISIBLE);
                        nearby_location3_long_value_tv.setVisibility(View.VISIBLE);
                        nearby_location3_name_value_tv.setVisibility(View.VISIBLE);
                        nearby_location3_address_value_tv.setVisibility(View.VISIBLE);

                        nearby_location1_distance_tv.setVisibility(View.VISIBLE);
                        nearby_location2_distance_tv.setVisibility(View.VISIBLE);
                        nearby_location3_distance_tv.setVisibility(View.VISIBLE);
                        nearby_location1_distance_value_tv.setVisibility(View.VISIBLE);
                        nearby_location2_distance_value_tv.setVisibility(View.VISIBLE);
                        nearby_location3_distance_value_tv.setVisibility(View.VISIBLE);
                    }
                }
                else if (!isChecked){
                    nearby_location1_label_tv.setVisibility(View.GONE);
                    nearby_location1_lat_tv.setVisibility(View.GONE);
                    nearby_location1_long_tv.setVisibility(View.GONE);
                    nearby_location1_name_tv.setVisibility(View.GONE);
                    nearby_location1_address_tv.setVisibility(View.GONE);
                    nearby_location1_lat_value_tv.setVisibility(View.GONE);
                    nearby_location1_long_value_tv.setVisibility(View.GONE);
                    nearby_location1_name_value_tv.setVisibility(View.GONE);
                    nearby_location1_address_value_tv.setVisibility(View.GONE);

                    nearby_location2_label_tv.setVisibility(View.GONE);
                    nearby_location2_lat_tv.setVisibility(View.GONE);
                    nearby_location2_long_tv.setVisibility(View.GONE);
                    nearby_location2_name_tv.setVisibility(View.GONE);
                    nearby_location2_address_tv.setVisibility(View.GONE);
                    nearby_location2_lat_value_tv.setVisibility(View.GONE);
                    nearby_location2_long_value_tv.setVisibility(View.GONE);
                    nearby_location2_name_value_tv.setVisibility(View.GONE);
                    nearby_location2_address_value_tv.setVisibility(View.GONE);

                    nearby_location3_label_tv.setVisibility(View.GONE);
                    nearby_location3_lat_tv.setVisibility(View.GONE);
                    nearby_location3_long_tv.setVisibility(View.GONE);
                    nearby_location3_name_tv.setVisibility(View.GONE);
                    nearby_location3_address_tv.setVisibility(View.GONE);
                    nearby_location3_lat_value_tv.setVisibility(View.GONE);
                    nearby_location3_long_value_tv.setVisibility(View.GONE);
                    nearby_location3_name_value_tv.setVisibility(View.GONE);
                    nearby_location3_address_value_tv.setVisibility(View.GONE);

                    nearby_location1_distance_tv.setVisibility(View.GONE);
                    nearby_location2_distance_tv.setVisibility(View.GONE);
                    nearby_location3_distance_tv.setVisibility(View.GONE);
                    nearby_location1_distance_value_tv.setVisibility(View.GONE);
                    nearby_location2_distance_value_tv.setVisibility(View.GONE);
                    nearby_location3_distance_value_tv.setVisibility(View.GONE);
                }
            }
        });




    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;

        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mGravity = sensorEvent.values.clone();
            // Shake detection
            float x = mGravity[0];
            float y = mGravity[1];
            float z = mGravity[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float)Math.sqrt(x*x + y*y + z*z);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            // Make this higher or lower according to how much
            // motion you want to detect

            if(mAccel > 1){
                // do something
                motion_detection_tv.setText(R.string.deviceInMotion);
            }
            else{
                motion_detection_tv.setText(R.string.deviceNotInMotion);
            }

        }
        if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            Log.d(TAG, "Gyroscope:" + sensorEvent.values[0]);
            gyro_values_tv.setText( Float.toString(sensorEvent.values[0])+"  "+
                                    Float.toString(sensorEvent.values[1])+"  "+
                                    Float.toString(sensorEvent.values[2]));

            GyroscopeDatabase gyroscopeDatabaseObject = GyroscopeDatabase.getInstance(this);
            Gyroscope gyroscopeObject = new Gyroscope(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
            gyroscopeDatabaseObject.gyroscopeDAO().insert(gyroscopeObject);
            // Get List
            List<Gyroscope> myEntries = gyroscopeDatabaseObject.gyroscopeDAO().getList();
            String output = "";
            for(Gyroscope m : myEntries)
            {
                output += Integer.toString(m.getId()) + " " + m.getX() + " " + m.getY() + " " + m.getZ() + "\n";

            }
            Log.i("Gyroscope : ",output);
        }
        else if (sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            Log.d(TAG, "Linear Acceleration:" + sensorEvent.values[0]+sensorEvent.values[1]+sensorEvent.values[2]);
            linear_values_tv.setText( Float.toString(sensorEvent.values[0])+"  "+
                    Float.toString(sensorEvent.values[1])+"  "+
                    Float.toString(sensorEvent.values[2]));

            LinearAccelerationDatabase linearAccelerationDatabaseObject = LinearAccelerationDatabase.getInstance(this);
            LinearAcceleration linearAccelerationObject = new LinearAcceleration(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
            linearAccelerationDatabaseObject.linearAccelerationDAO().insert(linearAccelerationObject);

            List<LinearAcceleration> lastTenEntries  = linearAccelerationDatabaseObject.linearAccelerationDAO().getLast10();

            ArrayList<Entry> values = new ArrayList<>();
            for(LinearAcceleration lin : lastTenEntries){
                values.add(new Entry(lin.getId(), ((lin.getX()+ lin.getY()+ lin.getZ())/3)));
            }

            //Line Chart
            LineDataSet set1;
            if (linear_LineChart.getData() != null &&
                    linear_LineChart.getData().getDataSetCount() > 0) {
                set1 = (LineDataSet) linear_LineChart.getData().getDataSetByIndex(0);
                set1.setValues(values);
                linear_LineChart.getData().notifyDataChanged();
                linear_LineChart.notifyDataSetChanged();
                linear_LineChart.getDescription().setEnabled(false);
            } else {
                set1 = new LineDataSet(values, "Sample Data");
                set1.setDrawIcons(false);
                set1.enableDashedLine(10f, 5f, 0f);
                set1.enableDashedHighlightLine(10f, 5f, 0f);
                set1.setColor(Color.DKGRAY);
                set1.setCircleColor(Color.DKGRAY);
                set1.setLineWidth(1f);
                set1.setCircleRadius(3f);
                set1.setDrawCircleHole(false);
                set1.setValueTextSize(9f);
                set1.setDrawFilled(true);
                set1.setFormLineWidth(1f);
                set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
                set1.setFormSize(15.f);
                if (Utils.getSDKInt() >= 18) {
                    Drawable drawable = ContextCompat.getDrawable(MainActivity.this, R.color.teal_200);
                    set1.setFillDrawable(drawable);
                } else {
                    set1.setFillColor(Color.DKGRAY);
                }
                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(set1);
                LineData data = new LineData(dataSets);
                linear_LineChart.setData(data);
            }




            // Get List
            List<LinearAcceleration> myEntries = linearAccelerationDatabaseObject.linearAccelerationDAO().getList();
            String output = "";
            for(LinearAcceleration m : myEntries)
            {
                output += Integer.toString(m.getId()) + " " + m.getX() + " " + m.getY() + " " + m.getZ() + "\n";

            }
            Log.i("Linear Acceleration : ",output);
        }
        else if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            Log.d(TAG, "Ambient Temperature:" + sensorEvent.values[0]);
            //temp_values_tv.setText(Float.toString(sensorEvent.values[0]));
            temp_values_tv.setText(Float.toString(sensorEvent.values[0]));

            AmbientTemperatureDatabase ambientTemperatureDatabaseObject = AmbientTemperatureDatabase.getInstance(this);
            AmbientTemperature ambientTemperatureObject = new AmbientTemperature(sensorEvent.values[0]);
            ambientTemperatureDatabaseObject.ambientTemperatureDAO().insert(ambientTemperatureObject);
            // Get List
            List<AmbientTemperature> myEntries = ambientTemperatureDatabaseObject.ambientTemperatureDAO().getList();
            String output = "";
            for(AmbientTemperature m : myEntries)
            {
                output += Integer.toString(m.getId()) + " " + Float.toString(m.getAmbientAirTemperature()) +"\n";
            }


            Log.i("Ambient Temperature : ",output);

        }
        else if (sensor.getType() == Sensor.TYPE_LIGHT) {
            Log.d(TAG, "Light:" + sensorEvent.values[0]);
            light_values_tv.setText(Float.toString(sensorEvent.values[0]));

            LightDatabase lightDatabaseObject = LightDatabase.getInstance(this);
            Light lightObject = new Light(sensorEvent.values[0]);
            lightDatabaseObject.lightDAO().insert(lightObject);

            if(lightObject.getIlluminance() > 5000){
                uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
            }
            else{
                uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
            }

            // Get List
            List<Light> myEntries = lightDatabaseObject.lightDAO().getList();
            String output = "";
            for(Light m : myEntries)
            {
                output += Integer.toString(m.getId()) + " " + m.getIlluminance() +"\n";

            }
            Log.i("Light : ",output);
        }
        else if (sensor.getType() == Sensor.TYPE_PROXIMITY) {
            Log.d(TAG, "Proximity:" + sensorEvent.values[0]);
            prox_values_tv.setText(Float.toString(sensorEvent.values[0]));

            ProximityDatabase proximityDatabaseObject = ProximityDatabase.getInstance(this);
            Proximity proximityObject = new Proximity(sensorEvent.values[0]);
            proximityDatabaseObject.proximityDAO().insert(proximityObject);

            List<Proximity> lastTenEntries = proximityDatabaseObject.proximityDAO().getLast10();

            ArrayList<Entry> values = new ArrayList<>();
            for(Proximity pr : lastTenEntries) {
                values.add(new Entry(pr.getId(), pr.getDistanceFromObject()));
            }

            LineDataSet set2;
            if (proximity_LineChart.getData() != null &&
                    proximity_LineChart.getData().getDataSetCount() > 0) {
                set2 = (LineDataSet) proximity_LineChart.getData().getDataSetByIndex(0);
                set2.setValues(values);
                proximity_LineChart.getData().notifyDataChanged();
                proximity_LineChart.notifyDataSetChanged();
                proximity_LineChart.getDescription().setEnabled(false);
            } else {
                set2 = new LineDataSet(values, "Sample Data");
                set2.setDrawIcons(false);
                set2.enableDashedLine(10f, 5f, 0f);
                set2.enableDashedHighlightLine(10f, 5f, 0f);
                set2.setColor(Color.DKGRAY);
                set2.setCircleColor(Color.DKGRAY);
                set2.setLineWidth(1f);
                set2.setCircleRadius(3f);
                set2.setDrawCircleHole(false);
                set2.setValueTextSize(9f);
                set2.setDrawFilled(true);
                set2.setFormLineWidth(1f);
                set2.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
                set2.setFormSize(15.f);
                if (Utils.getSDKInt() >= 18) {
                    Drawable drawable = ContextCompat.getDrawable(MainActivity.this, android.R.color.holo_orange_light);
                    set2.setFillDrawable(drawable);
                } else {
                    set2.setFillColor(Color.DKGRAY);
                }
                ArrayList<ILineDataSet> dataSets2 = new ArrayList<>();
                dataSets2.add(set2);
                LineData data2 = new LineData(dataSets2);
                proximity_LineChart.setData(data2);
            }



            if(proximityObject.getDistanceFromObject() < 2){
                //location_tb.setChecked(false);
                gyro_tb.setChecked(false);
                temp_tb.setChecked(false);
                light_tb.setChecked(false);
                magnet_tb.setChecked(false);
                linear_tb.setChecked(false);
                //prox_tb.setChecked(false);
            }

            // Get List
            List<Proximity> myEntries = proximityDatabaseObject.proximityDAO().getList();
            String output = "";
            for(Proximity m : myEntries)
            {
                output += Integer.toString(m.getId()) + " " + m.getDistanceFromObject() +"\n";

            }
            Log.i("Proximity : ",output);
        }
        else if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            Log.d(TAG, "Magnetometer:" + sensorEvent.values[0]);
            magnet_values_tv.setText(Float.toString(sensorEvent.values[0])+"  "+
                    Float.toString(sensorEvent.values[1])+"  "+
                    Float.toString(sensorEvent.values[2]));

            MagneticFieldDatabase magneticFieldDatabaseObject = MagneticFieldDatabase.getInstance(this);
            MagneticField magneticFieldObject = new MagneticField(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
            magneticFieldDatabaseObject.magneticFieldDAO().insert(magneticFieldObject);
            // Get List
            List<MagneticField> myEntries = magneticFieldDatabaseObject.magneticFieldDAO().getList();
            String output = "";
            for(MagneticField m : myEntries)
            {
                output += Integer.toString(m.getId()) + " " + m.getX() + " " + m.getY() + " " + m.getZ() + "\n";

            }
            Log.i("Magnetic Field : ",output);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    class MyLocationListener implements LocationListener {
        @Override
        public void onProviderDisabled(@NonNull String provider) {
            LocationListener.super.onProviderDisabled(provider);
        }

        @Override
        public void onLocationChanged(@NonNull Location location) {

            String name;
            String address;
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            curr_location_lat_value_tv.setText(String.valueOf(latitude));
            curr_location_long_value_tv.setText(String.valueOf(longitude));


            try {

                Geocoder geo = new Geocoder(getApplicationContext(), Locale.getDefault());
                List<Address> addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if (addresses.isEmpty()) {
                    curr_location_addr_value_tv.setText("Waiting for UserLocation");
                    curr_location_name_value_tv.setText("Name not generated yet");
                    name = null;
                    address = null;
                } else {
                    address = addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName();
                    name = addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality();
                    curr_location_addr_value_tv.setText(address);
                    curr_location_name_value_tv.setText(name);
                }

                UserLocationDatabase  userLocationDatabaseObject = UserLocationDatabase.getInstance(getApplicationContext());
                UserLocation userLocationObject = new UserLocation(name, address, latitude, longitude);
                userLocationDatabaseObject.userLocationDAO().insert(userLocationObject);
                // Get List
                List<UserLocation> myEntries = userLocationDatabaseObject.userLocationDAO().getList();
                String output = "";
                for(UserLocation m : myEntries)
                {
                    output += Integer.toString(m.getId()) + " " + m.getLatitude()+" "+m.getLongitude()+" "+m.getAddress() +"\n";

                }
                Log.i("User Location : ",output);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }


    }

    class NearbyPlacesLocationListener implements LocationListener {
        @Override
        public void onProviderDisabled(@NonNull String provider) {
            LocationListener.super.onProviderDisabled(provider);
        }

        @Override
        public void onLocationChanged(@NonNull Location location) {

            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            UserLocationDatabase  userLocationDatabaseObject = UserLocationDatabase.getInstance(getApplicationContext());

            // Get List
            List<UserLocation> myEntries = userLocationDatabaseObject.userLocationDAO().getList();
            UserLocation nearbyLocation1 = null, nearbyLocation2 = null, nearbyLocation3 = null;
            float min1 = Float.MAX_VALUE, min2 = Float.MAX_VALUE, min3 = Float.MAX_VALUE;
            //String output = "";
            for(UserLocation m : myEntries)
            {
                float[] results = new float[1];
                Location.distanceBetween(latitude, longitude, m.getLatitude(), m.getLongitude(), results);
                float distance = results[0];
                System.out.println("Distance 1 = "+ distance+" : object "+m.getAddress());
                if(distance < min1 && !(m.getLatitude() == latitude && m.getLongitude()==longitude)){
                    nearbyLocation1 = m;
                    min1 = distance;
                }
                System.out.println("Nearby 1 = "+ nearbyLocation1);
                //output += Integer.toString(m.getId()) + " " + m.getLatitude()+" "+m.getLongitude()+" "+m.getAddress() +"\n";
            }
            Log.i("\nNearby Location 1 : ","Distance = "+min1+"*"+
                    nearbyLocation1.getId()+"*\n"+
                    nearbyLocation1.getLatitude()+"*\n"+
                    nearbyLocation1.getLongitude()+"*\n"+
                    nearbyLocation1.getAddress());

            nearby_location1_distance_value_tv.setText(Float.toString(min1));
            nearby_location1_lat_value_tv.setText(Double.toString(nearbyLocation1.getLatitude()));
            nearby_location1_long_value_tv.setText(Double.toString(nearbyLocation1.getLongitude()));
            nearby_location1_name_value_tv.setText(nearbyLocation1.getName());
            nearby_location1_address_value_tv.setText(nearbyLocation1.getAddress());

            if(nearbyLocation1 != null) {
                for (UserLocation m : myEntries) {
                    float[] results = new float[1];
                    Location.distanceBetween(latitude, longitude, m.getLatitude(), m.getLongitude(), results);
                    float distance = results[0];
                    if (distance < min2 && m.getId() != nearbyLocation1.getId()
                            && !(m.getLatitude() == latitude && m.getLongitude()==longitude)
                            && !(m.getLatitude() == nearbyLocation1.getLatitude() && m.getLongitude()==nearbyLocation1.getLongitude())) {
                        nearbyLocation2 = m;
                        min2 = distance;
                    }
                    //output += Integer.toString(m.getId()) + " " + m.getLatitude() + " " + m.getLongitude() + " " + m.getAddress() + "\n";
                }
                Log.i("\nNearby Location 2 : ","Distance = "+min2+"*"+
                        nearbyLocation2.getId()+"*\n"+
                        nearbyLocation2.getLatitude()+"*\n"+
                        nearbyLocation2.getLongitude()+"*\n"+
                        nearbyLocation2.getAddress());

                nearby_location2_distance_value_tv.setText(Float.toString(min2));
                nearby_location2_lat_value_tv.setText(Double.toString(nearbyLocation2.getLatitude()));
                nearby_location2_long_value_tv.setText(Double.toString(nearbyLocation2.getLongitude()));
                nearby_location2_name_value_tv.setText(nearbyLocation2.getName());
                nearby_location2_address_value_tv.setText(nearbyLocation2.getAddress());
            }

            if(nearbyLocation2 != null) {
                for (UserLocation m : myEntries) {
                    float[] results = new float[1];
                    Location.distanceBetween(latitude, longitude, m.getLatitude(), m.getLongitude(), results);
                    float distance = results[0];
                    if (distance < min3 && m.getId() != nearbyLocation1.getId() && m.getId() != nearbyLocation2.getId()
                            && !(m.getLatitude() == latitude && m.getLongitude()==longitude)
                            && !(m.getLatitude() == nearbyLocation1.getLatitude() && m.getLongitude()==nearbyLocation1.getLongitude())
                            && !(m.getLatitude() == nearbyLocation2.getLatitude() && m.getLongitude()==nearbyLocation2.getLongitude())) {
                        nearbyLocation3 = m;
                        min3 = distance;
                    }
                    //output += Integer.toString(m.getId()) + " " + m.getLatitude() + " " + m.getLongitude() + " " + m.getAddress() + "\n";
                }
                Log.i("\nNearby Location 3 : ","Distance = "+min3+"*"+
                        nearbyLocation3.getId()+"*\n"+
                        nearbyLocation3.getLatitude()+"*\n"+
                        nearbyLocation3.getLongitude()+"*\n"+
                        nearbyLocation3.getAddress());

                nearby_location3_distance_value_tv.setText(Float.toString(min3));
                nearby_location3_lat_value_tv.setText(Double.toString(nearbyLocation3.getLatitude()));
                nearby_location3_long_value_tv.setText(Double.toString(nearbyLocation3.getLongitude()));
                nearby_location3_name_value_tv.setText(nearbyLocation3.getName());
                nearby_location3_address_value_tv.setText(nearbyLocation3.getAddress());
            }
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }


}