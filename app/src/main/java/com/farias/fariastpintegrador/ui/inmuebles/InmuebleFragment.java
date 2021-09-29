package com.farias.fariastpintegrador.ui.inmuebles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.farias.fariastpintegrador.R;
import com.farias.fariastpintegrador.ui.inmuebles.adapter.RecyclerAdapterInmuebles;

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
        model = new ViewModelProvider(this).get(InmuebleViewModel.class);

        // Inicializo el recycler view
        recyclerView = view.findViewById(R.id.RV_Inmuebles);    // Asi llamo a rv desde fragmnet

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),       // cambie el getContext por el getActivity y lo volvi atras
                LinearLayoutManager.VERTICAL,
                false);


        model.getInmuebles().observe( getViewLifecycleOwner(), inmuebles -> {               // cambie el this.getActivity por un getViewLifeycleOwner porqu es fragment
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