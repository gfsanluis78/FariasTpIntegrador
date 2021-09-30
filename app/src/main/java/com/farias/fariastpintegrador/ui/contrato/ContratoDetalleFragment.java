package com.farias.fariastpintegrador.ui.contrato;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farias.fariastpintegrador.R;
import com.farias.fariastpintegrador.databinding.FragmentContratoDetalleBinding;

public class ContratoDetalleFragment extends Fragment {

    private ContratoDetalleViewModel model;
    private FragmentContratoDetalleBinding binding;

    public static ContratoDetalleFragment newInstance() {
        return new ContratoDetalleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        model = new ViewModelProvider(this).get(ContratoDetalleViewModel.class);

        binding = FragmentContratoDetalleBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        Log.d("mensaje", "El binding contrato trae ");

        model.getContratoMutableLiveData().observe(getViewLifecycleOwner(), contrato -> {
            binding.ETIdContrato.setText(contrato.getIdContrato()+"");
            binding.ETFechaInicioContrato.setText(contrato.getFechaInicio());
            binding.ETFechaFinalizacionContrato.setText(contrato.getFechaFin());
            binding.ETMontoAlquilerContrato.setText("$ " +contrato.getMontoAlquiler());
            binding.ETInquilinoContrato.setText(contrato.getInquilino().getNombre() + " " + contrato.getInquilino().getApellido());
            binding.ETInmuebleContrato.setText(contrato.getInmueble().getTipo() + " en " + contrato.getInmueble().getDireccion());

        });

        model.setContratoMutableLiveData(getArguments());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}