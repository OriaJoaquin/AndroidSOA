package com.android.brillame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

public class DiscoModeActivity extends AppCompatActivity {
    private Singleton singleton;
    private SensorManager sm;

    private float aceleracionValor;
    private float ultimoAceleracionValor;
    private float shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disco_mode);

        singleton = singleton.getInstance();

        sm =(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        aceleracionValor = SensorManager.GRAVITY_EARTH;
        ultimoAceleracionValor = SensorManager.GRAVITY_EARTH;
        shake = 0.00F;
    }

    @Override
    public void onResume(){
        super.onResume();
        singleton.showToast("Sacude el teléfono para activar el modo disco!!", this);


    }

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x =  sensorEvent.values[0];
            float y =  sensorEvent.values[1];
            float z =  sensorEvent.values[2];

            ultimoAceleracionValor = aceleracionValor;
            aceleracionValor = (float) Math.sqrt((double)(x*x+y*y+z*z));
            float delta = aceleracionValor - ultimoAceleracionValor;
            shake = shake * 0.9F + delta;

            Log.i("SHAKE", String.valueOf(shake));
            if(shake > 5){
                singleton.showToast("Me estás sacudiendo!!", getApplicationContext());

                /*Acá debería ir el código de enviar el comando dance por bluetooth*/
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
