package com.farias.fariastpintegrador.ui.contrato;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.farias.fariastpintegrador.modelo.Contrato;
import com.farias.fariastpintegrador.modelo.Inmueble;
import com.farias.fariastpintegrador.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoDetalleViewModel extends ViewModel {
    MutableLiveData<Contrato> contratoMutableLiveData;
    private ApiClient apiClient;
    private Context context;

    public ContratoDetalleViewModel(){
        this.contratoMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Contrato> getContratoMutableLiveData(){ return contratoMutableLiveData; }

    public void setContratoMutableLiveData(Bundle bundle) {
        Inmueble i = (Inmueble) bundle.getSerializable("inmueble");

        SharedPreferences sp = context.getSharedPreferences("Usuario",0);
        String token = sp.getString("token","sin token");

        Call<Contrato> contratoCall = ApiClient.getMyApiClient().obtenerContratoVigente(token, i);
        contratoCall.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                contratoMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {

            }
        });

//        apiClient = ApiClient.getApi();
//        Inmueble i = (Inmueble) bundle.getSerializable("inmueble");
//        contratoMutableLiveData.setValue(apiClient.obtenerContratoVigente(i));
    }
}