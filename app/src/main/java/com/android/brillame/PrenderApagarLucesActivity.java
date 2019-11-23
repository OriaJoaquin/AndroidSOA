package com.android.brillame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

public class PrenderApagarLucesActivity extends AppCompatActivity {
    SensorManager mSensorManager;
    ImageView imagenFoco;
    Singleton singleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prender_apagar_luces);

        singleton = singleton.getInstance();

        imagenFoco = this.findViewById(R.id.imageViewFoco);

        configurarSensor();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorListener);
    }

    private void configurarSensor(){
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(sensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);
    }

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                if (event.values[0] == 0) {
                    //COMUNICACION CON BT
                    if(singleton.isConectado()){
                        singleton.enviarComandoBluetooth(singleton.COMANDO_LUCES_PRENDER);
                    } else {
                        singleton.showToast("Debés estar conectado al bluetooth para realizar esta acción.", getApplicationContext());
                    }
                    //Toast.makeText(getApplicationContext(), "Encender luces del led", Toast.LENGTH_SHORT).show();
                    imagenFoco.setImageResource(R.mipmap.light_on);
                } else {
                    //far
                    if(singleton.isConectado()){
                        singleton.enviarComandoBluetooth(singleton.COMANDO_LUCES_APAGAR);
                    } else {
                        singleton.showToast("Debés estar conectado al bluetooth para realizar esta acción.", getApplicationContext());
                    }
                    imagenFoco.setImageResource(R.mipmap.light_off);
                    //Toast.makeText(getApplicationContext(), "far", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
