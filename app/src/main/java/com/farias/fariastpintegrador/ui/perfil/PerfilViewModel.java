package com.farias.fariastpintegrador.ui.perfil;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.farias.fariastpintegrador.modelo.Propietario;
import com.farias.fariastpintegrador.request.ApiClient;

public class PerfilViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<String> mText;

    private MutableLiveData<Propietario> usuario;   // Se declaran los mutables de cada elemento del fragment para poder...
    private MutableLiveData<Integer> visibleEditar; // accederlos desde la vista
    private MutableLiveData<Integer> visibleGuardar;
    private MutableLiveData<Boolean> estadoEditable;
    private MutableLiveData<Boolean> estadoNoEditable;

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
        ApiClient api = ApiClient.getApi();
        Propietario p = api.obtenerUsuarioActual();
        usuario.setValue(p);
    }

    public void modificarDatos(Propietario p){      // del lado de la activity saco los datos del propietario y los mando a este metodo
        ApiClient api = ApiClient.getApi();         //
        api.actualizarPerfil(p);
        //usuario.setValue(p);
        visibleGuardar.setValue((View.INVISIBLE));
        this.cambiarEstadoNoEditable();
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

}