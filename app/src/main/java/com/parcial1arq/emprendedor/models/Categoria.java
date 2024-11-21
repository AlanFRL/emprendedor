package com.parcial1arq.emprendedor.models;

public class Categoria {
    private int id;
    private String descripcion;

    // Constructor
    public Categoria(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Sobrescribir el método toString para mostrar la descripción
    @Override
    public String toString() {
        return descripcion; // Muestra solo la descripción de la categoría
    }

}
