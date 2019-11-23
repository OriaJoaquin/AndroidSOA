package com.android.brillame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;


public class AnalizarObjetoActivity extends AppCompatActivity {
    Singleton singleton;
    Button btnComenzar;


    Handler bluetoothIn;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;
    TextView myLabel;

    ConnectedThread ct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analizar_objeto);
        singleton = Singleton.getInstance();

        myLabel = this.findViewById(R.id.myLabel);



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
            singleton.enviarComandoBluetooth(singleton.COMANDO_INICIAR);

            ct = new ConnectedThread(singleton.getSocket());
            ct.start();
            //iniciarThread();
        } else {
            singleton.showToast("Debés estar conectado al bluetooth para realizar esta acción.", getApplicationContext());
        }
    }

    private void iniciarThread() {
        final Handler handler = new Handler();
        final byte delimiter = 10; //This is the ASCII code for a newline character

        stopWorker = false;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopWorker)
                {
                    try
                    {
                        int bytesAvailable = singleton.getInputStream().available();
                        if(bytesAvailable > 0)
                        {
                            byte[] packetBytes = new byte[bytesAvailable];
                            singleton.getInputStream().read(packetBytes);
                            for(int i=0;i<bytesAvailable;i++)
                            {
                                byte b = packetBytes[i];
                                if(delimiter == b)
                                {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;

                                    handler.post(new Runnable()
                                    {
                                        public void run()
                                        {
                                            myLabel.setText(myLabel.getText() + "        " + data);
                                        }
                                    });
                                }
                                else
                                {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    }
                    catch (IOException ex)
                    {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }


    protected void onDestroy(){
        super.onDestroy();
        stopWorker = true;
        ct.cancel();
    }

}
