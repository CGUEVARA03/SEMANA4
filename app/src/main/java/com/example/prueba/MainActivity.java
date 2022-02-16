package com.example.prueba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
     SensorManager sensorManager;
     Sensor sensor;
     SensorEventListener sensorEventListenerOverride;
    private SensorEventListener sensorEventListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView texto = (TextView)findViewById(R.id.tvSensor);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if(sensor==null)finish();

        SensorEventListener sensorEventListener = new SensorEventListener() {

            @Override
            public void onSensorChanged (@NonNull SensorEvent sensorEvent) {
                if (sensorEvent.values[0] < sensor.getMaximumRange()) {
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                    texto.setText("CAMBIANDO A ROJO");
                } else {
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                    texto.setText("SENSOR DE PROXIMIDAD");
                }
            }

            @Override
            public void onAccuracyChanged (Sensor sensor, int i) {

            }
        };
        star();
}

    private void star(){

         sensorManager.registerListener(sensorEventListener,sensor,2000*1000);
    }

    public void stop(){

         sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onPause(){
        stop();
        super.onPause();
    }

    @Override
    protected void onResume(){
        star();
        super.onResume();
    }
}