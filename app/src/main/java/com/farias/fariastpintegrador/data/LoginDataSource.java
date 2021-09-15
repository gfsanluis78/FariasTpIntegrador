package com.farias.fariastpintegrador.data;

import com.farias.fariastpintegrador.data.model.LoggedInUser;
import com.farias.fariastpintegrador.modelo.Propietario;
import com.farias.fariastpintegrador.request.ApiClient;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            ApiClient api = ApiClient.getApi();
            Propietario esUsuario = api.login(username,password);
            if(esUsuario == null){
                return null;
            } else {
                LoggedInUser User = new LoggedInUser(esUsuario.getId()+"",esUsuario.getNombre() + " " + esUsuario.getApellido(), esUsuario.getAvatar());
                return new Result.Success<>(User);
            }

        //            LoggedInUser fakeUser =
//                    new LoggedInUser(
//                            java.util.UUID.randomUUID().toString(),
//                            "Jane Doe");
//            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}