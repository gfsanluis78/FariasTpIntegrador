package com.farias.fariastpintegrador.modelo;

/**
 * Created by Genaro Farias el 22/10/2021.
 * Estudiante de la ULP
 * gfsanluis78@gmail.com
 */

public class CredencialesUsuario {
    private String token;

    public CredencialesUsuario(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
