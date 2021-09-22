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

    private MutableLiveData<Propietario> usuario;
    private MutableLiveData<Integer> visibleEditar;
    private MutableLiveData<Integer> visibleGuardar;
    private MutableLiveData<Boolean> estadoEditable;

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

    public void ObtenerUsuario() {
        ApiClient api = ApiClient.getApi();
        Propietario p = api.obtenerUsuarioActual();
        usuario.setValue(p);
    }

    public void ModificarDatos(Propietario p){
        ApiClient api = ApiClient.getApi();
        api.actualizarPerfil(p);
        visibleEditar.setValue((View.VISIBLE));
        visibleGuardar.setValue((View.INVISIBLE));
    }

    public void cambiarEstado(){
        estadoEditable.setValue(true);
        visibleEditar.setValue((View.VISIBLE));
        visibleGuardar.setValue(View.VISIBLE);
    }

    public PerfilViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is perfil fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}