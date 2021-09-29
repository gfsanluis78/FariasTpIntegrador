package com.farias.fariastpintegrador.ui.inquilino;

import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.farias.fariastpintegrador.modelo.Inmueble;
import com.farias.fariastpintegrador.modelo.Inquilino;
import com.farias.fariastpintegrador.request.ApiClient;

public class InquilinoDetalleViewModel extends ViewModel {
    MutableLiveData<Inquilino> inquilino;
    private ApiClient apiClient;

    public InquilinoDetalleViewModel() {
        this.inquilino = new MutableLiveData<>();
    }

    public MutableLiveData<Inquilino> getInquilino() {
          return inquilino;
    }

    public void setInquilino(Bundle bundle) {
        apiClient = ApiClient.getApi();
        Inmueble i = (Inmueble) bundle.getSerializable("inmueble");
        inquilino.setValue(apiClient.obtenerInquilino(i));
    }
}