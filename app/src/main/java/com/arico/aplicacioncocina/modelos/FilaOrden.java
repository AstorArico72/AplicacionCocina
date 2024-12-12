package com.arico.aplicacioncocina.modelos;

public class FilaOrden implements java.io.Serializable {
    private int orden;
    private int artículo;
    private int cantidad;

    public FilaOrden (int idOrden, int idArticulo, int cantidad) {
        this.orden = idOrden;
        this.artículo = idArticulo;
        this.cantidad = cantidad;
    }

    public int getOrden() {
        return this.orden;
    }

    public int getArticulo() {
        return this.artículo;
    }

    public int getCantidad() {
        return this.cantidad;
    }
}
