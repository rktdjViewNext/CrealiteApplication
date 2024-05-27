package com.crealite.crealiteapp.modelo;

import java.io.Serializable;
import java.time.LocalDate;


public class Servicio implements Serializable {

    private int id;
    private double precioServicio;
    private String descripcion;
    private LocalDate fechaRealizar;
    private int duracion;
    private String localidad;
    private String provincia;

    private int empleadosNecesarios;
    private boolean empleadosAsignados;
    private Proyecto proyecto;


    // Constructor
    public Servicio(double precioServicio, String descripcion, LocalDate fechaRealizar, int duracion, String provincia, String localidad, int empleadosNecesarios, Proyecto proyecto) {
        this.precioServicio = precioServicio;
        this.descripcion = descripcion;
        this.fechaRealizar = fechaRealizar;
        this.duracion = duracion;
        this.provincia = provincia;
        this.localidad = localidad;
        this.empleadosNecesarios = empleadosNecesarios;
        this.proyecto = proyecto;

    }

    public Servicio(int id, double precioServicio, String descripcion, LocalDate fechaRealizar, int duracion, String localidad, String provincia, int empleadosNecesarios, Proyecto proyecto) {
        this.id = id;
        this.precioServicio = precioServicio;
        this.descripcion = descripcion;
        this.fechaRealizar = fechaRealizar;
        this.duracion = duracion;
        this.localidad = localidad;
        this.provincia = provincia;
        this.proyecto = proyecto;
        this.empleadosNecesarios = empleadosNecesarios;
    }

    public Servicio() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrecioServicio() {
        return precioServicio;
    }

    public void setPrecioServicio(double precioServicio) {
        this.precioServicio = precioServicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaRealizar() {
        return fechaRealizar;
    }

    public void setFechaRealizar(LocalDate fechaRealizar) {
        this.fechaRealizar = fechaRealizar;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public void setEmpleadosNecesarios(int empleadosNecesarios) {
        this.empleadosNecesarios = empleadosNecesarios;
    }

    @Override
    public String toString() {
        return "Servicio{" +
                "id=" + id +
                ", precioServicio=" + precioServicio +
                ", descripcion='" + descripcion + '\'' +
                ", fechaRealizar=" + fechaRealizar +
                ", duracion=" + duracion +
                ", localidad='" + localidad + '\'' +
                ", provincia='" + provincia + '\'' +
                ", proyecto= "+ proyecto + '\'' +
                '}';
    }


    public boolean isEmpleadosAsignados() {
        return empleadosAsignados;
    }

    public void setEmpleadosAsignados(boolean empleadosAsignados) {
        this.empleadosAsignados = empleadosAsignados;
    }

    public int getEmpleadosNecesarios() {
        return empleadosNecesarios;
    }
}
