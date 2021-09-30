package com.farias.fariastpintegrador.ui.contrato;

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
import com.farias.fariastpintegrador.ui.contrato.adapter.RecyclerAdapterContratos;

public class ContratoFragment extends Fragment {

    private ContratoViewModel model;
    private RecyclerView recyclerView;

    public static ContratoFragment newInstance() {
        return new ContratoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contrato,container,false);
        model = new ViewModelProvider(this).get(ContratoViewModel.class);

        recyclerView = view.findViewById(R.id.RV_Contratos);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);

        model.getInmuebles().observe( getViewLifecycleOwner(), inmuebles -> {
            RecyclerAdapterContratos mAdapter = new RecyclerAdapterContratos(getContext(), inmuebles);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(mAdapter);
        });

        model.setInmuebles();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}