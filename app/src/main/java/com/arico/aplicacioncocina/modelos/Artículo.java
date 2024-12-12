package com.arico.aplicacioncocina.modelos;


import java.util.List;

public class Artículo implements java.io.Serializable {
    private int id; //Sin signo - no admite valores negativos.
    private String nombre;
    private int precio; //Sin signo - no admite valores negativos.
    private List<String> atributos; //Está aquí por compatibilidad.
    private String imagen; //Está aquí por compatibilidad.

    public Artículo(int id, String nombre, int precio, List<String> atributos, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.atributos = atributos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public List<String> getAtributos() {
        return atributos;
    }

    public String getImagen() {
        return imagen;
    }

    public void setAtributos(List<String> atributos) {
        this.atributos = atributos;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getPrecio() {
        return this.precio;
    }

    public String getNombre() {
        return this.nombre;
    }

    public int getId() {
        return this.id;
    }
}