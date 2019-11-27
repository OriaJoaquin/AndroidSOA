package com.android.brillame;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConnectedThread extends Thread {
    final static String BRILLANTE = "1";
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private final String TAG = "Debug";
    Singleton singleton;
    byte[] mmBuffer; // mmBuffer store for the stream

    int contadorParametros = 0;
    String key;

    Handler handler;




    public ConnectedThread(BluetoothSocket socket, Handler handler) {
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams; using temp objects because
        // member streams are final.
        try {
            tmpIn = socket.getInputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when creating input stream", e);
        }
        try {
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when creating output stream", e);
        }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
        this.handler = handler;
        singleton = singleton.getInstance();
    }

    public void run() {
        mmBuffer = new byte[1024];
        int numBytes; // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs.
        while (true) {
            try {
                // Read from the InputStream.
                numBytes = mmInStream.read(mmBuffer);

                // Send the obtained bytes to the UI activity.
                if (numBytes > 0){
                    String str = new String(mmBuffer,0,numBytes);
                    Log.i("DATA BLUETOOTH", str);

                    String [] values = str.split("\\r?\\n");


                    double peso;
                    boolean estaLleno;

                    if(values[0].equals(BRILLANTE)){
                        peso =  Double.parseDouble(values[1]);
                        singleton.setPesoCanastoBrillante(singleton.getPesoCanastoBrillante() + peso);
                        singleton.setCantidadElementosCanastoBrillante(singleton.getCantidadElementosCanastoBrillante() + 1);
                        estaLleno = Boolean.getBoolean(values[2]);
                        singleton.setContenedorBrillantesFull(estaLleno);
                    } else {
                        peso =  Double.parseDouble(values[1]);
                        singleton.setPesoCanastoNoBrillante(singleton.getPesoCanastoNoBrillante() + peso);

                        singleton.setCantidadElementosCanastoNoBrillante(singleton.getCantidadElementosCanastoNoBrillante() + 1);
                        estaLleno = Boolean.getBoolean(values[2]);

                        singleton.setContenedorNoBrillantesFull(estaLleno);
                    }


                    Message msg = handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putString("brillante", values[0]);
                    msg.what = 1;
                    msg.setData(bundle);
                    handler.sendMessage(msg);

                    msg = handler.obtainMessage();
                    bundle = new Bundle();
                    bundle.putString("peso", values[1]);
                    msg.what = 2;
                    msg.setData(bundle);
                    handler.sendMessage(msg);

                    msg = handler.obtainMessage();
                    bundle = new Bundle();
                    bundle.putString("cesto", values[2]);
                    msg.what = 3;
                    msg.setData(bundle);
                    handler.sendMessage(msg);

                }
            } catch (IOException e) {
                Log.d(TAG, "Input stream was disconnected", e);
                break;
            }
        }
    }

    // Call this method from the main activity to shut down the connection.
    public void cancel() {
        this.interrupt();
    }



}

