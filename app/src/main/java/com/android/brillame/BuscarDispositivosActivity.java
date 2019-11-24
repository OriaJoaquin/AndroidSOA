package com.android.brillame;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class BuscarDispositivosActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int REQUEST_ENABLE_BT = 0;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothAdapter mBluetoothAdapter;

    Button btnVerVinculados;
    Button btnBluetoothOn;
    ListView listaView;
    Singleton singleton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_dispositivos);
        singleton = Singleton.getInstance();
        setearFuncionalidadBotones();
        configurarBluetooth();

    }

    @Override
    public void onResume(){
        super.onResume();
        habilitarBotones();
    }

    private void setearFuncionalidadBotones() {
        btnVerVinculados = this.findViewById(R.id.verVinculados);
        btnBluetoothOn = this.findViewById(R.id.btnBluetoothOn);

        this.btnVerVinculados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onVerVinculadosClick(v);
            }
        });

        this.btnBluetoothOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnBluetoothOnClick(v);
            }
        });
    }

    private void configurarBluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        listaView = findViewById(R.id.listaDispositivos);

        listaView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BluetoothDevice item = (BluetoothDevice) parent.getItemAtPosition(position);
                Log.d("MAC ADDRESS", item.getAddress());
                singleton.setMacAVincular(item.getAddress());

                try {
                    singleton.setSocket(item.createRfcommSocketToServiceRecord(MY_UUID));

                    singleton.getSocket().connect();

                    singleton.setOutputStream(singleton.getSocket().getOutputStream());

                    singleton.setInputStream(singleton.getSocket().getInputStream());

                    singleton.setConectado(true);

                    listaView.setVisibility(View.INVISIBLE);
                    singleton.showToast("Conectado con el dispositivo.",getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        habilitarBotones();
    }

    private void habilitarBotones(){
        if (!mBluetoothAdapter.isEnabled()) {
            btnVerVinculados.setEnabled(false);
        }
        else{
            btnVerVinculados.setEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        //unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }

    public void onVerVinculadosClick(View view) {
        Set<BluetoothDevice> setDispositivos = mBluetoothAdapter.getBondedDevices();

        if (setDispositivos == null) {
            Log.d(TAG, "onVerVinculadosClick : Error al obtener dispositivos vinculados.");
        } else if (setDispositivos.isEmpty()) {
            Log.d(TAG, "onVerVinculadosClick : Bluetooth desactivado o no hay dispositivos vinculados.");
        } else {
            Log.d(TAG, "onVerVinculadosClick : Hay " + setDispositivos.size() + " dispositivos vinculados");

            ArrayList<BluetoothDevice> dispositivos = new ArrayList<>();
            dispositivos.addAll(setDispositivos);


            AdapterBluetoothDevice adapter = new AdapterBluetoothDevice(this, dispositivos);

            listaView.setAdapter(adapter);
        }
    }

    public void onBtnBluetoothOnClick(View view) {
        if(!mBluetoothAdapter.isEnabled())
        {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_ENABLE_BT);
            habilitarBotones();


        } else{
            singleton.showToast("El bluetooth ya está encendido.",this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //la entrada fue por request de bt
        if (requestCode == REQUEST_ENABLE_BT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                singleton.showToast("Encendiendo bluetooth...",this);
            }
            else{
                //rechazaste el encendido de bt
                singleton.showToast("Rechazaste el encendido de bluetooth...",this);
            }
        }
    }
}
