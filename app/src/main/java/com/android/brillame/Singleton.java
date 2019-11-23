package com.android.brillame;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Singleton {
    public final String COMANDO_RESET = "0";
    public final String COMANDO_INICIAR = "1";
    public final String COMANDO_DISCO_MODE = "2";
    public final String COMANDO_MOVER_IZQUIERDA = "A";
    public final String COMANDO_MOVER_DERECHA = "B";
    public final String COMANDO_MOVER_CENTRO = "C";
    public final String COMANDO_LUCES_PRENDER = "3";
    public final String COMANDO_LUCES_APAGAR = "4";

    private static Singleton instance;
    private double pesoCanastoBrillante;
    private double pesoCanastoNoBrillante;
    private int cantidadElementosCanastoBrillante;
    private int cantidadElementosCanastoNoBrillante;
    private String macAVincular;
    private boolean contenedorBrillantesFull;
    private boolean contenedorNoBrillantesFull;
    private int cantidadSacudidas;

    BluetoothSocket socket;
    private boolean conectado;
    private OutputStream outputStream;
    private InputStream inputStream;


    public static Singleton getInstance() {
        if (instance == null)
            instance = new Singleton();
        return instance;
    }

    private Singleton() {
        pesoCanastoBrillante = 0;
        pesoCanastoNoBrillante = 0;
        cantidadElementosCanastoBrillante = 0;
        cantidadElementosCanastoNoBrillante = 0;
        macAVincular = "";
        contenedorBrillantesFull = false;
        contenedorNoBrillantesFull = false;
        cantidadSacudidas = 0;
    }

    public void enviarComandoBluetooth(String comando){
        try {
            this.getOutputStream().write(comando.getBytes());
        } catch (IOException e) {
            e.printStackTrace();e.printStackTrace();
        }
    }

    public void showToast(String msg, Context context) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public double getPesoCanastoBrillante() {
        return pesoCanastoBrillante;
    }

    public double getPesoCanastoNoBrillante() {
        return pesoCanastoNoBrillante;
    }

    public int getCantidadElementosCanastoBrillante() {
        return cantidadElementosCanastoBrillante;
    }

    public int getCantidadElementosCanastoNoBrillante() { return cantidadElementosCanastoNoBrillante; }

    public String getMacAVincular() {
        return macAVincular;
    }

    public boolean isContenedorBrillantesFull() {
        return contenedorBrillantesFull;
    }

    public boolean isContenedorNoBrillantesFull() {
        return contenedorNoBrillantesFull;
    }

    public int getCantidadSacudidas() {
        return cantidadSacudidas;
    }

    public BluetoothSocket getSocket() {
        return socket;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setPesoCanastoBrillante(double pesoCanastoBrillante) { this.pesoCanastoBrillante = pesoCanastoBrillante; }

    public void setPesoCanastoNoBrillante(double pesoCanastoNoBrillante) { this.pesoCanastoNoBrillante = pesoCanastoNoBrillante; }

    public void setCantidadElementosCanastoBrillante(int cantidadElementosCanastoBrillante) { this.cantidadElementosCanastoBrillante = cantidadElementosCanastoBrillante; }

    public void setCantidadElementosCanastoNoBrillante(int cantidadElementosCanastoNoBrillante) { this.cantidadElementosCanastoNoBrillante = cantidadElementosCanastoNoBrillante; }

    public void setMacAVincular(String macAVincular) {
        this.macAVincular = macAVincular;
    }

    public void setContenedorBrillantesFull(boolean contenedorBrillantesFull) { this.contenedorBrillantesFull = contenedorBrillantesFull; }

    public void setContenedorNoBrillantesFull(boolean contenedorNoBrillantesFull) { this.contenedorNoBrillantesFull = contenedorNoBrillantesFull; }

    public void setCantidadSacudidas(int cantidadSacudidas) { this.cantidadSacudidas = cantidadSacudidas; }

    public void setSocket(BluetoothSocket socket) {
        this.socket = socket;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public boolean isConectado() {
        return conectado;
    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }
}
