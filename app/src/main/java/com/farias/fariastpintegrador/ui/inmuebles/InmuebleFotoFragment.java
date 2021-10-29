package com.farias.fariastpintegrador.ui.inmuebles;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farias.fariastpintegrador.R;

public class InmuebleFotoFragment extends Fragment {

    private InmuebleFotoViewModel mViewModel;

    public static InmuebleFotoFragment newInstance() {
        return new InmuebleFotoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inmueble_foto, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InmuebleFotoViewModel.class);
        // TODO: Use the ViewModel
    }

}