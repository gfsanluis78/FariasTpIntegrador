package com.farias.fariastpintegrador.modelo;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class Inmueble implements Serializable {

    private int idInmueble;
    private String direccion;
    private String uso;
    private String tipo;
    private int ambientes;
    private String montoAlquilerPropuesto;
    private Propietario duenio;
    //En falso significa que el innmueble no está disponible por alguna falla en el mismo.
    private boolean disponibilidad=true;
    private String imagen;
    private String superficie;
    private String latitud;
    private String longitud;
    private String precioAproximado;

    public Inmueble(int idInmueble, String direccion, String uso, String tipo, int ambientes, String montoALquilerPropuesto, Propietario duenio, boolean disponibilidad, String imagen) {
        this.idInmueble = idInmueble;
        this.direccion = direccion;
        this.uso = uso;
        this.tipo = tipo;
        this.ambientes = ambientes;
        this.montoAlquilerPropuesto = montoAlquilerPropuesto;
        this.duenio = duenio;
        this.disponibilidad = disponibilidad;
        this.imagen = imagen;
        this.superficie = "300";    // default
        this.latitud = "33,26";
        this.longitud = "35,45";
        this.precioAproximado = "10000000";
    }
    public Inmueble() {
        this.superficie = "300";    // default
        this.latitud = "33,26";
        this.longitud = "35,45";
        this.precioAproximado = "10000000";
    }

    public String getMontoAlquilerPropuesto() {
        return montoAlquilerPropuesto;
    }

    public void setMontoAlquilerPropuesto(String montoAlquilerPropuesto) {
        this.montoAlquilerPropuesto = montoAlquilerPropuesto;
    }

    public Propietario getDuenio() {
        return duenio;
    }

    public void setDuenio(Propietario duenio) {
        this.duenio = duenio;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public String getSuperficie() {
        return superficie;
    }

    public void setSuperficie(String superficie) {
        this.superficie = superficie;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getPrecioAproximado() {
        return precioAproximado;
    }

    public void setPrecioAproximado(String precioAproximado) {
        this.precioAproximado = precioAproximado;
    }

    public int getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(int idInmueble) {
        this.idInmueble = idInmueble;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(int ambientes) {
        this.ambientes = ambientes;
    }

    public String getmontoAlquilerPropuesto() {
        return montoAlquilerPropuesto;
    }

    public void setMontoALquilerPropuesto(String montoAlquilerPropuesto) {
        this.montoAlquilerPropuesto = montoAlquilerPropuesto;
    }

    public Propietario getPropietario() {
        return duenio;
    }

    public void setPropietario(Propietario propietario) {
        this.duenio = propietario;
    }

    public boolean isEstado() {
        return disponibilidad;
    }

    public void setEstado(boolean estado) {
        this.disponibilidad = estado;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inmueble inmueble = (Inmueble) o;
        return idInmueble == inmueble.idInmueble;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idInmueble);
    }

    @NonNull
    @Override
    public String toString() {
        return "Inmueble con id numero " + idInmueble +
                " tipo " + tipo +
                " con direccion en " + direccion
                ;
    }

    public String getUrlFoto() {

        String urlBase = "http://192.168.1.111:45455/";
        String url = urlBase + imagen.replace("\\","/");
        Log.d("mensaje: ", "La url de la foto del inmueble " + url);

        return url;
    }


}
