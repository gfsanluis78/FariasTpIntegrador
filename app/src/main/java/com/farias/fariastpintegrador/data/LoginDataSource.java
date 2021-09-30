package com.farias.fariastpintegrador.data;

import com.farias.fariastpintegrador.data.model.LoggedInUser;
import com.farias.fariastpintegrador.modelo.Propietario;
import com.farias.fariastpintegrador.request.ApiClient;
import com.farias.fariastpintegrador.ui.login.LoginViewModel;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: Se agrega un sleep para simular la espera del request
            Thread.sleep(2000);
            ApiClient api = ApiClient.getApi();
            Propietario esUsuario = api.login(username,password);
            if(esUsuario == null){
                return null;
            } else {
                LoggedInUser User = new LoggedInUser(esUsuario.getId()+"",esUsuario.getNombre() + " " + esUsuario.getApellido(), esUsuario.getEmail(), esUsuario.getAvatar());
                return new Result.Success<>(User);
            }


        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication

    }
}