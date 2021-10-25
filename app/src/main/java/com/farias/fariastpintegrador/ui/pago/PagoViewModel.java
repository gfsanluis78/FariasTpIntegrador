package com.farias.fariastpintegrador.ui.pago;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
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

public class PagoViewModel extends AndroidViewModel {

    MutableLiveData<ArrayList<Pago>> pagos;
    ApiClient apiClient;
    Context context;


    // Constructor
    public PagoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        this.pagos = new MutableLiveData<>();
    }


    public MutableLiveData<ArrayList<Pago>> getPagos() {
        return pagos;
    }

    public void setPagos(Contrato contrato) {

        String token = ApiClient.leer(context, "Pago VM");
        Log.d("mensanje", "Se buscaran los pagos de " + contrato.toString());

        Contrato c = new Contrato(){};
        c.setIdContrato(contrato.getIdContrato());

        Call<List<Pago>> callPagos = ApiClient.getMyApiClient("Pago VM").obtenerPagos(token, c);
        callPagos.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if(!response.isSuccessful()){
                    Log.d("mensanje", "No se pudieron traer los pagos");
                }
                else {
                    Log.d("mensanje", "Se trajeron los pagos");
                    ArrayList<Pago> list = (ArrayList) response.body();
                    pagos.setValue(list);
                    if(list.isEmpty()){
                        Toast.makeText(context, "No hay pagos para mostrar", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                Log.d("mensaje", "Fallo en pago view model " + t.getMessage());
            }
        });


//        apiClient = ApiClient.getApi();
//        pagos.setValue(apiClient.obtenerPagos(contrato));
//        this.pagos = pagos;
    }
}