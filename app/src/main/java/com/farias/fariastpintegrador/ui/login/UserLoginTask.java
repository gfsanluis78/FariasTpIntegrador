package com.farias.fariastpintegrador.ui.login;

import android.os.AsyncTask;

/**
 * Created by Genaro Farias el 23/10/2021.
 * Estudiante de la ULP
 * gfsanluis78@gmail.com
 */

public class UserLoginTask extends AsyncTask<Void, Void, Boolean>{
    private final String mUserId;
    private final String mPassword;

    UserLoginTask(String userId, String password) {
        mUserId = userId;
        mPassword = password;
        }


    @Override
    protected Boolean doInBackground(Void... voids) {
        return null;
    }
}
