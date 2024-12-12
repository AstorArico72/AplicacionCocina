package com.arico.aplicacioncocina.modelos;

public class Orden implements java.io.Serializable {
    private int id;
    private int cliente;

    public int getId() {
        return id;
    }

    public int getCliente() {
        return cliente;
    }

    public Orden (int id, int cliente) {
        this.id = id;
        this.cliente = cliente;
    }
}
