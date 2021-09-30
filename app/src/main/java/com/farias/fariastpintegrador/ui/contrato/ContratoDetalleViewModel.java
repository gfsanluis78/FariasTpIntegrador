package com.farias.fariastpintegrador.ui.contrato;

import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.farias.fariastpintegrador.modelo.Contrato;
import com.farias.fariastpintegrador.modelo.Inmueble;
import com.farias.fariastpintegrador.request.ApiClient;

public class ContratoDetalleViewModel extends ViewModel {
    MutableLiveData<Contrato> contratoMutableLiveData;
    private ApiClient apiClient;

    public ContratoDetalleViewModel(){
        this.contratoMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Contrato> getContratoMutableLiveData(){ return contratoMutableLiveData; }

    public void setContratoMutableLiveData(Bundle bundle) {
        apiClient = ApiClient.getApi();
        Inmueble i = (Inmueble) bundle.getSerializable("inmueble");
        contratoMutableLiveData.setValue(apiClient.obtenerContratoVigente(i));
    }
}