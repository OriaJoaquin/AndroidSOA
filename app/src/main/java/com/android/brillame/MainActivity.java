package com.android.brillame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    Button btnAbrirActivityAnalizarObjeto;
    Button btnAbrirActivityBuscarDispositivos;
    Button btnAbrirActivityEstadisticas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setearFuncionalidadBotones();

    }

    private void setearFuncionalidadBotones() {
        btnAbrirActivityAnalizarObjeto = this.findViewById(R.id.btnAnalizarObjeto);
        btnAbrirActivityBuscarDispositivos = this.findViewById(R.id.btnBuscarDispositivos);
        btnAbrirActivityEstadisticas = this.findViewById(R.id.btnEstadisticas);

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
    }

    public void abrirActivityBuscarDispositivos(View view){
        //Intent intent = new Intent(this, );77
        Intent intent = new Intent(this, BuscarDispositivosActivity.class);
        startActivity(intent);
    }

    public void abrirActivityAnalizarObjeto(View view){
        Intent intent = new Intent(this, AnalizarObjetoActivity.class);
        startActivity(intent);
    }

    public void abrirActivityEstadistica(View view){
        Intent intent = new Intent(this, EstadisticasActivity.class);
        startActivity(intent);
    }



}
