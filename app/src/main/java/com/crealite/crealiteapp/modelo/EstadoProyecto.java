package com.crealite.crealiteapp.modelo;

public class EstadoProyecto {

    int id;
    String nombre;
    String estado;
    Proyecto proyecto;

    public EstadoProyecto(){};

    public EstadoProyecto(String nombre, String estado, Proyecto proyecto) {
        this.nombre = nombre;
        this.estado = estado;
        this.proyecto = proyecto;
    }

    public EstadoProyecto(int id,String nombre, String estado,Proyecto proyecto) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
        this.proyecto = proyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
}
