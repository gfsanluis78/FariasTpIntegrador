package com.farias.fariastpintegrador.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.farias.fariastpintegrador.modelo.Inmueble;
import com.farias.fariastpintegrador.request.ApiClient;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleViewModel extends AndroidViewModel {  // La tarea de esta es traer la lista de inmuebles

    MutableLiveData<ArrayList<Inmueble>> inmuebles;     // Lista que inicializo en el constructor
    Context context;

    public InmuebleViewModel(@NonNull Application application) {
        super(application);
        this.inmuebles = new MutableLiveData<>();
        this.context = application.getApplicationContext();
    }


    public MutableLiveData<ArrayList<Inmueble>> getInmuebles() {
        return inmuebles;
    }


    public void setInmuebles() {


         String token = ApiClient.leer(context, getClass().toString());

        Call<List<Inmueble>> callInmuebles = ApiClient.getMyApiClient("Inmueble VM").obtnerPropiedades(token);
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