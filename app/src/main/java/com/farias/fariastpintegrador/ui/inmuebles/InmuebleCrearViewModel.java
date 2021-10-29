package com.farias.fariastpintegrador.ui.inmuebles;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.app.Application;
import android.content.Context;

import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import androidx.lifecycle.MutableLiveData;

import com.farias.fariastpintegrador.modelo.Inmueble;
import com.farias.fariastpintegrador.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleCrearViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Inmueble> inmuebleMutableLiveData;
    private MutableLiveData<Button> buttonMutableLiveData;

    public InmuebleCrearViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }
    // TODO: Implement the ViewModel


    public MutableLiveData<Inmueble> getInmuebleMutableLiveData() {
        return inmuebleMutableLiveData;
    }

    public MutableLiveData<Button> getButtonMutableLiveData() {
        return buttonMutableLiveData;
    }

    public void setButtonMutableLiveData(MutableLiveData<Button> buttonMutableLiveData) {

    }

    public void guardarInmueble(Inmueble inmueble){
        String token = ApiClient.leer(context,"InmuebleCrearViewModel.guardarInmueble" );

        Call<Inmueble> inmuebleCall = ApiClient.getMyApiClient("InmuebleCrearViewModel.guardarInmueble").crearInmueble(token, inmueble);
        inmuebleCall.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if (response.isSuccessful()){
                    Inmueble i = (Inmueble) response.body();
                    Toast.makeText(context, "Nuevo inmueble creado con exito con el id "+ i.getIdInmueble() , Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "Fallo en InmuebleCrearViewModel.guardarInmueble: " + response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Toast.makeText(context, "Fallo en comunicacion con BD en InmuebleCrearViewModel.guardarInmueble: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });




    }
}