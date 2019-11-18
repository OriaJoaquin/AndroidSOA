package com.android.brillame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DiscoModeActivity extends AppCompatActivity {
    Singleton singleton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disco_mode);

        singleton = singleton.getInstance();
    }

    @Override
    public void onResume(){
        super.onResume();
        singleton.showToast("Sacude el teléfono para activar el modo disco!!", this);


    }
}
