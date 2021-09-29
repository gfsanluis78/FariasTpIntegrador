package com.farias.fariastpintegrador.ui.inquilino;

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
import com.farias.fariastpintegrador.ui.inmuebles.InmuebleViewModel;
import com.farias.fariastpintegrador.ui.inmuebles.adapter.RecyclerAdapterInmuebles;
import com.farias.fariastpintegrador.ui.inquilino.adapter.RecyclerAdapterInquilinos;

public class InquilinoFragment extends Fragment {

    private RecyclerView recyclerView;
    private InquilinoViewModel model;

    public static InquilinoFragment newInstance() {
        return new InquilinoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inquilino, container, false);
        model = new ViewModelProvider(this).get(InquilinoViewModel.class);

        // Inicializo el recycler view
        recyclerView = view.findViewById(R.id.RV_Inquilinos);    // Asi llamo a rv desde fragmnet

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),       // cambie el getContext por el getActivity y lo volvi atras
                LinearLayoutManager.VERTICAL,
                false);


        model.getInmuebles().observe( getViewLifecycleOwner(), inmuebles -> {               // cambie el this.getActivity por un getViewLifeycleOwner porqu es fragment
            {
                RecyclerAdapterInquilinos mAdapter = new RecyclerAdapterInquilinos(getContext(),inmuebles);
                recyclerView.setLayoutManager((linearLayoutManager));
                recyclerView.setAdapter(mAdapter);
            }
        });

        model.setInmuebles();

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}