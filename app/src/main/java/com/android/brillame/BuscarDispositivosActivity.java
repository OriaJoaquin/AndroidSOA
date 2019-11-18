package com.android.brillame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BuscarDispositivosActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int REQUEST_ENABLE_BT = 0;
    private DispositivoAdapter mDispositivoAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    Button btnBuscarDispositivos;
    Button btnDetenerBusqueda;
    Button btnVerVinculados;
    Button btnBluetoothOn;
    ListView listaView;
    Singleton singleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_dispositivos);
        setearFuncionalidadBotones();
        configurarBluetooth();
        singleton = Singleton.getInstance();

    }

    private void setearFuncionalidadBotones() {
        btnBuscarDispositivos = this.findViewById(R.id.iniciarBusqueda);
        btnDetenerBusqueda = this.findViewById(R.id.detenerBusqueda);
        btnVerVinculados = this.findViewById(R.id.verVinculados);
        btnBluetoothOn = this.findViewById(R.id.btnBluetoothOn);

        btnBuscarDispositivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onIniciarBusquedaClick(v);
            }
        });

        this.btnDetenerBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDetenerBusquedaClick(v);
            }
        });

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
        mDispositivoAdapter = new DispositivoAdapter();

//        RecyclerView recyclerView = findViewById(R.id.rvContacts);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(mDispositivoAdapter);
//        IntentFilter filterBusqueda = new IntentFilter();

//        filterBusqueda.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
//        filterBusqueda.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//        filterBusqueda.addAction(BluetoothDevice.ACTION_FOUND);
//
//        registerReceiver(mBroadcastReceiver, filterBusqueda);

        listaView = findViewById(R.id.listaDispositivos);

        listaView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BluetoothDevice item = (BluetoothDevice) parent.getItemAtPosition(position);
                Log.d("MAC ADDRESS", item.getAddress());
                singleton.setValueMacAVincular(item.getAddress());
            }
        });

        if (!mBluetoothAdapter.isEnabled()) {
            btnBuscarDispositivos.setEnabled(false);
            btnDetenerBusqueda.setEnabled(false);
            btnVerVinculados.setEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {
        //unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }

    public void onIniciarBusquedaClick(View view) {
        if (mBluetoothAdapter.isDiscovering()) {
            Log.d(TAG, "onIniciarBusquedaClick : Ya se estan buscando dispositivos");
        } else if (mBluetoothAdapter.startDiscovery()) {
            Log.d(TAG, "onIniciarBusquedaClick : Buscando dispositivos");
        } else {
            Log.d(TAG, "onIniciarBusquedaClick : Error al buscar dispositivos");
        }
    }

    public void onDetenerBusquedaClick(View view) {
        if (mBluetoothAdapter.cancelDiscovery()) {
            Log.d(TAG, "onDetenerBusquedaClick : Deteniendo la busqueda de dispositivos");
        } else {
            Log.d(TAG, "onDetenerBusquedaClick : Error al detener la busqueda de dispositivos");
        }

    }

    public void onVerVinculadosClick(View view) {
        Set<BluetoothDevice> setDispositivos = mBluetoothAdapter.getBondedDevices();

        if (setDispositivos == null) {
            Log.d(TAG, "onVerVinculadosClick : Error al obtener dispositivos vinculados.");
        } else if (setDispositivos.isEmpty()) {
            Log.d(TAG, "onVerVinculadosClick : Bluetooth desactivado o no hay dispositivos vinculados.");
        } else {
            Log.d(TAG, "onVerVinculadosClick : Hay " + setDispositivos.size() + " dispositivos vinculados");


            /*if (setDispositivos.size() > 0) {
                for (BluetoothDevice currentDevice : setDispositivos) {
                    Log.i(TAG, "Device Name " + currentDevice.getName());
                    Log.i(TAG, "Device Name " + currentDevice.getAddress());

                }
            }*/

            ArrayList<BluetoothDevice> dispositivos = new ArrayList<>();
            dispositivos.addAll(setDispositivos);


            AdapterBluetoothDevice adapter = new AdapterBluetoothDevice(this, dispositivos);

            listaView.setAdapter(adapter);
        }
    }

    public void onBtnBluetoothOnClick(View view) {
        if(!mBluetoothAdapter.isEnabled())
        {
            //mBluetoothAdapter.enable();
            singleton.showToast("Encendiendo bluetooth...",this);


            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_ENABLE_BT);

            btnBuscarDispositivos.setEnabled(true);
            btnDetenerBusqueda.setEnabled(true);
            btnVerVinculados.setEnabled(true);
        } else{
            singleton.showToast("El bluetooth ya está encendido.",this);
        }
    }



    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                    Log.d(TAG, "OnReceive : Busqueda de dispositivos iniciada");
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    Log.d(TAG, "OnReceive : Busqueda de dispositivos finalizada");
                    break;
                case BluetoothDevice.ACTION_FOUND:
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    Log.d(TAG, "OnReceive : Dispositivo encontrado: " + device.getName());
                    mDispositivoAdapter.agregarDispositivos(device);

                    break;
            }
        }
    };
}
