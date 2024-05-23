package com.crealite.crealiteapp.modelo;

import android.graphics.Bitmap;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Cliente extends Usuario implements Serializable {
    private String profesion;
    private String ciudad;

    // Constructor
    public Cliente(int id, String Usuario, String contraseña, String nombre, String apellidos, String telefono, String correo, LocalDate fechaNacimiento, boolean admin, Bitmap foto, String profesion, String ciudad) {
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

