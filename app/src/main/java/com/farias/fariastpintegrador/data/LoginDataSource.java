package com.farias.fariastpintegrador.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.session.MediaSession;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.farias.fariastpintegrador.data.model.LoggedInUser;
import com.farias.fariastpintegrador.modelo.CredencialesUsuario;
import com.farias.fariastpintegrador.modelo.LoginRetrofit;
import com.farias.fariastpintegrador.modelo.LoginView;
import com.farias.fariastpintegrador.modelo.Propietario;
import com.farias.fariastpintegrador.request.ApiClient;
import com.farias.fariastpintegrador.ui.login.LoginActivity;
import com.farias.fariastpintegrador.ui.login.LoginViewModel;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    Context context = LoginActivity.getContextOfApplication();
    Propietario esUsuario;
    String credencialesUsuario;
    LoginDataSourceViewModel loginDataSourceViewModel;

    LoggedInUser fakeUser;
    String elToken = "No token";


    public Result<LoggedInUser> login(String username, String password) {
        Log.d("mensaje", "Entro al LoginDataSource con " + username + " y " + password);
        LoggedInUser fakeUser = null;


//        try {
//                Call<LoginRetrofit> request = ApiClient.getMyApiClient().login(username, password);
//                Log.d("mensaje", "Hizo el CALL desde LoginDataSource.traerToken con " + username + " y " + password);
//
//                request.enqueue(new Callback<LoginRetrofit>() {
//                    @Override
//                    public void onResponse(Call<LoginRetrofit> call, Response<LoginRetrofit> response) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<LoginRetrofit> call, Throwable t) {
//
//                    }
//                });


//            LoginTask loginTask = new LoginTask(username, password);
//            synchronized (loginTask) {
//                loginTask.execute().notify();
//                AsyncTask.Status estado = loginTask.getStatus();
//                Log.d("mensaje asynctask: ", estado.toString());
//
//                if (fakeUser != null) {
//                    return new Result.Success<>(fakeUser);
//                } else {
//                    return new Result.Error(new IOException("Usuario o contraseña incorrectos")); // closed hanging quotation
//                }
//
//            }
//
//            } catch(Exception e){
//                return new Result.Error(new IOException("Error logging in", e));
//            }


        if (fakeUser != null) {
             return new Result.Success<>(fakeUser);
        } else {
             return new Result.Error(new IOException("Usuario o contraseña incorrectos")); // closed hanging quotation
                }
    }

        public void logout() {
            // TODO: revoke authentication
        }

        private class LoginTask extends AsyncTask<HashMap<String, String>, Void,  Void> {

        private String user;
        private String pass;

            public LoginTask(String username, String password) {
                this.user = username;
                this.pass = password;
            }

            /**
             * Override this method to perform a computation on a background thread. The
             * specified parameters are the parameters passed to {@link #execute}
             * by the caller of this task.
             * <p>
             * This will normally run on a background thread. But to better
             * support testing frameworks, it is recommended that this also tolerates
             * direct execution on the foreground thread, as part of the {@link #execute} call.
             * <p>
             * This method can call {@link #publishProgress} to publish updates
             * on the UI thread.
             *
             * @param hashMaps The parameters of the task.
             * @return A result, defined by the subclass of this task.
             * @see #onPreExecute()
             * @see #onPostExecute
             * @see #publishProgress
             */
            @Override
            protected Void doInBackground(HashMap<String, String>... hashMaps) {


                // Use retrofit2 or whatever to get it
                Call<LoginRetrofit> request = ApiClient.getMyApiClient().login(user, pass);
                Log.d("mensaje", "Hizo el CALL desde LoginDataSource.traerToken con " + user + " y " + pass);

                try {
                    LoginRetrofit laRta = request.execute().body();

                    elToken = laRta.getToken();

                    SharedPreferences sharedPreferences = context.getSharedPreferences("Usuario", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", "Bearer " + elToken);
                    Log.d("mensaje", "El token guardado " + elToken);
                    editor.commit();

                    esUsuario = laRta.getPropietario();

                    fakeUser = new LoggedInUser( esUsuario.getId()+"", esUsuario.getNombre() +" "+esUsuario.getApellido(),
                            esUsuario.getEmail(),
                            2);

                    Log.d("mensaje: Loggedinuser", fakeUser.getDisplayName());

                } catch (IOException e) {
                    e.printStackTrace();
                }


                return null;
            }

            protected void onPostExecute(Long result) {
                Toast.makeText( context, "Ejecutar " + result.toString(), Toast.LENGTH_SHORT).show();
            }
        }

    private class RegistroTask extends AsyncTask<HashMap<String, String>, Void,  Void> {

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This will normally run on a background thread. But to better
         * support testing frameworks, it is recommended that this also tolerates
         * direct execution on the foreground thread, as part of the {@link #execute} call.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param hashMaps The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(HashMap<String, String>... hashMaps) {

            SharedPreferences sharedPreferences = context.getSharedPreferences("Usuario", 0);
            String elToken = sharedPreferences.getString("token", "No token");

            Log.d("mensaje", elToken);


            Call<Propietario> prop = ApiClient.getMyApiClient().obtenerUsuarioActual(elToken);
            Log.d("mensaje", "Hizo el CALL desde LoginDataSource.RegistroTask.doInBackgrund con ");

            try {
                esUsuario = prop.execute().body();
                Log.d("mensaje: Propietario ", esUsuario.toString());
                fakeUser = new LoggedInUser( esUsuario.getId()+"", esUsuario.getNombre() +" "+esUsuario.getApellido(),
                        esUsuario.getEmail(),
                        2);

                Log.d("mensaje: Loggedinuser", fakeUser.getDisplayName());

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }

    public void getData(String user, String pass, Callback<LoginRetrofit> callback){
        ApiClient.getMyApiClient().login(user, pass).enqueue(callback);
    }

    Callback<LoginRetrofit> responseCallback = new Callback<LoginRetrofit>() {

        @Override
        public void onResponse(Call<LoginRetrofit> call, Response<LoginRetrofit> response) {
            LoginRetrofit features = response.body();
            Log.d("mesajes", "Data successfully downloaded");

            // Data is returned here

            Log.d("feature", String.valueOf(features)); // for example

        }

        @Override
        public void onFailure(Call<LoginRetrofit> call, Throwable t) {
            Log.e("mensaje: ", t.toString());
        }
    };



}



