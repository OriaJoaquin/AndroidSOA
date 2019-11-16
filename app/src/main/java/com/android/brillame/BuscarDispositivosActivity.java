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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BuscarDispositivosActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DispositivoAdapter mDispositivoAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    Button btnBuscarDispositivos;
    Button btnDetenerBusqueda;
    Button btnVerVinculados;
    ListView listaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_dispositivos);

        setearFuncionalidadBotones();


        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mDispositivoAdapter = new DispositivoAdapter();
        RecyclerView recyclerView = findViewById(R.id.rvContacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mDispositivoAdapter);
        IntentFilter filterBusqueda = new IntentFilter();
        filterBusqueda.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filterBusqueda.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filterBusqueda.addAction(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mBroadcastReceiver, filterBusqueda);

        listaView = findViewById(R.id.listaDispositivos);

        listaView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BluetoothDevice item = (BluetoothDevice) parent.getItemAtPosition(position);
                Log.d("MAC ADDRESS", item.getAddress());
            }
        });

       // RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);

       /* // Initialize contacts
        contacts = Contact.createContactsList(20);
        // Create adapter passing in the sample user data
        DispositivoAdapter adapter = new DispositivoAdapter(contacts);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));*/

    }

    private void setearFuncionalidadBotones() {
        btnBuscarDispositivos = this.findViewById(R.id.iniciarBusqueda);
        btnDetenerBusqueda = this.findViewById(R.id.detenerBusqueda);
        btnVerVinculados = this.findViewById(R.id.verVinculados);

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
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mBroadcastReceiver);
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


            if (setDispositivos.size() > 0) {
                for (BluetoothDevice currentDevice : setDispositivos) {
                    Log.i(TAG,"Device Name " + currentDevice.getName());
                    Log.i(TAG,"Device Name " + currentDevice.getAddress());

                }
            }

            ArrayList<BluetoothDevice> dispositivos = new ArrayList<>();
            dispositivos.addAll(setDispositivos);


            AdapterBluetoothDevice adapter = new AdapterBluetoothDevice(this, dispositivos);

            listaView.setAdapter(adapter);
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
