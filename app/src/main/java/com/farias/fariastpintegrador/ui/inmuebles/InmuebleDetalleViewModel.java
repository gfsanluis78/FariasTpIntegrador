package com.farias.fariastpintegrador.ui.inmuebles;

import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.farias.fariastpintegrador.modelo.Contrato;
import com.farias.fariastpintegrador.modelo.Inmueble;
import com.farias.fariastpintegrador.request.ApiClient;

public class InmuebleDetalleViewModel extends ViewModel {
    private MutableLiveData<Inmueble> inmueble ;
    private MutableLiveData<Contrato> contrato;
    //private MutableLiveData<Integer> visibleCambiar;


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
        apiClient = ApiClient.getApi();
        contrato.setValue(apiClient.obtenerContratoVigente(inmueble));
        Log.d("mensaje viewmodel Inm" , contrato.toString());
    }

    public void cambiarDisponiblidad(Inmueble i) {
        apiClient = ApiClient.getApi();
        apiClient.actualizarInmueble(i);
    }


//    public void setVisibleCambiar(){
//        if (visibleCambiar == null) {
//            visibleCambiar = new MutableLiveData<>();
//        }
//    }




}