package com.farias.fariastpintegrador.ui.inmuebles;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.farias.fariastpintegrador.databinding.FragmentInmuebleDetalleBinding;


public class InmuebleDetalleFragment extends Fragment {

    private InmuebleDetalleViewModel mViewModel;
    private FragmentInmuebleDetalleBinding binding;

    public static InmuebleDetalleFragment newInstance() {
        return new InmuebleDetalleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(InmuebleDetalleViewModel.class);

        binding = FragmentInmuebleDetalleBinding.inflate(inflater,container, false);
        View view = binding.getRoot();

        Log.d("mensaje", "El binding trae "+binding.TVPrecioDetalle);


        mViewModel.getInmueble().observe(getViewLifecycleOwner(), inmueble ->  {
                binding.TVDireccionDetalle.setText(inmueble.getDireccion());
                binding.TVPrecioDetalle.setText(inmueble.getPrecio()+"");
                binding.TVIdDetalle.setText(inmueble.getIdInmueble()+"");
                binding.TVAmbientesDetalle.setText(inmueble.getAmbientes()+"");
                binding.TVUsoDetalle.setText(inmueble.getUso());
                binding.TVTipoDetalle.setText(inmueble.getTipo());
                binding.TVDisponibilidadDetalle.setText(inmueble.isEstado()? "Disponible" : "No disponible");
                Glide.with(getContext())
                        .load(inmueble.getImagen())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)               // Llama la imagen remota y la carga en el cache,
                        .into(binding.IVFotoDetalle);                          // despues la busca de ahi y es mas rapido
        });

        mViewModel.setInmueble(getArguments());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}