package com.farias.fariastpintegrador.ui.perfil;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.farias.fariastpintegrador.MainActivity;
import com.farias.fariastpintegrador.R;
import com.farias.fariastpintegrador.databinding.FragmentPerfilBinding;
import com.farias.fariastpintegrador.modelo.Propietario;
import com.farias.fariastpintegrador.ui.login.LoginActivity;
import com.farias.fariastpintegrador.ui.login.LoginViewModel;


public class PerfilFragment extends Fragment {

    private PerfilViewModel perfilViewModel;
    private LoginViewModel loginViewModel;

    private FragmentPerfilBinding binding;
    private EditText id,dni,nombre,apellido,email,clave,telefono;
    private TextView titulo;
    private ImageView foto;
    private Button editar, guardar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        perfilViewModel =                                                                   // Instanciamos el viewModel
                new ViewModelProvider(this).get(PerfilViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);    // Instancio el binding del fragment
        View root = binding.getRoot();  // Del binding saco el root

        this.inicializarControles(root);         // Inicializo la vista


        perfilViewModel.getUsuario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {  // De mi nuevo perfilView uso el metodo getUsuario
            @Override                                                                                // para traer el usuario (Uso el metodo getUsuario)
            public void onChanged(Propietario propietario) {

                Log.i("mensaje", propietario.getNombre() );

                titulo.setText(propietario.getNombre() + " " + propietario.getApellido());
                id.setText(propietario.getId()+"");
                dni.setText(propietario.getDni().toString());
                nombre.setText(propietario.getNombre());
                apellido.setText(propietario.getApellido());
                email.setText(propietario.getEmail());
                clave.setText(propietario.getContraseña());
                telefono.setText(propietario.getTelefono());
                foto.setImageResource(propietario.getAvatar());
            }
        });

        perfilViewModel.getEstadoEditable().observe(getViewLifecycleOwner(), new Observer<Boolean>() {  // observo el boton
            @Override
            public void onChanged(Boolean aBoolean) {
//                id.setEnabled((aBoolean));
                dni.setEnabled((aBoolean));
                nombre.setEnabled((aBoolean));
                apellido.setEnabled((aBoolean));
                email.setEnabled((aBoolean));
                clave.setEnabled((aBoolean));
                telefono.setEnabled((aBoolean));
           }
        });

        perfilViewModel.getEstadoNoEditable().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                dni.setEnabled((aBoolean));
                nombre.setEnabled((aBoolean));
                apellido.setEnabled((aBoolean));
                email.setEnabled((aBoolean));
                clave.setEnabled((aBoolean));
                telefono.setEnabled((aBoolean));


                perfilViewModel.obtenerUsuario();

            }
        });

        perfilViewModel.getVisibleEditar().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                editar.setVisibility(integer);
                editar.setText("Cancelar");
                editar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        perfilViewModel.cambiarEstadoNoEditable();
                        editar.setText("Editar");
                        editar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                perfilViewModel.cambiarEstadoEditable();

                            }
                        });
                    }
                });
            }
        });

        perfilViewModel.getVisibleGuardar().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                guardar.setVisibility(integer);
            }
        });


        perfilViewModel.obtenerUsuario();

        return root;
    }

    private void inicializarControles(View view){
        titulo = view.findViewById(R.id.TV_titulo);
        id = view.findViewById(R.id.ET_Id);
        dni = view.findViewById(R.id.ET_dni);
        nombre = view.findViewById(R.id.ET_nombre);
        apellido = view.findViewById(R.id.ET_apellido);
        email = view.findViewById(R.id.ET_email);
        clave = view.findViewById(R.id.ET_password);
        telefono = view.findViewById(R.id.ET_telefono);
        editar = view.findViewById(R.id.BT_Editar);
        foto = view.findViewById(R.id.IV_foto);

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perfilViewModel.cambiarEstadoEditable();
            }
        });

        guardar= view.findViewById(R.id.BT_Guardar);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Propietario p = new Propietario();
                p.setId(Integer.parseInt(id.getText().toString()));
                p.setDni(Long.parseLong(dni.getText().toString()));
                p.setNombre(nombre.getText().toString());
                p.setApellido(apellido.getText().toString());
                p.setEmail(email.getText().toString());
                p.setContraseña(clave.getText().toString());
                p.setTelefono(telefono.getText().toString());

                perfilViewModel.modificarDatos(p);
                //perfilViewModel.cambiarEstadoNoEditable();
                editar.setText("Editar");
                editar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        perfilViewModel.cambiarEstadoEditable();
                    }
                });

                Toast.makeText(getContext(),"Datos guardados correctamente", LENGTH_SHORT).show();

            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}