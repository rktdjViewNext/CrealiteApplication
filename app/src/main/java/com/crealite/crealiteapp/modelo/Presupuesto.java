package com.crealite.crealiteapp.modelo;

public class Presupuesto {

    private int id;
    private double subtotal;
    private double iva;
    private double total;
    private boolean pagado;


    // Constructor
    public Presupuesto(int id, double subtotal,double iva,double total, boolean pagado) {
        this.id = id;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.pagado = pagado;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }



}
