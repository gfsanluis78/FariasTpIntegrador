package com.farias.fariastpintegrador.ui.contrato;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.farias.fariastpintegrador.modelo.Inmueble;
import com.farias.fariastpintegrador.modelo.Inquilino;
import com.farias.fariastpintegrador.request.ApiClient;

import java.util.ArrayList;

public class ContratoViewModel extends ViewModel {
    MutableLiveData<ArrayList<Inmueble>> inmuebles;     // Lista que inicializo en el constructor
    MutableLiveData<Inquilino> inquilino;
    ApiClient apiClient;                                // La tarea de traer la a la apiclient y traer la lista esta aca en el viewmodel

    public ContratoViewModel() {
        this.inmuebles = new MutableLiveData<>();
    }
    public MutableLiveData<ArrayList<Inmueble>> getInmuebles() {
        return inmuebles;
    }

    public MutableLiveData<Inquilino> getInquilino() {
        return inquilino;
    }

    public void setInmuebles() {

        apiClient = ApiClient.getApi();
        inmuebles.setValue(apiClient.obtenerPropiedadesAlquiladas());
        Log.d("mensaje viewmodel", inmuebles.toString());
    }

    public void setInquilino(Inmueble inmueble) {
        apiClient = ApiClient.getApi();
        inquilino.setValue(apiClient.obtenerInquilino(inmueble));
        Log.d("mensaje viewmodel", inquilino.toString());
    }




}