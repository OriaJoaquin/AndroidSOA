package com.android.brillame;

public class Singleton {

    private static Singleton instance;
    private Number pesoCanastoBrillante;
    private Number pesoCanastoNoBrillante;
    private Number cantidadElementosCanastoBrillante;
    private Number cantidadElementosCanastoNoBrillante;


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

    }

    public Number getValuePesoCanastoBrillante(){
        return pesoCanastoBrillante;
    }
    public Number getValuePesoCanastoNoBrillante(){
        return pesoCanastoNoBrillante;
    }
    public Number getValueCantidadElementosCanastoBrillante(){
        return cantidadElementosCanastoBrillante;
    }
    public Number getValueCantidadElementosCanastoNoBrillante(){
        return cantidadElementosCanastoNoBrillante;
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
}
