package com.android.brillame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class AnalizarObjetoActivity extends AppCompatActivity {
    Singleton singleton;
    Button btnComenzar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analizar_objeto);
        singleton = Singleton.getInstance();

        setearFuncionalidadBotones();
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
            String comando = "1";
            singleton.enviarComandoBluetooth(comando);
        } else {
            singleton.showToast("Debés estar conectado al bluetooth para realizar esta acción.", getApplicationContext());
        }
    }
}
