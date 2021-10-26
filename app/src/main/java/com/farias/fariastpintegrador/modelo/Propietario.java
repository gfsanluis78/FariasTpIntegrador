package com.farias.fariastpintegrador.modelo;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class Propietario implements Serializable {

    private int idPropietario;
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String telefono;
    private String avatar;
    private String avatarFile;

    public Propietario(){}
    public Propietario(int idPropietario, String dni, String nombre, String apellido, String email, String password, String telefono, String avatar, String avatarFile) {
        this.idPropietario = idPropietario;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.avatar=avatar;
        this.avatarFile=avatarFile;
    }

    public int getId() {
        return idPropietario;
    }

    public void setId(int id) {
        this.idPropietario = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return password;
    }

    public void setContraseña(String contraseña) {
        this.password = contraseña;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

//    public int getIdPropietario() {
//        return idPropietario;
//    }
//
//    public void setIdPropietario(int idPropietario) {
//        this.idPropietario = idPropietario;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

    public String getAvatarFile() {
        return avatarFile;
    }

    public void setAvatarFile(String avatarFile) {
        this.avatarFile = avatarFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Propietario that = (Propietario) o;
        return idPropietario == that.idPropietario;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPropietario);
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    @NonNull
    @Override
    public String toString() {
        return "Propietario con id " + idPropietario +
                " nombre completo " + getNombreCompleto() +
                " email " + email +
                " password " + password +
                " telefono " + telefono +
                " avatar " + avatar +
                " avatarFile " + avatarFile +
                " dni " + dni;
    }

    public String getUrlFoto() {

        String urlBase = "http://192.168.1.111:45455/";
        String url = urlBase + avatar;
        Log.d("mensaje: ", "La url de la foto del perfil " + url);

        return url;
    }

}
