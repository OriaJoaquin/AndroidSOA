package com.android.brillame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class AnalizarObjetoActivity extends AppCompatActivity {
    Singleton singleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analizar_objeto);
        singleton = Singleton.getInstance();

    }
}
