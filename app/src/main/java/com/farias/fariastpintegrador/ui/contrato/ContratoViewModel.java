package com.farias.fariastpintegrador.ui.contrato;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.google.android.gms.common.AccountPicker;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoViewModel extends AndroidViewModel {
    MutableLiveData<ArrayList<Inmueble>> inmuebles;     // Lista que inicializo en el constructor
    MutableLiveData<Inquilino> inquilino;
                     // La tarea de traer la a la apiclient y traer la lista esta aca en el viewmodel
    Context context;

    public ContratoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        this.inmuebles = new MutableLiveData<>();
    }


//    public ContratoViewModel() {
//        this.inmuebles = new MutableLiveData<>();
//    }



    public MutableLiveData<ArrayList<Inmueble>> getInmuebles() {
        return inmuebles;
    }

    public MutableLiveData<Inquilino> getInquilino() {
        return inquilino;
    }

    public void setInmuebles() {
        String token = traerToken();

        Call<List<Inmueble>> callPropALquiladas = ApiClient.getMyApiClient("Contrato VM").obtenerPropiedadesAlquiladas(token);
        callPropALquiladas.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                ArrayList<Inmueble> list = (ArrayList) response.body();

                if (list.isEmpty()){
                    Toast.makeText(context, "No hay Contratos para mostrar", Toast.LENGTH_SHORT).show();
                }
                inmuebles.setValue(list);
                Log.d("mensaje viewmodel", inmuebles.toString());
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {

            }
        });


//        apiClient = ApiClient.getApi();
//        inmuebles.setValue(apiClient.obtenerPropiedadesAlquiladas());
//        Log.d("mensaje viewmodel", inmuebles.toString());
    }


    public void setInquilino(Inmueble inmueble) {
        String token = traerToken();

        Call<List<Inquilino>> callInquilino = ApiClient.getMyApiClient("Contrato VM").obtenerInquilino(token, inmueble);
        callInquilino.enqueue(new Callback<List<Inquilino>>() {
            @Override
            public void onResponse(Call<List<Inquilino>> call, Response<List<Inquilino>> response) {
                if(response.isSuccessful()){
                    ArrayList<Inquilino> list = (ArrayList) response.body();
                    inquilino.setValue(list.get(0));
                    Log.d("mensaje viewmodel", inquilino.toString());
                }

            }

            @Override
            public void onFailure(Call<List<Inquilino>> call, Throwable t) {

            }
        });

//        apiClient = ApiClient.getApi();
//        inquilino.setValue(apiClient.obtenerInquilino(inmueble));
//        Log.d("mensaje viewmodel", inquilino.toString());
    }

    private String traerToken() {
              String token = ApiClient.leer(context, "Contrato VM");
        return token;
    }




}