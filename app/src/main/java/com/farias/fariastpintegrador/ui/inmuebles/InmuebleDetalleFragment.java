package com.farias.fariastpintegrador.ui.inmuebles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
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
import com.farias.fariastpintegrador.databinding.FragmentContratoDetalleBinding;
import com.farias.fariastpintegrador.databinding.FragmentInmuebleDetalleBinding;
import com.farias.fariastpintegrador.modelo.Contrato;


public class InmuebleDetalleFragment extends Fragment {

    private InmuebleDetalleViewModel mViewModel;
    private FragmentInmuebleDetalleBinding binding;
    private Contrato c;

    public static InmuebleDetalleFragment newInstance() {
        return new InmuebleDetalleFragment();
    }

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(InmuebleDetalleViewModel.class);

        binding = FragmentInmuebleDetalleBinding.inflate(inflater,container, false);
        View view = binding.getRoot();

        Log.d("mensaje", "El binding trae "+binding.TVPrecioDetalle);


        mViewModel.getInmueble().observe(getViewLifecycleOwner(), inmueble ->  {

                mViewModel.setContrato(inmueble);
                mViewModel.getContrato().observe(getViewLifecycleOwner(), contrato ->  {
                   {
                       c = contrato;
                    }
                });

                binding.TVDireccionDetalle.setText(inmueble.getDireccion());
                binding.TVPrecioDetalle.setText(inmueble.getPrecio()+"");
                binding.TVIdDetalle.setText(inmueble.getIdInmueble()+"");
                binding.TVAmbientesDetalle.setText(inmueble.getAmbientes()+"");
                binding.TVUsoDetalle.setText(inmueble.getUso());
                binding.TVTipoDetalle.setText(inmueble.getTipo());
                binding.TVDisponibilidadDetalle.setText(inmueble.isEstado()? "Disponible" : "No disponible");
                binding.TVAlquilado.setText(c == null? "No alquilado" : "Alquilado(n° "+c.getIdContrato()+")" );
                binding.BTDisponibilidad.setVisibility(c == null? 0 : 4);
                binding.BTDisponibilidad.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Log.d("mensaje", "Click en cambiar disponibilidad de " + inmueble.isEstado() );
                        inmueble.setEstado(inmueble.isEstado() == true? false: true);
                        Log.d("mensaje", "a disponibilidad " + inmueble.isEstado() );
                        mViewModel.cambiarDisponiblidad(inmueble);
                        mViewModel.setInmueble(getArguments());
                    }
                });
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