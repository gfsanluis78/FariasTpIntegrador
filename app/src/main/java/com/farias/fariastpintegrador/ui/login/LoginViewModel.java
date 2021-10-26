package com.farias.fariastpintegrador.ui.login;

import static androidx.core.content.ContextCompat.getSystemService;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.farias.fariastpintegrador.MainActivity;
import com.farias.fariastpintegrador.data.model.LoggedInUser;
import com.farias.fariastpintegrador.R;
import com.farias.fariastpintegrador.data.model.Result;
import com.farias.fariastpintegrador.modelo.LoginRetrofit;
import com.farias.fariastpintegrador.modelo.Propietario;
import com.farias.fariastpintegrador.request.ApiClient;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<LoginFormState> loginFormState;
    private MutableLiveData<Propietario> propietario;
    private MutableLiveData<LoginRetrofit> loginRetrofitMutableLiveData;
    private Context context;
    private MutableLiveData<String> suceso;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();              // Ok
        loginFormState = new MutableLiveData<>();                   // Ok
        propietario = new MutableLiveData<>();                      // Ok
        loginRetrofitMutableLiveData = new MutableLiveData<>();     // Ok
        suceso = new MutableLiveData<>();                           // Ok
    }

    public MutableLiveData<LoginFormState> getLoginFormState() {
        if( loginFormState == null){
            loginFormState = new MutableLiveData<>();
        }
        return loginFormState;
    }


    public MutableLiveData<Propietario> getPropietario() {
        if( propietario == null) {
            propietario = new MutableLiveData<>();
        }
        return propietario;
    }

    public MutableLiveData<LoginRetrofit> getLoginRetrofitMutableLiveData() {
        if( loginRetrofitMutableLiveData == null){
            loginRetrofitMutableLiveData = new MutableLiveData<>();
        }
        return loginRetrofitMutableLiveData;
    }

    public MutableLiveData<String> getSuceso(){
        if( suceso == null){
            suceso = new MutableLiveData<>();
        }
        return suceso;
    }


    public void login(String username, String password) {
        // can be launched in a separate asynchronous job

        Call<LoginRetrofit> request = ApiClient.getMyApiClient("Login VM").login(username, password);
                Log.d("mensaje", "Hizo el CALL desde LoginVM.login con " + username + " y " + password);

                request.enqueue(new Callback<LoginRetrofit>() {
                    @Override
                    public void onResponse(Call<LoginRetrofit> call, Response<LoginRetrofit> response) {
                        if (response.isSuccessful()) {
                            LoginRetrofit laRta = response.body();

                            String elToken = laRta.getToken();

                            ApiClient.guardar(context, elToken);
                            setPropietario();

                        } else
                        {
                            ApiClient.guardar(context, "");
                            Toast.makeText(context, "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                            suceso.postValue("Fallido");
                        }
                    }
                    @Override
                    public void onFailure(Call<LoginRetrofit> call, Throwable t) {
                        Toast.makeText(context, "Fallo en la comunicacion con la APi web", Toast.LENGTH_SHORT).show();
                        Log.d("mensaje: ", "Fallo en LoginVM/login"+t.getMessage());
                        suceso.postValue("Fallido");
                    }
                });
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 2;
    }

    public void setPropietario(){

        String token = ApiClient.leer(context, getClass().toString());

        if(token.length() > 10) {

            Call<Propietario> usuario = ApiClient.getMyApiClient("Login VM").obtenerUsuarioActual(token);
            usuario.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    //Log.d("mensaje", response.body().getId() + " " + response.body().getApellido());
                    propietario.setValue(response.body());
                    Log.d("mensaje/LoginVM/setProp", propietario.getValue().getEmail().toString());

                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable t) {
                    Log.d("mensaje/LogVM/onfailure", t.getMessage().toString());
                    Toast.makeText(context, "Fallo en buscar usuario actual" +t.getMessage() , Toast.LENGTH_SHORT).show();
                }
            });

        }
        else {
            Log.d("mensaje", "Quiso setear propietario con token "+ token);
        }



    }
}