package com.android.brillame;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MoverCajaActivity extends AppCompatActivity {

    private static final int ESTADO_IZQUIERDA = 0;
    private static final int ESTADO_CENTRO = 1;
    private static final int ESTADO_DERECHA= 2;
    private static final float sensibilidad = 4f;

    int estadoActual;
    SensorManager mSensorManager;
    TextView tv;
    TextView tvOrientacion;
    ImageView imgFlecha;
    Singleton singleton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mover_caja);

        tv = this.findViewById(R.id.tv);
        tvOrientacion = this.findViewById(R.id.tvOrientacion);

        singleton = singleton.getInstance();

        configurarSensor();
        setearImagenes();
    }

    private void configurarSensor() {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(sensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);

    }

    private void setearImagenes(){
        imgFlecha = this.findViewById(R.id.imgFlecha);
        estadoActual = ESTADO_CENTRO;
        tvOrientacion.setText("CENTRO");
    }

    @Override
    protected void onStop()
    {
        //unregister the sensor listener
        mSensorManager.unregisterListener(sensorListener);
        super.onStop();
    }

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
            {
                return;
            }

            if (sensorEvent.values[2] > sensibilidad) { // anticlockwise
                if(estadoActual == ESTADO_CENTRO){
                    imgFlecha.setImageResource(R.drawable.ic_arrow_left_solid);
                    estadoActual = ESTADO_IZQUIERDA;
                    tvOrientacion.setText("IZQUIERDA");

                    if(singleton.isConectado()){
                        singleton.enviarComandoBluetooth(singleton.COMANDO_MOVER_IZQUIERDA);
                    } else {
                        singleton.showToast("Debés estar conectado al bluetooth para realizar esta acción.", getApplicationContext());
                    }

                } else if (estadoActual == ESTADO_DERECHA){
                    imgFlecha.setImageResource(R.drawable.ic_arrow_up_solid);
                    estadoActual = ESTADO_CENTRO;
                    tvOrientacion.setText("CENTRO");

                    if(singleton.isConectado()){
                        singleton.enviarComandoBluetooth(singleton.COMANDO_MOVER_CENTRO);
                    } else {
                        singleton.showToast("Debés estar conectado al bluetooth para realizar esta acción.", getApplicationContext());
                    }
                }
            }

            if (sensorEvent.values[2] < -sensibilidad) { // clockwise
                if(estadoActual == ESTADO_CENTRO){
                    imgFlecha.setImageResource(R.drawable.ic_arrow_right_solid);
                    estadoActual = ESTADO_DERECHA;
                    tvOrientacion.setText("DERECHA");

                    if(singleton.isConectado()){
                        singleton.enviarComandoBluetooth(singleton.COMANDO_MOVER_DERECHA);
                    } else {
                        singleton.showToast("Debés estar conectado al bluetooth para realizar esta acción.", getApplicationContext());
                    }
                } else if (estadoActual == ESTADO_IZQUIERDA){
                    imgFlecha.setImageResource(R.drawable.ic_arrow_up_solid);
                    estadoActual = ESTADO_CENTRO;
                    tvOrientacion.setText("CENTRO");

                    if(singleton.isConectado()){
                        singleton.enviarComandoBluetooth(singleton.COMANDO_MOVER_CENTRO);
                    } else {
                        singleton.showToast("Debés estar conectado al bluetooth para realizar esta acción.", getApplicationContext());
                    }
                }
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
