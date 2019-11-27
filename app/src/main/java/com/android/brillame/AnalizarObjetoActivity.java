package com.android.brillame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;


public class AnalizarObjetoActivity extends AppCompatActivity {

    final static String BRILLANTE = "1";
    Singleton singleton;
    Button btnComenzar;
    Handler bluetoothHandler;
    ImageView imgLoading;
    androidx.cardview.widget.CardView cardResultado;
    androidx.cardview.widget.CardView card;


    TextView lblBrillante;
    TextView lblPeso;
    TextView lblCesto;

    ConnectedThread ct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analizar_objeto);
        singleton = Singleton.getInstance();

        imgLoading = this.findViewById(R.id.imgLoading);
        btnComenzar = this.findViewById(R.id.btnComenzar);
        cardResultado = this.findViewById(R.id.cardResultado);
        card = this.findViewById(R.id.card);

        lblBrillante = this.findViewById(R.id.lblBrillante);
        lblPeso = this.findViewById(R.id.lblPeso);
        lblCesto = this.findViewById(R.id.lblCesto);

        imgLoading.setVisibility(View.INVISIBLE);
        cardResultado.setVisibility(View.INVISIBLE);


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
                String str;
                if(msg.what == 1){
                    str = b.getString("brillante");

                    if (str.equals(BRILLANTE)){
                        lblBrillante.setText("Objeto brillante");
                    } else {
                        lblBrillante.setText("Objeto no brillante");
                    }

                } else if(msg.what == 2) {
                    str = b.getString("peso");

                    lblPeso.setText("Peso: " + str);
                } else {
                    str = b.getString("cesto");

                    if (str.equals(BRILLANTE)){
                        lblCesto.setText("Cesto lleno: SÍ");
                    } else {
                        lblCesto.setText("Cesto lleno: NO");
                    }

                    imgLoading.setVisibility(View.GONE);
                    btnComenzar.setVisibility(View.VISIBLE);
                    cardResultado.setVisibility(View.VISIBLE);
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
            cardResultado.setVisibility(View.INVISIBLE);
            imgLoading.setVisibility(View.VISIBLE);
            btnComenzar.setVisibility(View.GONE);
            ct = new ConnectedThread(singleton.getSocket(), bluetoothHandler);
            ct.start();

        } else {
            singleton.showToast("Debés estar conectado al bluetooth para realizar esta acción.", getApplicationContext());
        }
    }

    protected void onDestroy(){
        super.onDestroy();
        if(ct != null)
            ct.cancel();
    }

}
