package com.farias.fariastpintegrador;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.farias.fariastpintegrador.modelo.Propietario;


    public class MainActivityViewModel extends AndroidViewModel {

        private MutableLiveData<Propietario> propietario ;
        private Context context;

        public MainActivityViewModel(@NonNull Application application) {
            super(application);
            propietario =  new MutableLiveData<>();
        }

//        public MainActivityViewModel@Nullable Application aplication){
//            super(aplication);
//            propietario =  new MutableLiveData<>();
//        }

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

