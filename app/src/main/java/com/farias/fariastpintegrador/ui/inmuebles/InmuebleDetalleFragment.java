package com.farias.fariastpintegrador.ui.inmuebles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.farias.fariastpintegrador.R;
import com.farias.fariastpintegrador.databinding.FragmentInmuebleDetalleBinding;
import com.farias.fariastpintegrador.modelo.Inmueble;

public class InmuebleDetalleFragment extends Fragment {

    private InmuebleDetalleViewModel mViewModel;
    private FragmentInmuebleDetalleBinding binding;

    public static InmuebleDetalleFragment newInstance() {
        return new InmuebleDetalleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentInmuebleDetalleBinding.inflate(inflater,container, false);
        View view = binding.getRoot();
        mViewModel = new ViewModelProvider(this).get(InmuebleDetalleViewModel.class);

        mViewModel.getInmueble().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                binding.TVTituloDetalle.setText(inmueble.getDireccion() + " " + inmueble.getTipo());
                binding.TVPrecioDetalle.setText(inmueble.getPrecio()+"");
                Glide.with(getContext())
                        .load(inmueble.getImagen())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)               // Llama la imagen remota y la carga en el cache,
                        .into(binding.IVFotoDetalle);                          // despues la busca de ahi y es mas rapido

            }
        });

        mViewModel.setInmueble(getArguments());
        return inflater.inflate(R.layout.fragment_inmueble_detalle, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

}