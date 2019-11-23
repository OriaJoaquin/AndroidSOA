package com.android.brillame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

public class DiscoModeActivity extends AppCompatActivity {
    private Singleton singleton;
    private SensorManager mSensorManager;

    private float aceleracionValor;
    private float ultimoAceleracionValor;
    private float shake;
    private ImageView imgCarlton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disco_mode);

        imgCarlton = this.findViewById(R.id.imgCarlton);
        imgCarlton.setVisibility(View.INVISIBLE);

        singleton = singleton.getInstance();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(sensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        aceleracionValor = SensorManager.GRAVITY_EARTH;
        ultimoAceleracionValor = SensorManager.GRAVITY_EARTH;
        shake = 0.00F;
    }

    @Override
    public void onResume() {
        super.onResume();
        singleton.showToast("Sacude el teléfono para activar el modo disco!!", this);
        singleton.setCantidadSacudidas(0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorListener);
    }


    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            ultimoAceleracionValor = aceleracionValor;
            aceleracionValor = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = aceleracionValor - ultimoAceleracionValor;
            shake = shake * 0.9F + delta;

            if (shake > 5) {
                singleton.setCantidadSacudidas(singleton.getCantidadSacudidas() + 1);
                if (singleton.getCantidadSacudidas() >= 3) {
                    try {
                        if(singleton.isConectado()){
                            //COMUNICACION CON BT
                            singleton.showToast("Modo disco!!!", getApplicationContext());
                            String comando = "2";

                            singleton.getOutputStream().write(comando.getBytes());
                            imgCarlton.setVisibility(View.VISIBLE);
                        } else {
                            singleton.showToast("Debés estar conectado al bluetooth para realizar esta acción.", getApplicationContext());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    String mensaje = String.format("Faltan %d sacudidas.", 3 - singleton.getCantidadSacudidas());
                    singleton.showToast(mensaje, getApplicationContext());
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
