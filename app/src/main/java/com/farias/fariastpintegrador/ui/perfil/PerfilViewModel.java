package com.farias.fariastpintegrador.ui.perfil;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.farias.fariastpintegrador.modelo.Propietario;
import com.farias.fariastpintegrador.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    private MutableLiveData<Propietario> usuario;   // Se declaran los mutables de cada elemento del fragment para poder...

    private MutableLiveData<Integer> visibleEditar; // accederlos desde la vista
    private MutableLiveData<Integer> visibleGuardar;
    private MutableLiveData<Boolean> estadoEditable;
    private MutableLiveData<Boolean> estadoNoEditable;

    private Context context;

    public PerfilViewModel (){
        usuario =  new MutableLiveData<>();
        visibleGuardar =  new MutableLiveData<>();
        estadoEditable =  new MutableLiveData<>();
        estadoNoEditable =  new MutableLiveData<>();
    }

    public LiveData<Propietario> getUsuario() {

        if (usuario == null){
            usuario = new MutableLiveData<>();
        }

        return usuario;
    }

    public MutableLiveData<Integer> getVisibleEditar() {

        if(visibleEditar == null){
            visibleEditar = new MutableLiveData<>();
        }

        return visibleEditar;
    }

    public MutableLiveData<Integer> getVisibleGuardar() {

        if (visibleGuardar == null){
            visibleGuardar = new MutableLiveData<>();
        }

        return visibleGuardar;
    }

    public MutableLiveData<Boolean> getEstadoEditable() {

        if (estadoEditable == null) {
            estadoEditable = new MutableLiveData<>();
        }

        return estadoEditable;
    }

    public MutableLiveData<Boolean> getEstadoNoEditable() {
        if(estadoNoEditable == null) {
            estadoNoEditable = new MutableLiveData<>();
        }

        return estadoNoEditable;
    }

    public void obtenerUsuario() {                  // metodo para recrear al inicio de la vista al usuario actual. Usa el mutable  usuario

        String token = elToken();

        Call<Propietario> callActual = ApiClient.getMyApiClient().obtenerUsuarioActual(token);
        callActual.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                usuario.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {

            }
        });

    }

    public void modificarDatos(Propietario p){      // del lado de la activity saco los datos del propietario y los mando a este metodo

        String token = elToken();

        Call<Propietario> callMod = ApiClient.getMyApiClient().actualizarPerfil(token, p);
        callMod.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                visibleGuardar.setValue((View.INVISIBLE));
                PerfilViewModel.this.cambiarEstadoNoEditable();
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {

            }
        });
    }

    public void cambiarEstadoEditable(){                    // Uso el mutable estadoEditable para cambiar los estados de los mutables vedi y v guardar
        estadoEditable.setValue(true);
        visibleEditar.setValue((View.VISIBLE));
        visibleGuardar.setValue(View.VISIBLE);
    }

    public void cambiarEstadoNoEditable(){                    // Uso el mutable estadoEditable para cambiar los estados de los mutables vedi y v guardar
        estadoEditable.setValue(false);
        visibleEditar.setValue((View.VISIBLE));
        visibleGuardar.setValue(View.INVISIBLE);
    }

    private String elToken(){
        SharedPreferences sp = context.getSharedPreferences("Usuario",0);
        String token = sp.getString("token","sin token");
        return token;
    }

}