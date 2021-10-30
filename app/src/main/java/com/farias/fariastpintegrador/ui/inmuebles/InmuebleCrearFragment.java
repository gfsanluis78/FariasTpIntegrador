package com.farias.fariastpintegrador.ui.inmuebles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.farias.fariastpintegrador.R;
import com.farias.fariastpintegrador.databinding.FragmentInmuebleCrearBinding;
import com.farias.fariastpintegrador.modelo.Inmueble;

import java.io.ByteArrayOutputStream;

public class InmuebleCrearFragment extends Fragment {

    private InmuebleCrearViewModel mViewModel;
    private FragmentInmuebleCrearBinding binding;
    private EditText etDireccion, etTipo, etAmbientes, etUso, etMontoAlquilerPropuesto;
    private ImageView imagen1;
    private String imgDeCode="";
    private Button btCargarFoto, btGuardarInmueble;
    private Inmueble inmueble;

    public static InmuebleCrearFragment newInstance() {
        return new InmuebleCrearFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentInmuebleCrearBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        iniciarVista();

//        mViewModel.getInmuebleMutableLiveData().observe(getViewLifecycleOwner(), i -> {
//                inmueble = i;
//        });

        btCargarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarImagen(); 
            }
        });
        
        btGuardarInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar();
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    private void guardar() {
        inmueble = new Inmueble();
        inmueble.setDireccion(etDireccion.getText().toString());
        inmueble.setUso(etUso.getText().toString());
        inmueble.setTipo(etTipo.getText().toString());
        inmueble.setAmbientes(Integer.parseInt(etAmbientes.getText().toString()));
        inmueble.setMontoALquilerPropuesto(etMontoAlquilerPropuesto.getText().toString());
        convertirImagen();
        inmueble.setImagen(imgDeCode);

        mViewModel.guardarInmueble(inmueble);
    }

    public void iniciarVista(){

        mViewModel= new ViewModelProvider(this).get(InmuebleCrearViewModel.class);

        etDireccion = binding.ETDireccion;
        etUso = binding.ETUso;
        etTipo = binding.ETTipo;
        etAmbientes = binding.ETAmbientes;
        etMontoAlquilerPropuesto = binding.ETAlquilerPropuesto;
        imagen1 = binding.imageView;
        btCargarFoto = binding.BTTomarFoto;
        btGuardarInmueble = binding.BTGuardarInmueble;
    }

    private void cargarImagen(){
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion..."),10);
    }

    private void convertirImagen(){
        BitmapDrawable drawable = (BitmapDrawable) imagen1.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        if(bitmap!=null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            imgDeCode= Base64.encodeToString(b,Base64.DEFAULT);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10){
            Uri path=data.getData();
            imagen1.setImageURI(path);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InmuebleCrearViewModel.class);
        // TODO: Use the ViewModel
    }

}