package com.farias.fariastpintegrador.ui.inmuebles;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.farias.fariastpintegrador.modelo.Inmueble;
import com.farias.fariastpintegrador.request.ApiClient;
import com.google.android.gms.common.api.Api;

import java.util.ArrayList;

public class InmuebleViewModel extends ViewModel {  // La tarea de esta es traer la lista de inmuebles

    MutableLiveData<ArrayList<Inmueble>> inmuebles;     // Lista que inicializo en el constructor
    ApiClient apiClient;                                // La tarea de traer la a la apiclient y traer la lista esta aca en el viewmodel

    public InmuebleViewModel() {
        this.inmuebles = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<Inmueble>> getInmuebles() {
        return inmuebles;
    }

    public void setInmuebles() {

        apiClient = ApiClient.getApi();
        inmuebles.setValue(apiClient.obtnerPropiedades());
        Log.d("mensaje viewmodel", inmuebles.toString());
    }
}