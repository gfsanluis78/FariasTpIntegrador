package com.farias.fariastpintegrador.data;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.farias.fariastpintegrador.modelo.LoginRetrofit;
import com.farias.fariastpintegrador.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Genaro Farias el 23/10/2021.
 * Estudiante de la ULP
 * gfsanluis78@gmail.com
 */

public class LoginDataSourceViewModel extends ViewModel {

    private MutableLiveData<LoginRetrofit> loginRetrofitMutableLiveData;

    public LiveData<LoginRetrofit> getLoginRetrofitMutableLiveData() {
        if(loginRetrofitMutableLiveData == null){
            loginRetrofitMutableLiveData = new MutableLiveData<>();
        }
        return loginRetrofitMutableLiveData;
    }

    public void login(String user, String pass){
        Call<LoginRetrofit> loginRetrofitCall = ApiClient.getMyApiClient().login(user, pass);

        loginRetrofitCall.enqueue(new Callback<LoginRetrofit>() {
            @Override
            public void onResponse(Call<LoginRetrofit> call, Response<LoginRetrofit> response) {
                loginRetrofitMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<LoginRetrofit> call, Throwable t) {
                Log.d("mensaje Error",t.getMessage());
            }
        });


    }
}
