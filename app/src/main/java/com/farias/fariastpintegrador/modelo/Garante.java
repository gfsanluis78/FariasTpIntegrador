package com.farias.fariastpintegrador.modelo;

/**
 * Created by Genaro Farias el 25/10/2021.
 * Estudiante de la ULP
 * gfsanluis78@gmail.com
 */

public class Garante {
    private int idGarante;
    private String nombre;
    private String apellido;
    private String trabajo;
    private String dni;
    private String telefono;

    public Garante(int idGarante, String nombre, String apellido, String trabajo, String dni, String telefono) {
        this.idGarante = idGarante;
        this.nombre = nombre;
        this.apellido = apellido;
        this.trabajo = trabajo;
        this.dni = dni;
        this.telefono = telefono;
    }

    public Garante(int idGarante) {
        this.idGarante = idGarante;
    }

    public int getIdGarante() {
        return idGarante;
    }

    public void setIdGarante(int idGarante) {
        this.idGarante = idGarante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(String trabajo) {
        this.trabajo = trabajo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombreCompleto(){
        return nombre + " " + apellido;
    }
}
