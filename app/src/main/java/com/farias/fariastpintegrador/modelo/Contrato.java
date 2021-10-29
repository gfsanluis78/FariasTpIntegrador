package com.farias.fariastpintegrador.modelo;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.type.DateTime;

import java.io.Serializable;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Contrato implements Serializable {

    private int idContrato;
    private int idGarante;
    private int idInmueble;
    private int idInquilino;
    private String fechaInicio;
    private String fechaFin;
    private String montoAlquiler;
    private Garante garante;
    private Inquilino inquilino;
    private Inmueble inmueble;
    private boolean estadoCancelado;

    public Contrato() {}
    public Contrato(int idContrato, String fechaInicio, String fechaFin, String montoAlquiler, Inquilino inquilino, Inmueble inmueble) {
        this.idContrato = idContrato;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.montoAlquiler = montoAlquiler;
        this.inquilino = inquilino;
        this.inmueble = inmueble;
    }

    public Contrato(int idContrato, int idGarante, int idInmueble, int idInquilino, String fechaInicio, String fechaFin, String montoAlquiler, Garante garante, Inquilino inquilino, Inmueble inmueble, boolean estadoCancelado) {
        this.idContrato = idContrato;
        this.idGarante = idGarante;
        this.idInmueble = idInmueble;
        this.idInquilino = idInquilino;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.montoAlquiler = montoAlquiler;
        this.garante = garante;
        this.inquilino = inquilino;
        this.inmueble = inmueble;
        this.estadoCancelado = estadoCancelado;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getMontoAlquiler() {
        return montoAlquiler;
    }

    public void setMontoAlquiler(String montoAlquiler) {
        this.montoAlquiler = montoAlquiler;
    }


    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public int getIdGarante() {
        return idGarante;
    }

    public void setIdGarante(int idGarante) {
        this.idGarante = idGarante;
    }

    public int getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(int idInmueble) {
        this.idInmueble = idInmueble;
    }

    public int getIdInquilino() {
        return idInquilino;
    }

    public void setIdInquilino(int idInquilino) {
        this.idInquilino = idInquilino;
    }

    public Garante getGarante() {
        return garante;
    }

    public void setGarante(Garante garante) {
        this.garante = garante;
    }

    public boolean isEstadoCancelado() {
        return estadoCancelado;
    }

    public void setEstadoCancelado(boolean estadoCancelado) {
        this.estadoCancelado = estadoCancelado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contrato contrato = (Contrato) o;
        return idContrato == contrato.idContrato;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idContrato);
    }


    @NonNull
    @Override
    public String toString() {
        return "Este es el contrato id "+ idContrato +
                " del inmueble " + getInmueble().getIdInmueble() +
                " entre " + getInmueble().getPropietario().getNombreCompleto() + " y " +
                getInquilino().getNombreCompleto();
    }

    public String fechaInicioFormateada() {
        String d = convertStringFecha(fechaInicio);
        return d;
    }

    public String fechaFinFormateada() {
        String d = convertStringFecha(fechaFin);
        return d;
    }

    public String convertStringFecha(String fecha){
        String dia="";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        SimpleDateFormat formateadoDia = new SimpleDateFormat("dd MMM yyyy");
        try {
            Date d = dateFormat.parse(fecha);
            dia = formateadoDia.format(d);
            Log.d("mensaje", "Recibida en string " + fecha);
            Log.d("mensaje", "Fecha parseada de String a Date "+ d);
            Log.d("mensaje", "Fecha formateada a mi patern habitual " + dia);

        } catch (
                ParseException e) {
            e.printStackTrace();
        }
        return dia;
    }
}
