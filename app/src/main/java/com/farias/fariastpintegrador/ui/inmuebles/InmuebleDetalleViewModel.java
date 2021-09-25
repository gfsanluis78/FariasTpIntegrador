package com.farias.fariastpintegrador.ui.inmuebles;

import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.farias.fariastpintegrador.modelo.Inmueble;

public class InmuebleDetalleViewModel extends ViewModel {
    MutableLiveData<Inmueble> inmueble ;

    public InmuebleDetalleViewModel(MutableLiveData<Inmueble> inmueble) {
        this.inmueble = new MutableLiveData<>();
    }

    public MutableLiveData<Inmueble> getInmueble() {
        return inmueble;
    }

    public void setInmueble(Bundle bundle) {
        Inmueble i = (Inmueble) bundle.getSerializable("inmueble");
        inmueble.setValue(i);
    }
}