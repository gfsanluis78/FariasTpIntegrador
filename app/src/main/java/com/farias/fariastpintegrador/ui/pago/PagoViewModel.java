package com.farias.fariastpintegrador.ui.pago;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.farias.fariastpintegrador.modelo.Contrato;
import com.farias.fariastpintegrador.modelo.Pago;
import com.farias.fariastpintegrador.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagoViewModel extends ViewModel {

    MutableLiveData<ArrayList<Pago>> pagos;
    ApiClient apiClient;
    Context context;

    // Constructor
    public PagoViewModel(){ this.pagos = new MutableLiveData<>();}

    public MutableLiveData<ArrayList<Pago>> getPagos() {
        return pagos;
    }

    public void setPagos(Contrato contrato) {

        SharedPreferences sp = context.getSharedPreferences("Usuario",0);
        String token = sp.getString("token","sin token");

        Call<List<Pago>> callPagos = ApiClient.getMyApiClient().obtenerPagos(token, contrato);
        callPagos.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                pagos.setValue((ArrayList<Pago>) response.body());
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {

            }
        });


//        apiClient = ApiClient.getApi();
//        pagos.setValue(apiClient.obtenerPagos(contrato));
//        this.pagos = pagos;
    }
}