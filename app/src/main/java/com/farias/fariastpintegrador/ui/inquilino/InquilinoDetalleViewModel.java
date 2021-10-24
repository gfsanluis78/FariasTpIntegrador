package com.farias.fariastpintegrador.ui.inquilino;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.farias.fariastpintegrador.modelo.Inmueble;
import com.farias.fariastpintegrador.modelo.Inquilino;
import com.farias.fariastpintegrador.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinoDetalleViewModel extends ViewModel {
    MutableLiveData<Inquilino> inquilino;
    private ApiClient apiClient;
    private Context context;

    public InquilinoDetalleViewModel() {
        this.inquilino = new MutableLiveData<>();
    }

    public MutableLiveData<Inquilino> getInquilino() {
          return inquilino;
    }

    public void setInquilino(Bundle bundle) {

        Inmueble i = (Inmueble) bundle.getSerializable("inmueble");

        SharedPreferences sp = context.getSharedPreferences("Usuario",0);
        String token = sp.getString("token","sin token");

        Call<Inquilino> callInquilino = ApiClient.getMyApiClient().obtenerInquilino(token, i);
        callInquilino.enqueue(new Callback<Inquilino>() {
            @Override
            public void onResponse(Call<Inquilino> call, Response<Inquilino> response) {
                inquilino.setValue(response.body());
                Log.d("mensaje viewmodel", inquilino.toString());
            }

            @Override
            public void onFailure(Call<Inquilino> call, Throwable t) {

            }
        });

//        apiClient = ApiClient.getApi();
//        Inmueble i = (Inmueble) bundle.getSerializable("inmueble");
//        inquilino.setValue(apiClient.obtenerInquilino(i));
    }
}