package com.android.brillame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "LEDv0";
    public static final boolean D = true;
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
    //Nombre del dispositivo conectado
    private String mConnectedDeviceName = null;
    // Adaptador local Bluetooth
    private BluetoothAdapter AdaptadorBT = null;
    //Objeto miembro para el servicio de ConexionBT
    private ConexionBT Servicio_BT = null;
    //Vibrador
    private Vibrator vibrador;
    //variables para el Menu de conexión
    private boolean seleccionador = false;
    Button btnAbrirActivityAnalizarObjeto;
    Button btnAbrirActivityBuscarDispositivos;
    Button btnAbrirActivityEstadisticas;
    Button btnAbrirActivityDiscoMode;
    Button btnAbrirActivityPrenderApagarLuces;
    Singleton singleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        singleton = Singleton.getInstance();
        setearFuncionalidadBotones();
    }

    @Override
    public void onResume(){
        super.onResume();

        boolean estaLleno =  singleton.getValuecontenedorBrillantesFull() || singleton.getValuecontenedorNoBrillantesFull();

        if(estaLleno){
            btnAbrirActivityAnalizarObjeto.setEnabled(false);
            singleton.showToast("El cesto está lleno", this);
        } else {
            btnAbrirActivityAnalizarObjeto.setEnabled(true);
        }
    }

    private void setearFuncionalidadBotones() {
        btnAbrirActivityAnalizarObjeto = this.findViewById(R.id.btnAnalizarObjeto);
        btnAbrirActivityBuscarDispositivos = this.findViewById(R.id.btnBuscarDispositivos);
        btnAbrirActivityEstadisticas = this.findViewById(R.id.btnEstadisticas);
        btnAbrirActivityDiscoMode = this.findViewById(R.id.btnDiscoMode);
        btnAbrirActivityPrenderApagarLuces = this.findViewById(R.id.btnPrenderApagar);


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
        singleton.setValuecontenedorBrillantesFull(true);
        Log.d("pru",String.valueOf(singleton.getValuecontenedorBrillantesFull()));
        Intent intent = new Intent(this, EstadisticasActivity.class);
        startActivity(intent);
    }

    private void abrirActivityDiscoMode(View view) {
        Intent intent = new Intent(this, DiscoModeActivity.class);
        startActivity(intent);
    }

    private void abrirActivityPrenderApagarLuces(View v) {
        Intent intent = new Intent(this, PrenderApagarLucesActivity.class);
        startActivity(intent);
    }


}
