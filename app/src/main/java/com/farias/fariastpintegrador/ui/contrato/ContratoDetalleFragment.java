package com.farias.fariastpintegrador.ui.contrato;

import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.farias.fariastpintegrador.R;
import com.farias.fariastpintegrador.databinding.FragmentContratoDetalleBinding;
import com.farias.fariastpintegrador.modelo.Contrato;

import java.util.Date;

public class ContratoDetalleFragment extends Fragment {

    private ContratoDetalleViewModel model;
    private FragmentContratoDetalleBinding binding;
    private Contrato elContrato;

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
            binding.ETMontoAlquilerContrato.setText("$ " +contrato.getMontoAlquiler()+".00");
            binding.ETInquilinoContrato.setText(contrato.getInquilino().getNombre() + " " + contrato.getInquilino().getApellido());
            binding.ETInmuebleContrato.setText(contrato.getInmueble().getTipo() + " en " + contrato.getInmueble().getDireccion());

            elContrato = contrato;
            binding.BTContratosPagos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle result = new Bundle();

                    result.putSerializable("elContrato", elContrato);

                    Navigation.findNavController(view).navigate(R.id.pagoFragment, result);
                }
            });
        });

        model.setContratoMutableLiveData(getArguments());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}