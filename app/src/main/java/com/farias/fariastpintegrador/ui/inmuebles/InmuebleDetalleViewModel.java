package com.farias.fariastpintegrador.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.farias.fariastpintegrador.modelo.Contrato;
import com.farias.fariastpintegrador.modelo.Inmueble;
import com.farias.fariastpintegrador.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleDetalleViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> inmuebleViewModel ;
    private MutableLiveData<Contrato> contratoViewModel;
    private MutableLiveData<String> estado;
    private Context context;
    private Inmueble inmueble;
    private Contrato contrato;

    public InmuebleDetalleViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        this.inmuebleViewModel = new MutableLiveData<>();
        this.contratoViewModel = new MutableLiveData<>();
        this.estado = new MutableLiveData<>();
    }


//    public InmuebleDetalleViewModel() {
//        this.inmueble = new MutableLiveData<>();
//        this.contrato = new MutableLiveData<>();
////        this.visibleCambiar = new MutableLiveData<>();
//    }

    public MutableLiveData<Inmueble> getInmueble() {
        if(inmuebleViewModel == null){
            inmuebleViewModel = new MutableLiveData<>();
        }
        return inmuebleViewModel;
    }

    public MutableLiveData<Contrato> getContrato() {
        if(contratoViewModel == null){
            contratoViewModel = new MutableLiveData<>();
        }
        return contratoViewModel; }


    public MutableLiveData<String> getEstado() {
        return estado;
    }


    public void setInmueble(Bundle bundle) {

        inmueble = (Inmueble) bundle.getSerializable("inmueble");
        Log.d("mensaje: InmdetalleVM", " El bundle trae "  + inmueble.toString());
        inmuebleViewModel.setValue(inmueble);
    }

    public void setContrato(Inmueble i) {

        Inmueble elInmueble = new Inmueble(){};    // Todo: Porque?????
        elInmueble.setIdInmueble(i.getIdInmueble());

        String token = traerToken();

        Call<Contrato> contratoCall = ApiClient.getMyApiClient("Inmueble detalle VM").obtenerContratoVigente(token, elInmueble);
        contratoCall.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if(response.isSuccessful()){

                    contrato = (Contrato) response.body();
                    contratoViewModel.setValue(contrato);
                    Log.d("mensaje InmDetalleVM: " , "El contrato traido es " + contratoViewModel.toString());
                }

            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Log.d("mensaje viewmodel Inm" , "Error " + t.getMessage());


            }
        });



//        apiClient = ApiClient.getApi();
//        contrato.setValue(apiClient.obtenerContratoVigente(inmueble));
//        Log.d("mensaje viewmodel Inm" , contrato.toString());
    }

    public void cambiarDisponiblidad(Inmueble i) {

        Inmueble inmueble = new Inmueble(){};
        inmueble.setIdInmueble(i.getIdInmueble());

        String token = traerToken();

        Call<Inmueble> inmuebleCall = ApiClient.getMyApiClient("Inmueble detalle VM").actualizarInmueble(token,inmueble);
        inmuebleCall.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                Inmueble inmuebleModificado = (Inmueble) response.body();
                inmuebleViewModel.setValue(inmuebleModificado);
                Log.d("mensaje", "Estado de disponibilidad actualizado");
                Toast.makeText(context, "Se cambio la disponibilidad del inmueble", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {

            }
        });

    }

    private String traerToken() {

        String token = ApiClient.leer(context, getClass().toString());

        return token;
    }



}