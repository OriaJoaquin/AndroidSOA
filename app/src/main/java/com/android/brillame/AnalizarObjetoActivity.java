package com.android.brillame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;


public class AnalizarObjetoActivity extends AppCompatActivity {
    Singleton singleton;
    Button btnComenzar;
    Handler bluetoothHandler;



    volatile boolean stopWorker;
    TextView myLabel;

    ConnectedThread ct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analizar_objeto);
        singleton = Singleton.getInstance();

        myLabel = this.findViewById(R.id.myLabel);



        setearFuncionalidadBotones();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bluetoothHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                    Bundle b =  msg.getData();
                    String str = b.getString("Valor");
                    if(msg.what == 1){
                        Log.i("BUNDLE PESO", str);
                    } else if (msg.what == 2){
                        Log.i("BUNDLE BRILLANTE", str);
                    } else {
                        Log.i("BUNDLE CESTO", str);
                    }
            }
        };
    }

    private void setearFuncionalidadBotones(){
        btnComenzar = this.findViewById(R.id.btnComenzar);
        btnComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarProceso(v);
            }
        });
    }

    private void iniciarProceso(View v) {
        if(singleton.isConectado()){
            singleton.enviarComandoBluetooth(singleton.COMANDO_INICIAR);

            ct = new ConnectedThread(singleton.getSocket(), bluetoothHandler);
            ct.start();



        } else {
            singleton.showToast("Debés estar conectado al bluetooth para realizar esta acción.", getApplicationContext());
        }
    }

    protected void onDestroy(){
        super.onDestroy();
        stopWorker = true;
        ct.cancel();
    }

}
