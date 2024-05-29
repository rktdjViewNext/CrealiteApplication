package com.crealite.crealiteapp.modelo;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.time.LocalDate;


public class Cliente extends Usuario implements Serializable {
    private String profesion;
    private String ciudad;

    // Constructor
    public Cliente(int id, String Usuario, String contraseña, String nombre, String apellidos, String telefono, String correo, LocalDate fechaNacimiento, boolean admin, byte[] foto, String profesion, String ciudad) {
        super(id,Usuario, contraseña, nombre, apellidos, telefono, correo, fechaNacimiento, admin,foto);
        this.profesion = profesion;
        this.ciudad = ciudad;
    }

    public Cliente(String Usuario, String contraseña, String nombre, String apellidos, String telefono, String correo, LocalDate fechaNacimiento, boolean admin, String profesion, String ciudad) {
        super(Usuario, contraseña, nombre, apellidos, telefono, correo, fechaNacimiento, admin);
        this.profesion = profesion;
        this.ciudad = ciudad;
    }

    public Cliente (String usuario){
        super(usuario);

    }


    public String getProfesion() {
        return profesion;
    }



    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getId(){
        return super.getId();
    }

}

