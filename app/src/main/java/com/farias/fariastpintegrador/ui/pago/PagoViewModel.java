package com.farias.fariastpintegrador.ui.pago;

import android.widget.ArrayAdapter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.farias.fariastpintegrador.modelo.Contrato;
import com.farias.fariastpintegrador.modelo.Pago;
import com.farias.fariastpintegrador.request.ApiClient;

import java.util.ArrayList;

public class PagoViewModel extends ViewModel {

    MutableLiveData<ArrayList<Pago>> pagos;
    ApiClient apiClient;

    // Constructor
    public PagoViewModel(){ this.pagos = new MutableLiveData<>();}

    public MutableLiveData<ArrayList<Pago>> getPagos() {
        return pagos;
    }

    public void setPagos(Contrato contrato) {
        apiClient = ApiClient.getApi();
        pagos.setValue(apiClient.obtenerPagos(contrato));
        this.pagos = pagos;
    }
}