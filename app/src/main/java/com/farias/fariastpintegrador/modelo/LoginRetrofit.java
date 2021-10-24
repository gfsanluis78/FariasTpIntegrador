package com.farias.fariastpintegrador.modelo;

/**
 * Created by Genaro Farias el 23/10/2021.
 * Estudiante de la ULP
 * gfsanluis78@gmail.com
 */

public class LoginRetrofit {
    private String token;
    private Propietario propietario;

    public LoginRetrofit(String token, Propietario propietario) {
        this.token = token;
        this.propietario = propietario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        token = token;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

}
