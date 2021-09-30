package com.farias.fariastpintegrador.ui.logout;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.farias.fariastpintegrador.MainActivity;
import com.farias.fariastpintegrador.R;
import com.farias.fariastpintegrador.data.LoginDataSource;
import com.farias.fariastpintegrador.data.model.LoggedInUser;
import com.farias.fariastpintegrador.databinding.FragmentInquilinoFilaBinding;
import com.farias.fariastpintegrador.databinding.FragmentLogoutBinding;
import com.farias.fariastpintegrador.databinding.FragmentPerfilBinding;

public class LogoutFragment extends Fragment {

    private LoginDataSource logueado;
    private LogoutViewModel mViewModel;
    private Button logout;
    private FragmentLogoutBinding binding;

    public static LogoutFragment newInstance() {
        return new LogoutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLogoutBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        this.iniciarControl(view);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                builder.setTitle("Log out");

                builder.setMessage("Seguro desea desloguarse");

                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        logueado.logout();
                        startActivity(new Intent(getActivity(), MainActivity.class));

                        //Navigation.findNavController(view).navigate(R.id.login);
                        //finish();
                    }
                });
                // y si no quiero
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                //Inicializar el alert dialog
                AlertDialog alertDialog = builder.create();
                // Mostrar el alert
                alertDialog.show();
            }
        });

        return view;
    }

    private void iniciarControl(View view) {
        logout = view.findViewById(R.id.button_logout);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LogoutViewModel.class);
        // TODO: Use the ViewModel
    }

}