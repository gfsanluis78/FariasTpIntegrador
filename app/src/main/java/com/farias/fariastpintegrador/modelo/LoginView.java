package com.farias.fariastpintegrador.modelo;

/**
 * Created by Genaro Farias el 20/10/2021.
 * Estudiante de la ULP
 * gfsanluis78@gmail.com
 */

public class LoginView {
    private String Usuario;
    private String Clave;

    public LoginView(String usuario, String clave) {
        Usuario = usuario;
        Clave = clave;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }
}
