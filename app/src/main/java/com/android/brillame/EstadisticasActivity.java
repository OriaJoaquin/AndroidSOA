package com.android.brillame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class EstadisticasActivity extends AppCompatActivity {
    TextView cantidadElementosCanastoBrillante;
    TextView cantidadElementosCanastoNoBrillante;
    TextView pesoCanastoBrillante;
    TextView pesoCanastoNoBrillante;
    TextView canastoBrillanteLleno;
    TextView CanastoNoBrillanteLleno;
    Singleton singleton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);
        singleton = Singleton.getInstance();
        setearValores();

    }

    private void setearValores() {
        cantidadElementosCanastoBrillante = this.findViewById(R.id.lblCestoBrillanteCantidad);
        cantidadElementosCanastoNoBrillante = this.findViewById(R.id.lblCestoNoBrillanteCantidad);
        pesoCanastoBrillante = this.findViewById(R.id.lblCestoBrillantePeso);
        pesoCanastoNoBrillante = this.findViewById(R.id.lblCestoNoBrillantePeso);
        canastoBrillanteLleno = this.findViewById(R.id.lblCestoBrillanteLleno);
        CanastoNoBrillanteLleno = this.findViewById(R.id.lblCestoNoBrillanteLleno);

        cantidadElementosCanastoBrillante.setText("Cantidad de elementos: " + String.valueOf(singleton.getCantidadElementosCanastoBrillante()));
        cantidadElementosCanastoNoBrillante.setText("Cantidad de elementos: " + String.valueOf(singleton.getCantidadElementosCanastoNoBrillante()));
        pesoCanastoBrillante.setText("Peso Total:" + String.valueOf(singleton.getPesoCanastoBrillante()) + " kg");
        pesoCanastoNoBrillante.setText("Peso Total:" + String.valueOf(singleton.getPesoCanastoNoBrillante()) + " kg");
        canastoBrillanteLleno.setText("Lleno:" + String.valueOf(singleton.isContenedorBrillantesFull() ? "Si" : "No"));
        CanastoNoBrillanteLleno.setText("Lleno:" + String.valueOf(singleton.isContenedorNoBrillantesFull() ? "Si" : "No"));


    }
}
