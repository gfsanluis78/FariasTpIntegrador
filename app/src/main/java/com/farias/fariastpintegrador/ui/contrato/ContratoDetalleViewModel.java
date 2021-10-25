package com.farias.fariastpintegrador.ui.contrato;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.farias.fariastpintegrador.modelo.Contrato;
import com.farias.fariastpintegrador.modelo.Inmueble;
import com.farias.fariastpintegrador.request.ApiClient;
import com.google.android.gms.common.api.Api;

import java.util.ArrayList;
import java.util.List;

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

        String token = ApiClient.leer(context, "Contrato detalle VM");

        Call<Contrato> contratoCall = ApiClient.getMyApiClient("Contrato detalle VM").obtenerContratoVigente(token, i);
        contratoCall.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if(response.isSuccessful()){

                    contratoMutableLiveData.setValue((Contrato)response.body());

                }

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