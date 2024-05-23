package com.crealite.crealiteapp.modelo;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;
import java.time.LocalDate;

public class Usuario implements Serializable {

    private int id;
    private String usuario;
    private String contrasena;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String correo;
    private LocalDate fechaNacimiento;
    private boolean admin;
    private Bitmap foto;

    // Constructor
    public Usuario(int id,String Usuario, String contraseña, String nombre, String apellidos, String telefono, String correo, LocalDate fechaNacimiento, boolean admin, Bitmap foto) {
        this.id = id;
        this.usuario = Usuario;
        this.contrasena = contraseña;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.admin = admin;
        this.foto = foto;
    }

    public Usuario(String Usuario, String contraseña, String nombre, String apellidos, String telefono, String correo, LocalDate fechaNacimiento, boolean admin) {
        this.usuario = Usuario;
        this.contrasena = contraseña;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.admin = admin;
    }

    public Usuario(String usuario){
        this.usuario = usuario;
    }

    //Getters y Setters


    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "Usuario='" + usuario + '\'' +
                ", contraseña='" + contrasena + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", admin=" + admin +
                '}';
    }
}
