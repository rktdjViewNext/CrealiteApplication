package com.crealite.crealiteapp.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Proyecto implements Serializable {
    private int id;
    private String nombre;
    private boolean pagado;
    private Presupuesto presupuesto;
    private Cliente cliente;
    private Boolean finalizado;


    // Constructor
    public Proyecto(int id, String nombre, boolean pagado, Presupuesto presupuesto, Cliente cliente, boolean finalizado) {
        this.id = id;
        this.pagado = pagado;
        this.presupuesto = presupuesto;
        this.cliente = cliente;
        this.nombre = nombre;
        this.finalizado = finalizado;
    }

    public Proyecto(String nombre, boolean pagado, Presupuesto presupuesto, Cliente cliente, boolean finalizado) {
        this.pagado = pagado;
        this.presupuesto = presupuesto;
        this.cliente = cliente;
        this.nombre = nombre;
        this.finalizado = finalizado;
    }

    public Proyecto(String nombre, Cliente cliente) {
        this.cliente = cliente;
        this.nombre = nombre;
    }


    public Proyecto(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setPresupuesto(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;
    }

    public Boolean getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(Boolean finalizado) {
        this.finalizado = finalizado;
    }

    @Override
    public String toString() {
        return "Proyecto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", pagado=" + pagado +
                ", presupuesto=" + presupuesto +
                ", cliente=" + cliente +
                '}';
    }
}
