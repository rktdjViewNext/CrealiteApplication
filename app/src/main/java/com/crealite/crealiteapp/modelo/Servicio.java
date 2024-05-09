package com.crealite.crealiteapp.modelo;

import java.time.LocalDate;

public class Servicio {

    private String tipo;
    private LocalDate fecha;


    public Servicio(String tipo, LocalDate fecha) {
        this.tipo = tipo;
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
