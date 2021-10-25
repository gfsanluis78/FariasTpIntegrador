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


        model.getInquilino().observe(getViewLifecycleOwner(), inquilino -> {

            Log.d("mensaje", "El  inquilino es " + inquilino.getInquilino().getApellido());


            binding.ETIdInquilino.setText(inquilino.getInquilino().getIdInquilino()+"");
            binding.ETNombreInquilino.setText(inquilino.getInquilino().getNombre());
            binding.ETApellidoInquilino.setText(inquilino.getInquilino().getApellido());
            binding.ETDniInquilino.setText(inquilino.getInquilino().getDNI()+"");
            binding.ETEmailInquilino.setText(inquilino.getInquilino().getEmail());
            binding.ETTelefonoInquilino.setText(inquilino.getInquilino().getTelefono());
            binding.ETLugarTrabajoInquilino.setText(inquilino.getGarante().getTrabajo());
            binding.ETGaranteInquilino.setText(inquilino.getGarante().getNombreCompleto());
            binding.ETTelefonoGarante.setText(inquilino.getGarante().getTelefono());
        });

        model.setInquilino(getArguments());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}