package com.crealite.crealiteapp.modelo;

import java.time.LocalDate;

public class Fotografia extends Servicio{

    private int cantidadFotos;
    private String tipo;
    // Constructor
    public Fotografia(double precioServicio, String descripcion, LocalDate fechaRealizar, int duracion, int cantidadFotos, String provincia, String localidad, String tipo,int empleadosNecesarios, Proyecto proyecto, boolean finalizado) {
        super(precioServicio, descripcion, fechaRealizar, duracion, provincia, localidad,empleadosNecesarios,proyecto, finalizado);
        this.cantidadFotos = cantidadFotos;
        this.tipo = tipo;
    }

    public Fotografia(){

    }

    public int getCantidadFotos() {
        return cantidadFotos;
    }

    public void setCantidadFotos(int cantidadFotos) {
        this.cantidadFotos = cantidadFotos;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
