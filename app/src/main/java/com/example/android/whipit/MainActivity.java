package com.example.android.whipit;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    SensorManager sensorManager;
    Sensor motionSensor;
    private SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.values[2]>17||event.values[2]<-17)
            {
                MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.whip);
                mediaPlayer.start();
                Log.i("SensorValue: ", event.values[2] + "");
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.i("AccuracyValue: ", sensor.toString() + " -accuracy: " + accuracy);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for(Sensor sensor: sensorList)
        {
            Log.i("SensorFound: ", sensor.toString());
        }
        motionSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
    @Override
    protected void onResume() {
        if(motionSensor != null)
        {
            sensorManager.registerListener(sensorListener, motionSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
        {
            Log.i("SensorValue: ", "not found");
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(sensorListener);
        super.onPause();
    }

}
