package com.farias.fariastpintegrador.ui.inmuebles;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.farias.fariastpintegrador.modelo.Inmueble;
import com.farias.fariastpintegrador.request.ApiClient;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleViewModel extends ViewModel {  // La tarea de esta es traer la lista de inmuebles

    MutableLiveData<ArrayList<Inmueble>> inmuebles;     // Lista que inicializo en el constructor
    ApiClient apiClient;                                // La tarea de traer la a la apiclient y traer la lista esta aca en el viewmodel
    Context context;

    public InmuebleViewModel() {
        this.inmuebles = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<Inmueble>> getInmuebles() {
        return inmuebles;
    }


    public void setInmuebles() {

        SharedPreferences sp = context.getSharedPreferences("Usuario",0);
        String token = sp.getString("token","sin token");

        Call<List<Inmueble>> callInmuebles = ApiClient.getMyApiClient().obtnerPropiedades(token);
        callInmuebles.enqueue(new Callback<List<Inmueble>>() {
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
//        inmuebles.setValue(apiClient.obtnerPropiedades());
//        Log.d("mensaje viewmodel", inmuebles.toString());

    }
}