package com.farias.fariastpintegrador;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.farias.fariastpintegrador.modelo.Propietario;


    public class MainActivityViewModel extends ViewModel {

        private MutableLiveData<Propietario> propietario ;

        public MainActivityViewModel(){
            propietario =  new MutableLiveData<>();
        }

        public MutableLiveData<Propietario> getPropietario() {
            if(propietario == null){
                propietario =  new MutableLiveData<>();
            }
            return propietario;
        }

        public void actualizarPerfil(Propietario p){
            propietario.setValue(p);
        }


    }

