package com.crealite.crealiteapp.modelo;

import java.time.LocalDate;

public class Diseno extends Servicio{

    private String dimensiones;
    private boolean animado;
    private String tipo;

    // Constructor
    public Diseno(double precioServicio, String descripcion, LocalDate fechaRealizar, int duracion, int empleadosNecesarios, String dimensiones, boolean animado, String provincia,String localidad, String tipo,Proyecto proyecto,boolean finalizado) {
        super(precioServicio, descripcion, fechaRealizar, duracion,provincia,localidad,empleadosNecesarios, proyecto,finalizado);
        this.dimensiones = dimensiones;
        this.animado = animado;
        this.tipo = tipo;
    }

    public Diseno(){

    }

    public String getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }

    public boolean isAnimado() {
        return animado;
    }

    public void setAnimado(boolean animado) {
        this.animado = animado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
