package com.android.brillame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    // Tipos de mensaje enviados y recibidos desde el Handler de ConexionBT
    public static final int Mensaje_Estado_Cambiado = 1;
    public static final int Mensaje_Leido = 2;
    public static final int Mensaje_Escrito = 3;
    public static final int Mensaje_Nombre_Dispositivo = 4;
    public static final int Mensaje_TOAST = 5;
    public static final int MESSAGE_Desconectado = 6;
    public static final int REQUEST_ENABLE_BT = 7;

    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    Button btnAbrirActivityAnalizarObjeto;
    Button btnAbrirActivityBuscarDispositivos;
    Button btnAbrirActivityEstadisticas;
    Button btnAbrirActivityDiscoMode;
    Button btnAbrirActivityPrenderApagarLuces;
    Button btnAbrirActivityMoverCaja;
    Singleton singleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        singleton = Singleton.getInstance();
        setearFuncionalidadBotones();

        if(!singleton.isConectado()){
            btnAbrirActivityPrenderApagarLuces.setEnabled(false);
            btnAbrirActivityEstadisticas.setEnabled(false);
            btnAbrirActivityDiscoMode.setEnabled(false);
            btnAbrirActivityMoverCaja.setEnabled(false);
            btnAbrirActivityAnalizarObjeto.setEnabled(false);
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        boolean estaLleno =  singleton.isContenedorBrillantesFull() || singleton.isContenedorNoBrillantesFull();


        if(singleton.isConectado()){
            btnAbrirActivityPrenderApagarLuces.setEnabled(true);
            btnAbrirActivityEstadisticas.setEnabled(true);
            btnAbrirActivityDiscoMode.setEnabled(true);
            btnAbrirActivityMoverCaja.setEnabled(true);
            btnAbrirActivityAnalizarObjeto.setEnabled(true);
        }
    }

    private void setearFuncionalidadBotones() {
        btnAbrirActivityAnalizarObjeto = this.findViewById(R.id.btnAnalizarObjeto);
        btnAbrirActivityBuscarDispositivos = this.findViewById(R.id.btnBuscarDispositivos);
        btnAbrirActivityEstadisticas = this.findViewById(R.id.btnEstadisticas);
        btnAbrirActivityDiscoMode = this.findViewById(R.id.btnDiscoMode);
        btnAbrirActivityPrenderApagarLuces = this.findViewById(R.id.btnPrenderApagar);
        btnAbrirActivityMoverCaja = this.findViewById(R.id.btnMoverCaja);


        btnAbrirActivityAnalizarObjeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActivityAnalizarObjeto(v);
            }
        });

        this.btnAbrirActivityBuscarDispositivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActivityBuscarDispositivos(v);
            }
        });

        this.btnAbrirActivityEstadisticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActivityEstadistica(v);
            }
        });

        this.btnAbrirActivityDiscoMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActivityDiscoMode(v);
            }
        });

        this.btnAbrirActivityPrenderApagarLuces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActivityPrenderApagarLuces(v);
            }
        });

        this.btnAbrirActivityMoverCaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActivityMoverCaja(v);
            }
        });
    }


    public void abrirActivityBuscarDispositivos(View view) {
        Intent intent = new Intent(this, BuscarDispositivosActivity.class);
        startActivity(intent);
    }

    public void abrirActivityAnalizarObjeto(View view) {
        Intent intent = new Intent(this, AnalizarObjetoActivity.class);
        startActivity(intent);
    }

    public void abrirActivityEstadistica(View view) {
        Intent intent = new Intent(this, EstadisticasActivity.class);
        startActivity(intent);
    }

    private void abrirActivityDiscoMode(View view) {
        Intent intent = new Intent(this, DiscoModeActivity.class);
        startActivity(intent);
    }

    private void abrirActivityPrenderApagarLuces(View view) {
        Intent intent = new Intent(this, PrenderApagarLucesActivity.class);
        startActivity(intent);
    }

    private void abrirActivityMoverCaja(View view) {
        Intent intent = new Intent(this, MoverCajaActivity.class);
        startActivity(intent);
    }
}
