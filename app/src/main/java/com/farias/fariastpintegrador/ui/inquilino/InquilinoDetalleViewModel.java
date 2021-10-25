package com.farias.fariastpintegrador.ui.inquilino;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.farias.fariastpintegrador.modelo.Contrato;
import com.farias.fariastpintegrador.modelo.Inmueble;
import com.farias.fariastpintegrador.modelo.Inquilino;
import com.farias.fariastpintegrador.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinoDetalleViewModel extends AndroidViewModel {
    MutableLiveData<Contrato> inquilino;
    private Context context;

    public InquilinoDetalleViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        this.inquilino = new MutableLiveData<>();
    }


    public MutableLiveData<Contrato> getInquilino() {
        if (inquilino == null){
            inquilino = new MutableLiveData<>();
        }
          return inquilino;
    }

    public void setInquilino(Bundle bundle) {

        Inmueble i = (Inmueble) bundle.getSerializable("inmueble");

        Log.d("mensaje", "El inmueble es" + i.getIdInmueble() + " " +
                i.getPropietario() + " " +
                " " + i.getmontoAlquilerPropuesto());

        String token = ApiClient.leer(context, "Inqulino detalle VM");

        Call<Contrato> callInquilino = ApiClient.getMyApiClient("Inquilino detalle VM").obtenerContratoVigente(token, i);
        callInquilino.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if(response.isSuccessful()){

                    if(response.body() != null){
                        inquilino.setValue(response.body());
                        Log.d("mensaje viewmodel", inquilino.toString());
                    }

                }

            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Toast.makeText(context, "Fallo en buscar inquilino", Toast.LENGTH_SHORT).show();
            }
        });

//        apiClient = ApiClient.getApi();
//        Inmueble i = (Inmueble) bundle.getSerializable("inmueble");
//        inquilino.setValue(apiClient.obtenerInquilino(i));
    }
}