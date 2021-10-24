package com.farias.fariastpintegrador.ui.inmuebles;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.farias.fariastpintegrador.modelo.Contrato;
import com.farias.fariastpintegrador.modelo.Inmueble;
import com.farias.fariastpintegrador.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleDetalleViewModel extends ViewModel {
    private MutableLiveData<Inmueble> inmueble ;
    private MutableLiveData<Contrato> contrato;
    private Context context;


    ApiClient apiClient;

    public InmuebleDetalleViewModel() {
        this.inmueble = new MutableLiveData<>();
        this.contrato = new MutableLiveData<>();
//        this.visibleCambiar = new MutableLiveData<>();
    }

    public MutableLiveData<Inmueble> getInmueble() {
        return inmueble;
    }

    public MutableLiveData<Contrato> getContrato() { return contrato; }

//    public MutableLiveData<Integer> getVisibleCambiar() { return visibleCambiar; }


    public void setInmueble(Bundle bundle) {
        Inmueble i = (Inmueble) bundle.getSerializable("inmueble");
        inmueble.setValue(i);
    }

    public void setContrato(Inmueble inmueble) {

        String token = traerToken();

        Call<Contrato> contratoCall = ApiClient.getMyApiClient().obtenerContratoVigente(token, inmueble);
        contratoCall.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                contrato.setValue(response.body());
                Log.d("mensaje viewmodel Inm" , contrato.toString());
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {

            }
        });



//        apiClient = ApiClient.getApi();
//        contrato.setValue(apiClient.obtenerContratoVigente(inmueble));
//        Log.d("mensaje viewmodel Inm" , contrato.toString());
    }

    public void cambiarDisponiblidad(Inmueble i) {
        String token = traerToken();

        Call<Inmueble> inmuebleCall = ApiClient.getMyApiClient().actualizarInmueble(token,i);
        inmuebleCall.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                Log.d("mensaje", "Estado de disponibilidad actualizado");
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {

            }
        });

//        apiClient = ApiClient.getApi();
//        apiClient.actualizarInmueble(i);
    }

    private String traerToken() {
        SharedPreferences sp = context.getSharedPreferences("Usuario",0);
        String token = sp.getString("token","sin token");
        return token;
    }



}