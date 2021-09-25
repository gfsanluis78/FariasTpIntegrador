package com.farias.fariastpintegrador.ui.inmuebles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farias.fariastpintegrador.R;
import com.farias.fariastpintegrador.databinding.FragmentInmuebleBinding;
import com.farias.fariastpintegrador.modelo.Inmueble;
import com.farias.fariastpintegrador.ui.inmuebles.adapter.RecyclerAdapterInmuebles;

import java.util.ArrayList;

public class InmuebleFragment extends Fragment {

    private RecyclerView recyclerView;
    private InmuebleViewModel model;
    //private FragmentInmuebleBinding binding;

    public static InmuebleFragment newInstance() {

        return new InmuebleFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inmueble, container, false);

        // Inicializo el recycler view
        recyclerView = view.findViewById(R.id.RV_Inmuebles);    // Asi llamo a rv desde fragmnet

        LinearLayoutManager linearLayoutManager;
        linearLayoutManager = new LinearLayoutManager( getActivity(),       // cambie el getContext por el getActivity
                LinearLayoutManager.VERTICAL,
                false);


        model = new ViewModelProvider(this).get(InmuebleViewModel.class);
        model.getInmuebles().observe( this.getActivity(), inmuebles -> {
          {
                RecyclerAdapterInmuebles mAdapter = new RecyclerAdapterInmuebles(getContext(),inmuebles);
                recyclerView.setLayoutManager((linearLayoutManager));
                recyclerView.setAdapter(mAdapter);
            }
        });

        model.setInmuebles();

        return view;
    }



}