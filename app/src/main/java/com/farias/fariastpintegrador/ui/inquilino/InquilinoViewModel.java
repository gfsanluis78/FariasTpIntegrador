package com.farias.fariastpintegrador.ui.inquilino;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.farias.fariastpintegrador.modelo.Contrato;
import com.farias.fariastpintegrador.modelo.Inmueble;
import com.farias.fariastpintegrador.modelo.Inquilino;
import com.farias.fariastpintegrador.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinoViewModel extends ViewModel {

    MutableLiveData<ArrayList<Inmueble>> inmuebles;     // Lista que inicializo en el constructor
    MutableLiveData<Inquilino> inquilino;
    ApiClient apiClient;                                // La tarea de traer la a la apiclient y traer la lista esta aca en el viewmodel
    Context context;

    public InquilinoViewModel() {
        this.inmuebles = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<Inmueble>> getInmuebles() {
        return inmuebles;
    }

    public MutableLiveData<Inquilino> getInquilino() {
        return inquilino;
    }

    public void setInmuebles() {

        String token = traerToken();
        Call<List<Inmueble>> callLista = ApiClient.getMyApiClient().obtenerPropiedadesAlquiladas(token);
        callLista.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                inmuebles.setValue((ArrayList<Inmueble>) response.body());
                Log.d("mensaje viewmodel", inmuebles.toString());
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {

            }
        });

//        apiClient = ApiClient.getApi();
//        inmuebles.setValue(apiClient.obtenerPropiedadesAlquiladas());
//        Log.d("mensaje viewmodel", inmuebles.toString());
    }

    public void setInquilino(Inmueble inmueble) {

        String token = traerToken();

        Call<Inquilino> callInquilino = ApiClient.getMyApiClient().obtenerInquilino(token, inmueble);
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
//        inquilino.setValue(apiClient.obtenerInquilino(inmueble));
//        Log.d("mensaje viewmodel", inquilino.toString());
    }

    private String traerToken() {
        SharedPreferences sp = context.getSharedPreferences("Usuario",0);
        String token = sp.getString("token","sin token");
        return token;
    }



}