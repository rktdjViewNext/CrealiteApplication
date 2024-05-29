package com.crealite.crealiteapp.modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Video extends Servicio implements Serializable {

    private double duracionVideo;
    private boolean makingOff;
    private String tipo;

    // Constructor
    public Video( double precioServicio, String descripcion, LocalDate fechaRealizar, int duracion, double duracionVideo, boolean makingOff,String provincia,String localidad, String tipo, int empleadosNecesarios,Proyecto proyecto, boolean finalizado) {
        super(precioServicio, descripcion, fechaRealizar, duracion, provincia,localidad,empleadosNecesarios,proyecto,finalizado);
        this.duracionVideo = duracionVideo;
        this.makingOff = makingOff;
        this.tipo = tipo;
    }

    public Video(){

    }

    public double getDuracionVideo() {
        return duracionVideo;
    }

    public void setDuracionVideo(double duracionVideo) {
        this.duracionVideo = duracionVideo;
    }

    public boolean isMakingOff() {
        return makingOff;
    }

    public void setMakingOff(boolean makingOff) {
        this.makingOff = makingOff;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String  tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Video{" +
                "duracionVideo=" + duracionVideo +
                ", makingOff=" + makingOff +
                ", tipo=" + tipo +
                "} " + super.toString();
    }
}
