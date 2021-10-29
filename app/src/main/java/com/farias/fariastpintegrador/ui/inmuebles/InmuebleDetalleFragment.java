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
import com.farias.fariastpintegrador.modelo.Inmueble;


public class InmuebleDetalleFragment extends Fragment {

    private InmuebleDetalleViewModel mViewModel;
    private FragmentInmuebleDetalleBinding binding;
    private Contrato c;
    private Inmueble i;

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


        mViewModel.getContrato().observe(getViewLifecycleOwner(), contrato ->  {
            {
                if (i != null){
                    c = contrato;
                    binding.BTDisponibilidad.setVisibility(c == null? 0 : 4);
                    binding.TVAlquilado.setText(c == null? "No alquilado" : "Alquilado(n° " + c.getIdContrato() +")" );
                    binding.TVDireccionDetalle.setText(i.getDireccion());
                    binding.TVPrecioDetalle.setText("$ " + i.getmontoAlquilerPropuesto()+".00");
                    binding.TVIdDetalle.setText(i.getIdInmueble()+"");
                    binding.TVAmbientesDetalle.setText(i.getAmbientes()+"");
                    binding.TVUsoDetalle.setText(i.getUso());
                    binding.TVTipoDetalle.setText(i.getTipo());
                    binding.TVDisponibilidadDetalle.setText(i.isEstado()? "Disponible" : "No disponible");
                    binding.BTDisponibilidad.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            Log.d("mensaje", "Click en cambiar disponibilidad");
                            mViewModel.cambiarDisponiblidad(i);
                        }
                    });
                    Glide.with(getContext())
                            .load(i.getUrlFoto())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)               // Llama la imagen remota y la carga en el cache,
                            .into(binding.IVFotoDetalle);                          // despues la busca de ahi y es mas rapido
                }
            }

        });

        mViewModel.getInmueble().observe(getViewLifecycleOwner(), inmueble ->  {
            i = inmueble;
            if (i!= null){

                Log.d("mensaje: InmDetalleVM", "El inmueble observado es " +i.toString());
                mViewModel.setContrato(i);
            }
        });

        mViewModel.setInmueble(getArguments());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}