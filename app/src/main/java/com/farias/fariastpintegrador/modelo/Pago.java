package com.farias.fariastpintegrador.modelo;

import java.io.Serializable;

public class Pago implements Serializable {

    private int idPago;
    private int numeroPago;
    private int idContrato;
    private String fecha;
    private String importe;
    private Contrato contrato;


    public Pago() {}

    public Pago(int idPago, int numeroPago, int idContrato , String fecha, String importe, Contrato contrato) {
        this.idPago = idPago;
        this.numeroPago = numeroPago;
        this.idContrato = idContrato;
        this.fecha = fecha;
        this.importe = importe;
        this.contrato = contrato;
    }



    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getNumero() {
        return numeroPago;
    }

    public void setNumero(int numeroPago) {
        this.numeroPago = numeroPago;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getFechaDePago() {
        return fecha;
    }

    public void setFechaDePago(String fechaDePago) {
        this.fecha = fechaDePago;
    }

    public int getNumeroPago() {
        return numeroPago;
    }

    public void setNumeroPago(int numeroPago) {
        this.numeroPago = numeroPago;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }
}
