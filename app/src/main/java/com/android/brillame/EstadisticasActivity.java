package com.android.brillame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EstadisticasActivity extends AppCompatActivity {
    TextView cantidadElementosCanastoBrillante;
    TextView cantidadElementosCanastoNoBrillante;
    TextView pesoCanastoBrillante;
    TextView pesoCanastoNoBrillante;
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

        cantidadElementosCanastoBrillante.setText("Cantidad de elementos: "+ String.valueOf(singleton.getValueCantidadElementosCanastoBrillante()));
        cantidadElementosCanastoNoBrillante.setText("Cantidad de elementos: "+ String.valueOf(singleton.getValueCantidadElementosCanastoNoBrillante()));
        pesoCanastoBrillante.setText("Peso Total:" + String.valueOf( singleton.getValuePesoCanastoBrillante()) + " kg");
        pesoCanastoNoBrillante.setText("Peso Total:" + String.valueOf( singleton.getValuePesoCanastoNoBrillante()) + " kg");


    }
}
