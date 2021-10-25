package com.farias.fariastpintegrador.ui.inquilino;

import android.app.Application;
import android.content.Context;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.farias.fariastpintegrador.modelo.Contrato;
import com.farias.fariastpintegrador.modelo.Inmueble;
import com.farias.fariastpintegrador.modelo.Inquilino;
import com.farias.fariastpintegrador.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinoViewModel extends AndroidViewModel {

    MutableLiveData<ArrayList<Contrato>> inmuebles;     // Lista que inicializo en el constructor
    MutableLiveData<Contrato> inquilino;
    Context context;

    public InquilinoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        this.inmuebles = new MutableLiveData<>();
    }


    public MutableLiveData<ArrayList<Contrato>> getInmuebles() {
        return inmuebles;
    }

    public MutableLiveData<Contrato> getInquilino() {
        return inquilino;
    }

    public void setInmuebles() {

        String token = traerToken();
        Call<List<Contrato>> callLista = ApiClient.getMyApiClient("Inquilino VM").obtenerContratosVigentes(token);
        callLista.enqueue(new Callback<List<Contrato>>() {
            @Override
            public void onResponse(Call<List<Contrato>> call, Response<List<Contrato>> response) {
                if (response.isSuccessful()){
                    Log.d("mensaje viewmodel", "llego devolucion de request");

                    ArrayList<Contrato> list = (ArrayList) response.body();

                    if (list.isEmpty()){
                        Toast.makeText(context, "No hay Inquilinos para mostrar", Toast.LENGTH_SHORT).show();
                    }
                    inmuebles.postValue(list);
                    Log.d("mensaje viewmodel", inmuebles.toString());
                }
             }

            @Override
            public void onFailure(Call<List<Contrato>> call, Throwable t) {
                    Log.d("mensaje ", "Fallo en inquilino view model " + t.getMessage() );
            }
        });

//        apiClient = ApiClient.getApi();
//        inmuebles.setValue(apiClient.obtenerPropiedadesAlquiladas());
//        Log.d("mensaje viewmodel", inmuebles.toString());
    }

    public void setInquilino(Inmueble inmueble) {

        String token = traerToken();

        Call<Contrato> callInquilino = ApiClient.getMyApiClient("Inquilino VM").obtenerContratoVigente(token, inmueble);
        callInquilino.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                        if (response.isSuccessful()){
                            inquilino.setValue(response.body());
                            Log.d("mensaje viewmodel", inquilino.toString());
                        }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {

            }
        });

//        apiClient = ApiClient.getApi();
//        inquilino.setValue(apiClient.obtenerInquilino(inmueble));
//        Log.d("mensaje viewmodel", inquilino.toString());
    }

    private String traerToken() {

        String token = ApiClient.leer(context, getClass().toString());
        return token;
    }



}