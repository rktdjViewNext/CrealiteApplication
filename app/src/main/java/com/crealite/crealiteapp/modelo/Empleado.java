package com.crealite.crealiteapp.modelo;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Empleado extends Usuario {
    private String dni;
    private double precioHora;
    private List<Especialidad> especialidades;
    private List<Servicio> servicios;

    // Constructor
    public Empleado(String usuario, String contraseña, String nombre, String apellidos, String telefono, String correo, LocalDate fechaNacimiento, boolean admin, String dni, double precioHora, List<Especialidad> especialidades, ArrayList<Servicio> servicios) {
        super(usuario, contraseña, nombre, apellidos, telefono, correo, fechaNacimiento, admin);
        this.dni = dni;
        this.precioHora = precioHora;
        this.especialidades = especialidades;
        this.servicios = servicios;
    }

    public Empleado(String usuario, String contraseña, String nombre, String apellidos, String telefono, String correo, LocalDate fechaNacimiento, boolean admin, String dni, double precioHora, List<Especialidad> especialidades) {
        super(usuario, contraseña, nombre, apellidos, telefono, correo, fechaNacimiento, admin);
        this.dni = dni;
        this.precioHora = precioHora;
        this.especialidades = especialidades;
        this.servicios = new ArrayList<>();
    }

    public Empleado(String usuario){
        super(usuario);
    }


    // Getters y setters

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public double getPrecioHora() {
        return precioHora;
    }

    public void setPrecioHora(double precioHora) {
        this.precioHora = precioHora;
    }

    public String getEspecialidades() {
        StringBuilder getEspecilidades = new StringBuilder();

        for (int i = 0; i < especialidades.size(); i++) {
            if (especialidades.size() == 1){
                return especialidades.get(i).toString();
            }else{
                if (i == 0){
                    getEspecilidades.append(especialidades.get(i).toString());
                }else{
                    getEspecilidades.append(","+especialidades.get(i).toString());
                }
            }
        }

        return getEspecilidades.toString();
    }

    public void setEspecialidades(List<Especialidad> especialidades) {
        this.especialidades = especialidades;
    }

    public static List<Especialidad> devolverListaEspecilidades(String specialidad){
        String [] separarEspecialidades = specialidad.split(",");
        List<Especialidad> listaEspecilidad = new ArrayList<>();
        for (int i = 0; i < separarEspecialidades.length; i++) {
            if (separarEspecialidades[i].equalsIgnoreCase("FOTOGRAFO")){
                listaEspecilidad.add(Especialidad.FOTOGRAFO);
            }else if (separarEspecialidades[i].equalsIgnoreCase("FILMMAKER")){
                listaEspecilidad.add(Especialidad.FILMMAKER);
            }else if (separarEspecialidades[i].equalsIgnoreCase("EDITOR")){
                listaEspecilidad.add(Especialidad.EDITOR);
            }else if (separarEspecialidades[i].equalsIgnoreCase("DISEÑADOR")){
                listaEspecilidad.add(Especialidad.DISEÑADOR);
            }else if (separarEspecialidades[i].equalsIgnoreCase("ANIMADOR")){
                listaEspecilidad.add(Especialidad.ANIMADOR);
            }
        }
        return listaEspecilidad;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "dni='" + dni + '\'' +
                ", precioHora=" + precioHora +
                ", especialidades=" + especialidades +
                "} " + super.toString();
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }
}

