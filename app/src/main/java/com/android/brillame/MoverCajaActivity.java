package com.android.brillame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.EmptyStackException;

public class MoverCajaActivity extends AppCompatActivity {

    private static final int ESTADO_IZQUIERDA = 0;
    private static final int ESTADO_CENTRO = 1;
    private static final int ESTADO_DERECHA= 2;
    private int estadoActual;
    private SensorManager mSensorManager;
    private TextView tv;
    private TextView tvOrientacion;
    private ImageView imgFlecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mover_caja);

        tv = this.findViewById(R.id.tv);
        tvOrientacion = this.findViewById(R.id.tvOrientacion);

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

            if (sensorEvent.values[0] > 2.0F) {

            }

            if (sensorEvent.values[2] > 5f) { // anticlockwise
                if(estadoActual == ESTADO_CENTRO){
                    imgFlecha.setImageResource(R.drawable.ic_arrow_left_solid);
                    estadoActual = ESTADO_IZQUIERDA;
                    tvOrientacion.setText("IZQUIERDA");
                } else if (estadoActual == ESTADO_DERECHA){
                    imgFlecha.setImageResource(R.drawable.ic_arrow_up_solid);
                    estadoActual = ESTADO_CENTRO;
                    tvOrientacion.setText("CENTRO");
                }



            }
            if (sensorEvent.values[2] < -5f) { // clockwise
                if(estadoActual == ESTADO_CENTRO){
                    imgFlecha.setImageResource(R.drawable.ic_arrow_right_solid);
                    estadoActual = ESTADO_DERECHA;
                    tvOrientacion.setText("DERECHA");
                } else if (estadoActual == ESTADO_IZQUIERDA){
                    imgFlecha.setImageResource(R.drawable.ic_arrow_up_solid);
                    estadoActual = ESTADO_CENTRO;
                    tvOrientacion.setText("CENTRO");
                }
            }

            //else it will output the Roll, Pitch and Yawn values
            tv.setText("Orientation X (Roll) :"+ sensorEvent.values[2] +"\n"+
                    "Orientation Y (Pitch) :"+ sensorEvent.values[1] +"\n"+
                    "Orientation Z (Yaw) :"+ sensorEvent.values[0]);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
