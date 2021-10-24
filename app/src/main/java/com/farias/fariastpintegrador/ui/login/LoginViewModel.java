package com.farias.fariastpintegrador.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Patterns;

import com.farias.fariastpintegrador.data.LoginRepository;
import com.farias.fariastpintegrador.data.Result;
import com.farias.fariastpintegrador.data.model.LoggedInUser;
import com.farias.fariastpintegrador.R;
import com.farias.fariastpintegrador.modelo.LoginRetrofit;
import com.farias.fariastpintegrador.modelo.Propietario;
import com.farias.fariastpintegrador.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private MutableLiveData<Propietario> propietario = new MutableLiveData<>();
    ApiClient apiClient;
    private Context context;
    private Context miContext;


    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;

    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password, Context elContexto) {
        // can be launched in a separate asynchronous job


        Call<LoginRetrofit> request = ApiClient.getMyApiClient().login(username, password);
                Log.d("mensaje", "Hizo el CALL desde LoginDataSource.traerToken con " + username + " y " + password);

                request.enqueue(new Callback<LoginRetrofit>() {
                    @Override
                    public void onResponse(Call<LoginRetrofit> call, Response<LoginRetrofit> response) {
                        LoginRetrofit laRta = response.body();

                        String elToken = laRta.getToken();

                        SharedPreferences sharedPreferences = elContexto.getSharedPreferences("Usuario", 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", "Bearer " + elToken);
                        Log.d("mensaje", "El token guardado " + elToken);
                        editor.commit();

                        Propietario esUsuario = laRta.getPropietario();

                        Result<LoggedInUser> result = new Result.Success<>(new LoggedInUser( esUsuario.getId()+"", esUsuario.getNombre() +" "+esUsuario.getApellido(),
                                esUsuario.getEmail(),
                                2));


                        if (result instanceof Result.Success) {
                            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
                            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));

                        } else {
                            loginResult.setValue(new LoginResult(R.string.login_failed));
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginRetrofit> call, Throwable t) {

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

    public MutableLiveData<Propietario> getPropietario() {
        if( propietario == null) {
            propietario = new MutableLiveData<>();
        }
        return propietario;
    }

    public void setPropietario(Context miContext){

        SharedPreferences sp = miContext.getSharedPreferences("Usuario",0);
        String token = sp.getString("token","sin token");

        Call<Propietario> usuario = ApiClient.getMyApiClient().obtenerUsuarioActual(token);
        usuario.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                //Log.d("mensaje", response.body().getId() + " " + response.body().getApellido());
                propietario.setValue(response.body());
                Log.d("mensaje viewmodel login", propietario.toString());

            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {

            }
        });

    }
}