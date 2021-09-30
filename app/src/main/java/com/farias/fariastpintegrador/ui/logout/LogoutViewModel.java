package com.farias.fariastpintegrador.ui.logout;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LogoutViewModel extends ViewModel {
    private MutableLiveData<Boolean> logout;

    public MutableLiveData<Boolean> getLogout() {
        return logout;
    }
}