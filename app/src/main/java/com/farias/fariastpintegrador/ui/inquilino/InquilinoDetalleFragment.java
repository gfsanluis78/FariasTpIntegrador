package com.farias.fariastpintegrador.ui.inquilino;

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
import com.farias.fariastpintegrador.databinding.FragmentInquilinoDetalleBinding;

public class InquilinoDetalleFragment extends Fragment {

    private InquilinoDetalleViewModel model;
    private FragmentInquilinoDetalleBinding binding;

    public static InquilinoDetalleFragment newInstance() {
        return new InquilinoDetalleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        model = new ViewModelProvider(this).get(InquilinoDetalleViewModel.class);

        binding = FragmentInquilinoDetalleBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        Log.d("mensaje", "El binding inquilino trae ");

        model.getInquilino().observe(getViewLifecycleOwner(), inquilino -> {

            binding.ETIdInquilino.setText(inquilino.getIdInquilino()+"");
            binding.ETNombreInquilino.setText(inquilino.getNombre());
            binding.ETApellidoInquilino.setText(inquilino.getApellido());
            binding.ETDniInquilino.setText(inquilino.getDNI()+"");
            binding.ETEmailInquilino.setText(inquilino.getEmail());
            binding.ETTelefonoInquilino.setText(inquilino.getTelefono());
            binding.ETLugarTrabajoInquilino.setText(inquilino.getLugarDeTrabajo());
            binding.ETGaranteInquilino.setText(inquilino.getNombreGarante());
            binding.ETTelefonoGarante.setText(inquilino.getTelefonoGarante());
        });

        model.setInquilino(getArguments());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}