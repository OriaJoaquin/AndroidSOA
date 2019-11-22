package com.android.brillame;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;

public class Singleton {

    private static Singleton instance;
    private Number pesoCanastoBrillante;
    private Number pesoCanastoNoBrillante;
    private Number cantidadElementosCanastoBrillante;
    private Number cantidadElementosCanastoNoBrillante;
    private String macAVincular;
    private boolean contenedorBrillantesFull;
    private boolean contenedorNoBrillantesFull;
    private Number sacudidas;



    BluetoothSocket socket;
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
        sacudidas = 0;
    }

    public Number getValuePesoCanastoBrillante() {
        return pesoCanastoBrillante;
    }

    public Number getValuePesoCanastoNoBrillante() {
        return pesoCanastoNoBrillante;
    }

    public Number getValueCantidadElementosCanastoBrillante() {
        return cantidadElementosCanastoBrillante;
    }

    public Number getValuesacudidas() {
        return sacudidas;
    }
    public Number getValueCantidadElementosCanastoNoBrillante() {
        return cantidadElementosCanastoNoBrillante;
    }

    public String getValueMacAVincular() {
        return macAVincular;
    }

    public boolean getValuecontenedorBrillantesFull() {
        return contenedorBrillantesFull;
    }

    public boolean getValuecontenedorNoBrillantesFull() {
        return contenedorNoBrillantesFull;
    }


    public void setValuePesoCanastoBrillante(Number value) {
        this.pesoCanastoBrillante = value;
    }

    public void setValuePesoCanastoNoBrillante(Number value) {
        this.pesoCanastoNoBrillante = value;
    }

    public void setValueCantidadElementosCanastoBrillante(Number value) {
        this.cantidadElementosCanastoBrillante = value;
    }

    public void setValueCantidadElementosCanastoNoBrillante(Number value) {
        this.cantidadElementosCanastoNoBrillante = value;
    }

    public void setValueMacAVincular(String value) {
        this.macAVincular = value;
    }

    public void setValuecontenedorBrillantesFull(boolean lleno) {
        contenedorBrillantesFull = lleno;
    }

    public void setValuecontenedorNoBrillantesFull(boolean lleno) {
        contenedorNoBrillantesFull = lleno;
    }

    public void setValuesacudidas(Number value){
        sacudidas = value;
    }

    public void showToast(String msg, Context context) {
        // Toast.makeText(this,msg, Toast.LENGTH_LONG).show();
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
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

    public void setSocket(BluetoothSocket socket) {
        this.socket = socket;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
